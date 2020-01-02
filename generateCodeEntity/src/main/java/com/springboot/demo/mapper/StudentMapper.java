package com.springboot.demo.mapper;


import com.springboot.demo.entity.Student;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import com.springboot.demo.mapper.provider.StudentProvider;
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
public interface StudentMapper {

    @InsertProvider(type = StudentProvider.class, method = "insert")
    int insert(Student obj);

    @DeleteProvider(type = StudentProvider.class, method = "deleteByCondition")
    int deleteByCondition(Map<String, Object> map);

    @UpdateProvider(type = StudentProvider.class, method = "update")
    int update(Student obj);

    @SelectProvider(type = StudentProvider.class, method = "find")
    List<Student> find(Map<String, Object> map);

    @SelectProvider(type = StudentProvider.class, method = "findSingle")
    Student findSingle(Map<String, Object> map);

    @UpdateProvider(type = StudentProvider.class, method = "logicDelete")
    int logicDelete(Map<String, Object> map);
}
