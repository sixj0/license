package com.sixj.test.controller;

import com.sixj.license.aspect.License;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sixiaojie
 * @date 2021-05-27-15:02
 */
@RestController
@RequestMapping("/license")
public class TestLicenseController {

    @GetMapping("/test")
    @License
    public String test(){
        return "success";
    }
}
