package com.exchange.controller;

import com.exchange.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SecurityController {

    private UserRoleService userRoleService;

    @Autowired
    public SecurityController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping(value = "/role/{userName}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> getUserRole(@PathVariable(value = "userName") String userName) {
        return userRoleService.getRolesByUserName(userName);
    }

}
