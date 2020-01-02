package com.springboot.demo.controller;

import com.github.pagehelper.PageInfo;
import com.springboot.demo.utils.JsonResult;
import com.springboot.demo.entity.Student;
import com.springboot.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * desc：
 * author CDN
 * create 2020-01-02
 */
@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentService;


    /**
     * desc: 新增
     * param:
     * author: CDN
     * date: 2020-01-02
     */
    @RequestMapping("insert")
    public JsonResult insert(@RequestBody Student student) {
        return JsonResult.buildSuccess(studentService.insert(student));
    }


    /**
     * desc: 条件删除
     * param:
     * return:
     * author: CDN
     * date: 2020-01-02
     */
    @RequestMapping("deleteByCondition")
    public JsonResult deleteByCondition(@RequestBody Map map) {
        return JsonResult.buildSuccess(studentService.deleteByCondition(map));
    }


    /**
     * desc: 修改
     * param:
     * author: CDN
     * date: 2020-01-02
     */
    @RequestMapping("update")
    public JsonResult update(@RequestBody Student student) {
        return JsonResult.buildSuccess(studentService.update(student));
    }


    /**
     * desc: 条件查询(分页)
     * param:
     * return:
     * author: CDN
     * date: 2020-01-02
     */
    @RequestMapping("getPageList")
    public JsonResult getPageList(@RequestParam HashMap map) {
        PageInfo<Student> pageInfo = studentService.find(map);
        return JsonResult.buildSuccess(pageInfo);
    }

    /**
     * desc: 单条记录
     * param:
     * return:
     * author: CDN
     * date: 2020-01-02
     */
    @RequestMapping("findSingle")
    public JsonResult findSingle(@RequestParam Map map) {
        return JsonResult.buildSuccess(studentService.findSingle(map));
    }


    /**
     * desc: 逻辑删除
     * param:
     * return:
     * author: CDN
     * date: 2019/9/21
     */
    @RequestMapping("deleteLogic")
    public JsonResult deleteLogic(@RequestBody Map<String, Object> map) {
        return JsonResult.buildSuccess(studentService.deleteLogic(map));
    }
}
