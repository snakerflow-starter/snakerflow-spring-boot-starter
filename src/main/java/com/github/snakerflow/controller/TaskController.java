package com.github.snakerflow.controller;

import com.alibaba.fastjson.JSON;
import com.github.snakerflow.result.Result;
import com.github.snakerflow.service.SnakerEngineFacets;
import lombok.extern.slf4j.Slf4j;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 流程Controller
 *
 * @author guoqing.zhao
 * Date: 2019-12-04 4:11 下午
 */

@RequestMapping("/")
@RestController
@Slf4j
public class TaskController {
    @Autowired
    private SnakerEngineFacets facets;

    /**
     * @param terms
     * @return
     */
    @RequestMapping(value = "tasks", method = RequestMethod.GET)
    public Result<List<WorkItem>> diagnosis(String terms) {
        Result<List<WorkItem>> result = new Result<>();
        List<String> list = new ArrayList<>();
        list.add("snaker");
        String[] assignees = new String[list.size()];
        list.toArray(assignees);

        Page<WorkItem> majorPage = new Page<WorkItem>(5);
        List<WorkItem> majorWorks = facets.getEngine()
                .query()
                .getWorkItems(majorPage, new QueryFilter()
                        .setOperators(assignees)
                        .setTaskType(TaskModel.TaskType.Major.ordinal()));
        System.out.println(JSON.toJSON(majorWorks));
        result.buildSuccess(majorWorks);
        return result;
    }

    private List<Process> getProcesses() {
        Page<Process> page = new Page<>();
        page.setPageNo(1);
        page.setPageSize(15);
        page.setTotalCount(0);
        String displayName = null;
        QueryFilter filter = new QueryFilter();
        if (StringHelper.isNotEmpty(displayName)) {
            filter.setDisplayName(displayName);
        }
        return facets.getEngine().process().getProcesss(page, filter);
    }
}
