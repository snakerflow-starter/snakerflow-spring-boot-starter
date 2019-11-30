package org.spring.springboot;

import com.alibaba.fastjson.JSON;
import com.github.snakerflow.Application;
import com.github.snakerflow.service.SnakerEngineFacets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DemoApplicationTests {
    @Autowired
    private SnakerEngineFacets facets;

    /**
     * 1、获取流程列表
     */
    @Test
    public void getProcessList() {
        List<Process> processs = getProcesses();
        System.out.println(JSON.toJSON(processs));
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

    @Test
    public void getOrder() {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("reason", "2");
        params.put("processId", "5b89c4fc30194442a8a82ab6acde6734");
        params.put("approveDept.operator", "snaker");
        params.put("apply.operator", "test");
        params.put("day", 2);
        params.put("approveBoss.operator", "admin");
        List<Process> processs = getProcesses();
        Process process = processs.get(1);
        String processId = process.getId();
        String userName = "test";
        facets.startAndExecute(processId, userName, params);
        System.out.println(JSON.toJSON(processs));
    }
    @Test
    public void getTask() {
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
    }

    @Test
    public void 审批() {
        String taskId = "5382ea0474eb40f9a81c9ed4c43b0ec1";
        String userName = "snaker";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("reason", "2");
        params.put("processId", "5b89c4fc30194442a8a82ab6acde6734");
        params.put("approveDept.operator", "snaker");
        params.put("apply.operator", "test");
        params.put("day", 2);
        params.put("approveBoss.operator", "admin");
        params.put("approveDept.suggest", "同意");
        params.put("method", "0");
        params.put("taskId", "5382ea0474eb40f9a81c9ed4c43b0ec1");
        List<Task> execute = facets.execute(taskId, userName, params);
        System.out.println(JSON.toJSON(execute));
    }

}
