package com.springboot.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.demo.utils.MyException;
import com.springboot.demo.utils.StrUtils;
import com.springboot.demo.entity.Student;
import com.springboot.demo.mapper.StudentMapper;
import com.springboot.demo.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    @Resource
    StudentMapper studentMapper;

    @Override
    public int insert(Student obj) {
         
        if (StrUtils.isNullOrEmpty(obj)) {
            throw new MyException("插入数据不能为空！");
        }
        return studentMapper.insert(obj);
    }

    @Override
    public int deleteByCondition(Map<String, Object> map) {
        if ("".equals(map.get("stuId")) || null == map.get("stuId")) {
            throw new MyException("主键不能为空！");
        }
        return studentMapper.deleteByCondition(map);
    }

    @Override
    public int update(Student obj) {
        if ("".equals(obj.getStuId() + "") || null == obj.getStuId()) {
            throw new MyException("主键不能为空！");
        }
        return studentMapper.update(obj);
    }

    @Override
    public  PageInfo<Student> find(Map<String, Object> map) {

        int pageIndex = 0;
        int pageSize = 10;
        if (map.get("pageIndex") != null) {
            pageIndex = Integer.parseInt(map.get("pageIndex") + "");
        }
        if (map.get("pageSize") != null) {
            pageIndex = Integer.parseInt(map.get("pageSize") + "");
        }
        PageHelper.startPage(pageIndex, pageSize);
        List<Student> list = studentMapper.find(map);

        return new PageInfo<>(list);
    }

    @Override
    public Student findSingle(Map<String, Object> map) {
        if ("".equals(map.get("stuId")) || null == map.get("stuId")) {
            throw new MyException("主键不能为空！");
        }
        return studentMapper.findSingle(map);
    }


    @Override
    public int deleteLogic(Map<String, Object> map) {
        if ("".equals(map.get("stuId")) || null == map.get("stuId")) {
            throw new MyException("主键不能为空！");
        }
        return studentMapper.logicDelete(map);
    }
}
