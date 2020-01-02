package com.springboot.demo.mapper;


import com.springboot.demo.entity.Teacher;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import com.springboot.demo.mapper.provider.TeacherProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * desc：
 * author CDN
 * create 2020-01-02
 * version 1.0.0
 */
public interface TeacherMapper {

    @InsertProvider(type = TeacherProvider.class, method = "insert")
    int insert(Teacher obj);

    @DeleteProvider(type = TeacherProvider.class, method = "deleteByCondition")
    int deleteByCondition(Map<String, Object> map);

    @UpdateProvider(type = TeacherProvider.class, method = "update")
    int update(Teacher obj);

    @SelectProvider(type = TeacherProvider.class, method = "find")
    List<Teacher> find(Map<String, Object> map);

    @SelectProvider(type = TeacherProvider.class, method = "findSingle")
    Teacher findSingle(Map<String, Object> map);
}
