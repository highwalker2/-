package com.lab.mapper;

import com.lab.dto.UserInfoUpdateDTO;
import com.lab.dto.UserLoginDTO;
import com.lab.dto.UserRegisterDTO;
import com.lab.entity.User;
import com.lab.vo.UserInfoVO;
import com.lab.vo.UserLoginVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where student_id=#{studentId} and password=#{password}")
    UserLoginVO getByUser(UserLoginDTO userLoginDTO);

    @Insert("insert into user(student_id,password) values (#{studentId}, #{password})")
    void addUser(UserRegisterDTO userRegisterDTO);

    @Select("select * from user where id=#{currentId}")
    UserInfoVO getById(Long currentId);

    @Update("update user set password=#{newPwd} where id=#{currentId}")
    void updatepwd(String newPwd,Long currentId);

    @Select("select user.password from user where id=#{currentId}")
    String getPassword(Long currentId);

    @Update("update user set avatar=#{filePath} where id=#{currentId}")
    void updateAvatar(String filePath,Long currentId);

    @Select("select count(*) from user where type=#{c}")
    Integer getByType(char c);

    @Update("update user set name=#{name},phone=#{phone},email=#{email} where id=#{id}")
    void updateInfo(UserInfoUpdateDTO userInfoUpdateDTO);
}
