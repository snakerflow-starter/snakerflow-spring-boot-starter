/* Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.snakerflow.plugin.starter.example.controller;

import org.apache.commons.lang.StringUtils;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.*;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.model.TaskModel.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoguoqing
 * @since 0.1
 */
@Component
public class SnakerEngineFacets {
    @Autowired
    private SnakerEngine engine;

    public void initFlows() {
        //engine.process().redeploy("6d71000339064b44a4cdec4585dac0bc",StreamHelper.getStreamFromClasspath("flow/process_starry.snaker"));
        //engine.process().redeploy("6d71000339064b44a4cdec4585dac0bc",StreamHelper.getStreamFromClasspath("flow/process_hengrui.snaker"));
        //engine.process().deploy(StreamHelper.getStreamFromClasspath("flows/leave.snaker"));
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("flows/leave.snaker");
        engine.process().deploy(stream);
    }

    public SnakerEngine getEngine() {
        return engine;
    }

    public List<String> getAllProcessNames() {
        List<Process> list = engine.process().getProcesss(new QueryFilter());
        List<String> names = new ArrayList<String>();
        for (Process entity : list) {
            if (names.contains(entity.getName())) {
                continue;
            } else {
                names.add(entity.getName());
            }
        }
        return names;
    }

    public Order startInstanceById(String processId, String operator, Map<String, Object> args) {
        return engine.startInstanceById(processId, operator, args);
    }

    public Order startInstanceByName(String name, Integer version, String operator, Map<String, Object> args) {
        return engine.startInstanceByName(name, version, operator, args);
    }

    public Order startAndExecute(String name, Integer version, String operator, Map<String, Object> args) {
        Order order = engine.startInstanceByName(name, version, operator, args);
        List<Task> tasks = engine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
        List<Task> newTasks = new ArrayList<Task>();
        if (tasks != null && tasks.size() > 0) {
            Task task = tasks.get(0);
            newTasks.addAll(engine.executeTask(task.getId(), operator, args));
        }
        return order;
    }

    public Order startAndExecute(String processId, String operator, Map<String, Object> args) {
        Order order = engine.startInstanceById(processId, operator, args);
        List<Task> tasks = engine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
        List<Task> newTasks = new ArrayList<Task>();
        if (tasks != null && tasks.size() > 0) {
            Task task = tasks.get(0);
            newTasks.addAll(engine.executeTask(task.getId(), operator, args));
        }
        return order;
    }

    public List<Task> execute(String taskId, String operator, Map<String, Object> args) {
        return engine.executeTask(taskId, operator, args);
    }

    public List<Task> executeAndJump(String taskId, String operator, Map<String, Object> args, String nodeName) {
        return engine.executeAndJumpTask(taskId, operator, args, nodeName);
    }

    public List<Task> transferMajor(String taskId, String operator, String... actors) {
        List<Task> tasks = engine.task().createNewTask(taskId, TaskType.Major.ordinal(), actors);
        engine.task().complete(taskId, operator);
        return tasks;
    }

    public List<Task> transferAidant(String taskId, String operator, String... actors) {
        List<Task> tasks = engine.task().createNewTask(taskId, TaskType.Aidant.ordinal(), actors);
        engine.task().complete(taskId, operator);
        return tasks;
    }

    public Map<String, Object> flowData(String orderId, String taskName) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(taskName)) {
            List<HistoryTask> histTasks = engine.query()
                    .getHistoryTasks(
                            new QueryFilter().setOrderId(orderId).setName(
                                    taskName));
            List<Map<String, Object>> vars = new ArrayList<Map<String, Object>>();
            for (HistoryTask hist : histTasks) {
                vars.add(hist.getVariableMap());
            }
            data.put("vars", vars);
            data.put("histTasks", histTasks);
        }
        return data;
    }

    public void addSurrogate(Surrogate entity) {
        if (entity.getState() == null) {
            entity.setState(1);
        }
        engine.manager().saveOrUpdate(entity);
    }

    public void deleteSurrogate(String id) {
        engine.manager().deleteSurrogate(id);
    }

    public Surrogate getSurrogate(String id) {
        return engine.manager().getSurrogate(id);
    }

    public List<Surrogate> searchSurrogate(Page<Surrogate> page, QueryFilter filter) {
        return engine.manager().getSurrogate(page, filter);
    }

    public List<Task> getTasks(String orderId) {
        return engine.query().getActiveTasks(new QueryFilter().setOrderId(orderId));
    }

    public List<Process> getProcess(String orderId) {
        QueryFilter filter = new QueryFilter();
        filter.setOrderId(orderId);
        return engine.process().getProcesss(new QueryFilter());
    }
    public List<Process> getProcessList() {
        QueryFilter filter = new QueryFilter();
        return engine.process().getProcesss(new QueryFilter());
    }
}
