package com.springboot.demo.service;

import com.springboot.demo.entity.Teacher;

import java.util.HashMap;
import com.github.pagehelper.PageInfo;
import java.util.Map;

/**
 * desc:
 * author caidingnu
 * create 2020-01-02
 * version 1.0.0
 */
public interface TeacherService {

    int insert(Teacher obj);

    int deleteByCondition(Map<String, Object> map);

    int update(Teacher obj);

    PageInfo<Teacher> find(Map<String, Object> map);

    Teacher findSingle(Map<String, Object> map);

}
