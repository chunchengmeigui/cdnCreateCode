package com.springboot.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.demo.utils.MyException;
import com.springboot.demo.utils.StrUtils;
import com.springboot.demo.entity.Teacher;
import com.springboot.demo.mapper.TeacherMapper;
import com.springboot.demo.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * desc：
 * author CDN
 * create 2020-01-02
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    TeacherMapper teacherMapper;

    @Override
    public int insert(Teacher obj) {
         
        if (StrUtils.isNullOrEmpty(obj)) {
            throw new MyException("插入数据不能为空！");
        }
        return teacherMapper.insert(obj);
    }

    @Override
    public int deleteByCondition(Map<String, Object> map) {
        if ("".equals(map.get("tecId")) || null == map.get("tecId")) {
            throw new MyException("主键不能为空！");
        }
        return teacherMapper.deleteByCondition(map);
    }

    @Override
    public int update(Teacher obj) {
        if ("".equals(obj.getTecId() + "") || null == obj.getTecId()) {
            throw new MyException("主键不能为空！");
        }
        return teacherMapper.update(obj);
    }

    @Override
    public  PageInfo<Teacher> find(Map<String, Object> map) {

        int pageIndex = 0;
        int pageSize = 10;
        if (map.get("pageIndex") != null) {
            pageIndex = Integer.parseInt(map.get("pageIndex") + "");
        }
        if (map.get("pageSize") != null) {
            pageIndex = Integer.parseInt(map.get("pageSize") + "");
        }
        PageHelper.startPage(pageIndex, pageSize);
        List<Teacher> list = teacherMapper.find(map);

        return new PageInfo<>(list);
    }

    @Override
    public Teacher findSingle(Map<String, Object> map) {
        if ("".equals(map.get("tecId")) || null == map.get("tecId")) {
            throw new MyException("主键不能为空！");
        }
        return teacherMapper.findSingle(map);
    }
}
