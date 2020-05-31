package com.github.snakerflow.plugin.starter.example.dao;

import com.github.snakerflow.plugin.starter.example.entity.CityDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * description:
 *
 * @author guoqing.zhao
 * Date: 2019-12-25 10:21 上午
 */
@Mapper
public interface CityDAO {
    /**
     * 详情查询
     *
     * @param id
     * @return
     */
    @Select("select id,province_id,city_name,description from city where id = #{id}")
    CityDO getById(Long id);

    /**
     * 详情查询
     *
     * @param cityDO
     * @return
     */
    @Insert("insert into city(id, province_id, city_name, description) values(#{id}, #{provinceId}, #{cityName}, #{description})")
    Integer insert(CityDO cityDO);
}
