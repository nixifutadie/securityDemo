package com.freermarker.freemarkerdemo.service;


import com.freermarker.freemarkerdemo.entity.Login;
import com.freermarker.freemarkerdemo.entity.Role;
import com.freermarker.freemarkerdemo.entity.User;
import com.freermarker.freemarkerdemo.mapper.UserMapper;
import com.freermarker.freemarkerdemo.utils.ResultResponse;
import com.freermarker.freemarkerdemo.utils.TokenUtils;
import com.freermarker.freemarkerdemo.utils.imp.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class LoginService {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private TokenUtils jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /*public ResultResponse login(Login login){
        if (login!=null){

            if ((StringUtils.isEmpty(login.getUsername())||!"admin".equals(login.getUsername()))
            && ((StringUtils.isEmpty(login.getPassword())||!"123456".equals(login.getPassword())))){
                return new ResultResponse("500","用户名密码不正确");
            }else {
                return new ResultResponse(login);
            }
        }   return null;

    }*/


    /*public ResultResponse login(Login login) throws AuthenticationException {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
        return new ResultResponse("200",jwtTokenUtil.generateToken(userDetails.getUsername()));
    }*/



    public ResultResponse save(Login login){
        String uid = UUID.randomUUID().toString().replaceAll("-","");
        String rid = UUID.randomUUID().toString().replaceAll("-","");
        User user = new User();
        Role role = new Role();
        user.setUsername(login.getUsername());
        // 记得注册的时候把密码加密一下
        user.setPassword(bCryptPasswordEncoder.encode(login.getPassword()));
        user.setId(uid);
        user.setRId(rid);

        role.setId(rid);
        role.setAuth("ROLE_USER");
        role.setRoleName("普通用户");
        userMapper.saveUser(user);
        userMapper.saveUserRole(role);
        return new  ResultResponse("200","注册成功");
    }
}
