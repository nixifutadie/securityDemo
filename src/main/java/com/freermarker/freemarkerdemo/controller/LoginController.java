package com.freermarker.freemarkerdemo.controller;


import com.freermarker.freemarkerdemo.entity.Login;
import com.freermarker.freemarkerdemo.service.LoginService;
import com.freermarker.freemarkerdemo.utils.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /*@GetMapping
    public ResultResponse login(Login login){
      return loginService.login(login);
    }*/

    @PostMapping("/save")
    public ResultResponse save(@RequestBody Login login){
        return loginService.save(login);
    }


}
