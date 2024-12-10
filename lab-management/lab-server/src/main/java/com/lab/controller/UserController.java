package com.lab.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.util.IdUtil;
import com.lab.context.BaseContext;
import com.lab.dto.PassWordUpdateDTO;
import com.lab.dto.UserInfoUpdateDTO;
import com.lab.dto.UserLoginDTO;
import com.lab.dto.UserRegisterDTO;
import com.lab.entity.User;
import com.lab.properties.JwtProperties;
import com.lab.result.Result;
import com.lab.service.UserService;
import com.lab.utils.AliOssUtil;
import com.lab.utils.JwtUtil;
import com.lab.vo.UserInfoVO;
import com.lab.vo.UserLoginVO;
import com.lab.vo.UserTypeCountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:7000"}) // 允许来自 http://localhost:7000 的请求
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private AliOssUtil aliOssUtil;

    // 用于存储验证码和对应的过期时间
    private static final ConcurrentHashMap<String, String> codeCache = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Long> expirationCache = new ConcurrentHashMap<>();

    // 每分钟清理一次过期验证码
    static {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            expirationCache.forEach((key, expirationTime) -> {
                if (now > expirationTime) {
                    codeCache.remove(key);
                    expirationCache.remove(key);
                }
            });
        }, 1, 1, TimeUnit.MINUTES);
    }

    /**
     * 图形验证码生成接口
     * @return
     */
    @GetMapping("/getCode")
    public Result<Map<String, String>> getCode() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        String verify = IdUtil.simpleUUID();
        String code = lineCaptcha.getCode();

        // 将验证码存储到内存中并设置过期时间为2分钟
        codeCache.put(verify, code);
        expirationCache.put(verify, System.currentTimeMillis() + 120 * 1000);

        // 创建返回结果
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        lineCaptcha.write(os);
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(5);
        map.put("uuid", verify);
        map.put("code", code);
        map.put("img", Base64.encode(os.toByteArray()));

        return Result.success(map);
    }

    /**
     * 用户登录接口
     * @param userLoginDTO
     * @return
     */
    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("成员登录：{}", userLoginDTO);

        Boolean a=true;
        //判断是否需要验证码进行验证
        if(userLoginDTO.getIsVery()){
//        if(a){
            //验证uuid和图形验证码
            //获取uuid
            String uuid = userLoginDTO.getUuid();
            //获取验证码
            String code = userLoginDTO.getCode();

            //获取暂存的uuid和code
            String codece = codeCache.get(uuid);
            Long time=expirationCache.get(uuid);
            // 检查验证码是否已过期
            if (time == null || System.currentTimeMillis() > time) {
                return Result.error("验证码已过期");
            }
            //验证验证码是否正确
            if(! codece.equals(code)) {
                return Result.error("验证码不正确");
            }
        }

        UserLoginVO userLoginVO=userService.getByUser(userLoginDTO);
        if(userLoginVO==null){
            return Result.error("学号或者密码不正确");
        }

        //登录成功后，生成Jwt令牌
        log.info("成员登录：{}", jwtProperties.getUserSecretKey());
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId",userLoginVO.getId());
        String token = JwtUtil.creatJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO result = UserLoginVO.builder().id(userLoginVO.getId()).name(userLoginVO.getName()).token(token).build();
        return Result.success(result);
    }

    /**
     * 用户注册接口
     * @param userRegisterDTO
     * @return
     */
    @ApiOperation("用户注册接口")
    @PostMapping("/reg")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册{}", userRegisterDTO);

        //验证uuid和图形验证码
        //获取uuid
        String uuid = userRegisterDTO.getUuid();
        //获取验证码
        String code = userRegisterDTO.getCode();

        //获取暂存的uuid和code
        String codece = codeCache.get(uuid);
        Long time=expirationCache.get(uuid);
        // 检查验证码是否已过期
        if (time == null || System.currentTimeMillis() > time) {
            return Result.error("验证码已过期");
        }
        //验证验证码是否正确
        if(! codece.equals(code)) {
            return Result.error("验证码不正确");
        }

        userService.addUser(userRegisterDTO);
        return Result.success();
    }

    /**
     * 获取用户信息，存储在前端仓库
     * @return
     */
    @ApiOperation("获取用户的信息")
    @GetMapping("/userinfo")
    public Result<UserInfoVO> getUserInfo() {
        log.info("获取当前登录用户的信息");

        UserInfoVO userInfoVO=userService.getUserInfo();
        return Result.success(userInfoVO);
    }

    /**
     * 密码修改接口
     * @param passWordUpdateDTO
     * @return
     */
    @PatchMapping("/updatepwd")
    public Result passWordUpdate(@RequestBody PassWordUpdateDTO passWordUpdateDTO){
        log.info("开始进行密码修改：{}", passWordUpdateDTO);
        String pwd=userService.getPassword();
        String oldpwd=passWordUpdateDTO.getOld_pwd();

        if(!pwd.equals(passWordUpdateDTO.getOld_pwd())){
            return Result.error("原密码错误");
        }
        userService.updatepwd(passWordUpdateDTO.getNew_pwd());
        return Result.success();
    }

    @PatchMapping("/update/avatar")
    @ApiOperation("用户头像更新")
    public Result<String> updateAvatar(MultipartFile file){
        log.info("更新头像：{}", file);
        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename()+'-'+ BaseContext.getCurrentId();
            String filePath=aliOssUtil.upload(file.getBytes(),originalFilename);

            userService.updateAvatar(filePath);

            return Result.success(filePath);
        }catch (Exception e){
            log.info("文件上传失败：{}",e);
        }
        return Result.error("文件上传失败");
    }

    /**
     * 统计成员类型接口
     * @return
     */
    @ApiOperation("成员类型统计接口")
    @GetMapping("/statistics")
    public Result<UserTypeCountVo> userTypeCount(){
        log.info("统计成员类型数量");

        UserTypeCountVo userTypeCountVo=userService.typeCount();
        return Result.success(userTypeCountVo);
    }

    /**
     * 用户基本信息更新接口
     * @param userInfoUpdateDTO
     * @return
     */
    @ApiOperation("用户基本信息更新接口")
    @PutMapping("/infoUpdate")
    public Result userInfoUpdate(@RequestBody UserInfoUpdateDTO userInfoUpdateDTO){
        log.info("更新用户信息：{}", userInfoUpdateDTO);

        userService.updateInfo(userInfoUpdateDTO);

        return Result.success();
    }
}
