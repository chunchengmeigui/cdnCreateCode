package com.springboot.demo.service;

import com.springboot.demo.entity.Student;

import java.util.HashMap;
import com.github.pagehelper.PageInfo;
import java.util.Map;

/**
 * desc:
 * author caidingnu
 * create 2020-01-02
 * version 1.0.0
 */
public interface StudentService {

    int insert(Student obj);

    int deleteByCondition(Map<String, Object> map);

    int update(Student obj);

    PageInfo<Student> find(Map<String, Object> map);

    Student findSingle(Map<String, Object> map);

    int deleteLogic(Map<String, Object> map);
}
