package com.github.snakerflow.plugin.starter.example.controller;

import com.github.snakerflow.plugin.starter.example.service.SnakerflowTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主应用snakerflow主测试类
 *
 * @author zhaoguoqing
 * Date: 2020-01-23 9:20 下午
 */
@RestController
public class SnakerflowTestController {

    @Autowired
    private SnakerflowTestService snakerflowTestService;

    @GetMapping("/getById")
    public String getById() {
        return snakerflowTestService.getById();
    }

    /**
     * 主要用于测试starter和主应用的mapper的兼容性
     *
     * @return String
     */
    @GetMapping("/getCity")
    public String getCity() {
        return snakerflowTestService.getCity();
    }

    /**
     * 主要用于测试snakerflow是否正常加载
     *
     * @return String
     */
    @GetMapping("/getProcessList")
    public String getProcess() {
        return snakerflowTestService.getProcess();
    }

}
