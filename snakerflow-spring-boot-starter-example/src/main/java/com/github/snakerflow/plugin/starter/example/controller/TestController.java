package com.github.snakerflow.plugin.starter.example.controller;

import com.alibaba.fastjson.JSON;
import com.github.snakerflow.plugin.starter.example.dao.CityMapper;
import org.snaker.engine.entity.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试controller
 * @author zhaoguoqing
 * @date 20200307
 */
@RestController
public class TestController {

    @Resource
    private CityMapper cityMapper;
    @Autowired
    private SnakerEngineFacets snakerEngineFacets;

    @GetMapping("/getById")
    public String getById() {
        return "getById ok !";
    }

    @GetMapping("/getCity")
    public String getCity() {
        String city = cityMapper.selectById(1L).toString();
        return city;
    }

    @GetMapping("/getProcessList")
    public String getProcessList() {
        snakerEngineFacets.initFlows();
        List<Process> processList = snakerEngineFacets.getProcessList();
        return JSON.toJSONString(processList);
    }

}
