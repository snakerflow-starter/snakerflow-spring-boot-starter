package com.github.snakerflow.plugin.starter.example.controller;

import com.alibaba.fastjson.JSON;
import com.github.snakerflow.plugin.starter.example.controller.SnakerflowTestController;
import com.github.snakerflow.plugin.starter.example.entity.CityDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SnakerflowControllerTest {
    @Autowired
    private SnakerflowTestController snakerflowTestController;

    @Test
    public void getById() {
        String byId = snakerflowTestController.getById();
        Assert.assertNotNull(byId);
    }

    @Test
    public void getByCity() {
        String city = snakerflowTestController.getCity();
        Assert.assertNotNull(city);
        CityDO cityDO = JSON.parseObject(city, CityDO.class);
        CityDO cityDOTest = new CityDO();
        Assert.assertFalse(cityDO.equals(cityDOTest));
        Assert.assertNotNull(cityDO.hashCode());
        Assert.assertNotNull(cityDO.toString());
    }

    @Test
    public void getProcessList() {
        String process = snakerflowTestController.getProcess();
        System.out.println(process);
        Assert.assertNotNull(process);
    }
}
