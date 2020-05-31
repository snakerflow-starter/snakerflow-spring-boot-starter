package com.github.snakerflow.plugin.starter.example.service;

import com.alibaba.fastjson.JSON;
import com.github.snakerflow.plugin.starter.example.dao.CityDAO;
import com.github.snakerflow.plugin.starter.example.entity.CityDO;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * description: SnakerflowTestService
 *
 * @author guoqing.zhao
 * Date: 2020-03-23 9:20 下午
 */
@Service
public class SnakerflowTestService {
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private SnakerEngineFacets snakerEngineFacets;

    /**
     * 主要用于测试starter和主应用的正常启动
     *
     * @return String
     */
    @GetMapping("/getById")
    public String getById() {
        return "getById ok !";
    }

    /**
     * 主要用于测试starter和主应用的mapper的兼容性
     *
     * @return String
     */
    public String getCity() {
        CityDO cityDO = new CityDO();
        cityDO.setId(1L);
        cityDO.setCityName("杭州");
        cityDO.setDescription("杭州西湖很美");
        cityDO.setProvinceId(1L);
        cityDAO.insert(cityDO);
        cityDO = cityDAO.getById(1L);
        return JSON.toJSONString(cityDO);
    }

    /**
     * 主要用于测试snakerflow是否正常加载
     *
     * @return String
     */
    @Transactional
    public String getProcess() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        return JSON.toJSONString(allProcess);
    }

    /**
     * 主要用于测试snakerflow是否正常加载
     *
     * @return String
     */
    @Transactional
    public String start() {
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (CollectionUtils.isEmpty(allProcess)) {
            return "请先初始化process，http://localhost:8080/getProcessList";
        }
        Order order = snakerEngineFacets.startInstanceById(allProcess.get(0).getId(), "zhaoguoqing", null);
        return JSON.toJSONString(order);
    }
}
