package com.github.snakerflow.controller;

import com.alibaba.fastjson.JSON;
import com.github.snakerflow.model.ProcessDTO;
import com.github.snakerflow.result.Result;
import com.github.snakerflow.service.SnakerEngineFacets;
import lombok.extern.slf4j.Slf4j;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Process;
import org.snaker.engine.helper.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
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
public class ProcessController {
    @Autowired
    private SnakerEngineFacets facets;

    /**
     * 获取数据库相关流程
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result<List<ProcessDTO>> diagnosis() {
        Result<List<ProcessDTO>> result = new Result<>();
        List<Process> processs = getProcesses();
        if (CollectionUtils.isEmpty(processs)) {
            return result.buildFailue("1001", "数据库数据为空");
        }
        List<ProcessDTO> processDTOS = new ArrayList<>();
        for (Process process : processs) {
            ProcessDTO processDTO = new ProcessDTO();
            processDTO.setDisplayName(process.getDisplayName());
            processDTO.setId(process.getId());
            try {
                processDTO.setContentString(StringHelper.textXML(new String(process.getDBContent(), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            processDTOS.add(processDTO);
        }
        log.info("result" + JSON.toJSON(processDTOS));
        result.buildSuccess(processDTOS);
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
