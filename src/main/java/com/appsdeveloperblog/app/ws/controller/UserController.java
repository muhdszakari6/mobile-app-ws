package com.appsdeveloperblog.app.ws.controller;

import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @GetMapping
    public String getUser(){
        return "Get user was called";
    }
    @PostMapping
    public String createUser(@RequestBody UserDetailsRequestModel userDetails) {
        return "Create user was called";
    }
    @PutMapping
    public String updateUser(){
        return  "Update user was called";
    }
    @DeleteMapping
    public String deleteUser(){
        return "Delete user was called";
    }

}
