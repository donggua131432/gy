package com.gy.mapper.sys;

import com.gy.domain.dto.sys.User;
import com.gy.domain.vo.req.UserQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteUserById(String userId);

    int insertUser(User user);

    User selectUserById(@Param("appId") String appId);

    List<User> selectListUserPage(UserQuery userQuery);

    String selectUserRoles(@Param("userId") String userId);

    int updateUser(User user);

    int reSetPassword(@Param("userId") String userId,@Param("password") String password);

}