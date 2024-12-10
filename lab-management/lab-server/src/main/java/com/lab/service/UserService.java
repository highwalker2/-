package com.lab.service;

import com.lab.dto.UserInfoUpdateDTO;
import com.lab.dto.UserLoginDTO;
import com.lab.dto.UserRegisterDTO;
import com.lab.vo.UserInfoVO;
import com.lab.vo.UserLoginVO;
import com.lab.vo.UserTypeCountVo;

public interface UserService {
    /**
     * 用户登录接口
     * @param userLoginDTO
     * @return
     */
    UserLoginVO getByUser(UserLoginDTO userLoginDTO);

    /**
     * 用户注册接口
     * @param userRegisterDTO
     * @return
     */
    void addUser(UserRegisterDTO userRegisterDTO);

    /**
     * 获取用户信息，存储在前端仓库
     * @return
     */
    UserInfoVO getUserInfo();

    void updatepwd(String newPwd);

    String getPassword();

    void updateAvatar(String filePath);

    UserTypeCountVo typeCount();

    void updateInfo(UserInfoUpdateDTO userInfoUpdateDTO);
}
