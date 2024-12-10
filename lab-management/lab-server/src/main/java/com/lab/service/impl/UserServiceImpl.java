package com.lab.service.impl;

import com.lab.context.BaseContext;
import com.lab.dto.UserInfoUpdateDTO;
import com.lab.dto.UserLoginDTO;
import com.lab.dto.UserRegisterDTO;
import com.lab.entity.User;
import com.lab.mapper.UserMapper;
import com.lab.service.UserService;
import com.lab.vo.UserInfoVO;
import com.lab.vo.UserLoginVO;
import com.lab.vo.UserTypeCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录接口
     * @param userLoginDTO
     * @return
     */
    public UserLoginVO getByUser(UserLoginDTO userLoginDTO) {
        UserLoginVO user=userMapper.getByUser(userLoginDTO);
        return user;
    }

    /**
     * 用户注册接口
     * @param userRegisterDTO
     * @return
     */
    public void addUser(UserRegisterDTO userRegisterDTO) {
        userMapper.addUser(userRegisterDTO);
    }

    /**
     * 获取用户信息，存储在前端仓库
     * @return
     */
    public UserInfoVO getUserInfo() {
        Long currentId = BaseContext.getCurrentId();
        UserInfoVO userInfoVO=userMapper.getById(currentId);
        return userInfoVO;
    }

    @Override
    public void updatepwd(String newPwd) {
        Long currentId = BaseContext.getCurrentId();
        userMapper.updatepwd(newPwd, currentId);
    }

    @Override
    public String getPassword() {
        Long currentId = BaseContext.getCurrentId();
        String pwd=userMapper.getPassword(currentId);
        return pwd;
    }

    @Override
    public void updateAvatar(String filePath) {
        Long currentId = BaseContext.getCurrentId();
        userMapper.updateAvatar(filePath,currentId);
    }

    @Override
    public UserTypeCountVo typeCount() {
        Integer mentor=userMapper.getByType('0');
        Integer doctor=userMapper.getByType('1');
        Integer postgraduate=userMapper.getByType('2');
        Integer undergraduate=userMapper.getByType('3');
        Integer employee=userMapper.getByType('4');

        UserTypeCountVo userTypeCountVo = new UserTypeCountVo();
        userTypeCountVo.setMentor(mentor);
        userTypeCountVo.setDoctor(doctor);
        userTypeCountVo.setPostgraduate(postgraduate);
        userTypeCountVo.setUndergraduate(undergraduate);
        userTypeCountVo.setEmployee(employee);
        return userTypeCountVo;
    }

    @Override
    public void updateInfo(UserInfoUpdateDTO userInfoUpdateDTO) {
        userMapper.updateInfo(userInfoUpdateDTO);
    }
}
