package com.github.snakerflow.plugin.starter.example.entity;

import lombok.Data;

@Data
public class CityDO {
    private Long id;

    private Long provinceId;

    private String cityName;

    private String description;

}