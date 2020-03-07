package com.github.snakerflow.plugin.starter.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("city")
public class CityDO {
    private Long id;

    private Long provinceId;

    private String cityName;

    private String description;

}