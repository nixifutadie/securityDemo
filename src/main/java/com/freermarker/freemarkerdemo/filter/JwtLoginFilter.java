package com.freermarker.freemarkerdemo.filter;

import ch.qos.logback.core.util.TimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freermarker.freemarkerdemo.entity.Login;
import com.freermarker.freemarkerdemo.entity.User;
import com.freermarker.freemarkerdemo.mapper.UserMapper;
import com.freermarker.freemarkerdemo.utils.ResultResponse;
import com.freermarker.freemarkerdemo.utils.TokenUtils;
import lombok.Data;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author: Lan
 * @date: 2019/4/8 15:27
 * @description:处理登录请求
 */

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private TokenUtils jwtTokenUtil;


    @Value("${token.header}")
    private String token_header;

    @Autowired
    private AuthenticationManager jwtAuthenticationManager;


    public JwtLoginFilter(){
        //this.jwtAuthenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }


    /**
     * 请求登录
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
            Login loginUser = new ObjectMapper().readValue(request.getInputStream(), Login.class);
            return jwtAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 所以就是JwtUser啦
        User jwtUser = (User) authResult.getPrincipal();
        System.out.println("jwtUser:" + jwtUser.toString());
        String token = jwtTokenUtil.generateToken(jwtUser.getUsername());
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader("Authorization", jwtTokenUtil.TOKEN_PREFIX + token);
    }

    /**
     * 登录失败
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }

    /**
     * 校验参数
     *
     * @param loginForm
     */
    private void checkLoginForm(Login loginForm, HttpServletResponse response) {
        if (StringUtils.isEmpty(loginForm.getUsername())) {
            new ResultResponse("500","用户名读取错误",response);
            return;
        }
        if (StringUtils.isEmpty(loginForm.getPassword())) {
            new ResultResponse("500","密码读取错误",response);
            return;
        }
    }
}
