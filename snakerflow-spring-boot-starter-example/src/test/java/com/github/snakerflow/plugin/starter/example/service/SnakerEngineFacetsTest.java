package com.github.snakerflow.plugin.starter.example.service;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SnakerEngineFacetsTest {
    @Autowired
    SnakerEngineFacets snakerEngineFacets;

    @Test
    public void initFlows() {
        String flows = snakerEngineFacets.initFlows();
        Assert.assertNotNull(flows);
    }

    @Test
    public void getAllProcess() {
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        Assert.assertNotNull(allProcess);
    }

    @Test
    public void getEngine() {
        SnakerEngine engine = snakerEngineFacets.getEngine();
        Assert.assertNotNull(engine);
    }

    @Test
    public void getAllProcessNames() {
        List<String> allProcessNames = snakerEngineFacets.getAllProcessNames();
        Assert.assertNotNull(allProcessNames);
    }

    @Test
    public void getProcessByOrderId() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startInstanceById(process.getId(), "apply.operator", null);
            Assert.assertNotNull(dm);
            List<Process> processByOrderId = snakerEngineFacets.getProcessByOrderId(dm.getId());
            Assert.assertNotNull(processByOrderId);
        }
    }

    @Test
    public void startInstanceById() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startInstanceById(process.getId(), "apply.operator", null);
            Assert.assertNotNull(dm);
        }
    }

    @Test
    public void startInstanceByName() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startInstanceByName(process.getName(), 0, "apply.operator", null);
            Assert.assertNotNull(dm);
        }
    }


    @Test
    public void testStartAndExecute() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startAndExecute(process.getId(), "apply.operator", null);
            Assert.assertNotNull(dm);
        }
    }

    @Test
    public void startAndExecute() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startAndExecute(process.getName(), 0, "apply.operator", null);
            Assert.assertNotNull(dm);
        }
    }

    @Test
    public void execute() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startAndExecute(process.getId(), "apply.operator", null);
            List<Task> tasks = snakerEngineFacets.getTasks(dm.getId());
            if (!CollectionUtils.isEmpty(tasks)) {
                List<Task> taskList = snakerEngineFacets.execute(tasks.get(0).getId(), "approveDept.operator", null);
                Assert.assertNotNull(taskList);
            }
        }
    }


    @Test
    public void getTasks() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startAndExecute(process.getId(), "apply.operator", null);
            List<Task> tasks = snakerEngineFacets.getTasks(dm.getId());
            Assert.assertNotNull(tasks);
        }
    }

    @Test
    public void executeAndJump() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (!CollectionUtils.isEmpty(allProcess)) {
            Process process = allProcess.get(0);
            Order dm = snakerEngineFacets.startAndExecute(process.getId(), "apply.operator", null);
            List<Task> tasks = snakerEngineFacets.getTasks(dm.getId());
            List<Task> taskList = snakerEngineFacets.executeAndJump(tasks.get(0).getId(), "approveDept.operator", null, "end1");
            System.out.println("tasks" + JSON.toJSON(tasks));
            Assert.assertNotNull(tasks);
        }
    }

}
