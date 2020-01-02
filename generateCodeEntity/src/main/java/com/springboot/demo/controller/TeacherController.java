package com.springboot.demo.controller;

import com.github.pagehelper.PageInfo;
import com.springboot.demo.utils.JsonResult;
import com.springboot.demo.entity.Teacher;
import com.springboot.demo.service.TeacherService;
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
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;


    /**
     * desc: 新增
     * param:
     * author: CDN
     * date: 2020-01-02
     */
    @RequestMapping("insert")
    public JsonResult insert(@RequestBody Teacher teacher) {
        return JsonResult.buildSuccess(teacherService.insert(teacher));
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
        return JsonResult.buildSuccess(teacherService.deleteByCondition(map));
    }


    /**
     * desc: 修改
     * param:
     * author: CDN
     * date: 2020-01-02
     */
    @RequestMapping("update")
    public JsonResult update(@RequestBody Teacher teacher) {
        return JsonResult.buildSuccess(teacherService.update(teacher));
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
        PageInfo<Teacher> pageInfo = teacherService.find(map);
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
        return JsonResult.buildSuccess(teacherService.findSingle(map));
    }
}
