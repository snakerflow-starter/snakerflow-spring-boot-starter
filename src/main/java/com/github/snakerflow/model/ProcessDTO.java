package com.github.snakerflow.model;

import lombok.Data;
import org.snaker.engine.entity.Process;

/**
 * description:
 *
 * @author guoqing.zhao
 * Date: 2019-12-04 4:52 下午
 */
@Data
public class ProcessDTO extends Process {
    private String contentString;
}
