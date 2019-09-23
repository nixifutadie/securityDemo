package com.freermarker.freemarkerdemo.mapper;

import com.freermarker.freemarkerdemo.entity.Role;
import com.freermarker.freemarkerdemo.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User getUserFromDatabase(@Param("username") String username);

    Integer saveUser(User user);

    Integer saveUserRole(Role role);

}
