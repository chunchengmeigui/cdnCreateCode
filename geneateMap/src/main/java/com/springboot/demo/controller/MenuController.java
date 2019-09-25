package com.springboot.demo.controller;

import com.github.pagehelper.PageInfo;
import com.springboot.demo.utils.JsonResult;
import com.springboot.demo.entity.Menu;
import com.springboot.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * desc：
 * author CDN
 * create 2019-09-26
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    MenuService menuService;


    /**
     * desc: 新增
     * param:
     * author: CDN
     * date: 2019-09-26
     */
    @RequestMapping("insert")
    @Transactional
    public JsonResult insert(@RequestBody  Map<String, Object> map) {
        return JsonResult.buildSuccess(menuService.insert(map));
    }


    /**
     * desc: 条件删除
     * param:
     * return:
     * author: CDN
     * date: 2019-09-26
     */
    @RequestMapping("deleteByCondition")
    @Transactional
    public JsonResult deleteByCondition(@RequestBody  Map<String, Object> map) {
        return JsonResult.buildSuccess(menuService.deleteByCondition(map));
    }


    /**
     * desc: 修改
     * param:
     * author: CDN
     * date: 2019-09-26
     */
    @RequestMapping("update")
    @Transactional
    public JsonResult update(@RequestBody Menu menu) {
        return JsonResult.buildSuccess(menuService.update(menu));
    }


    /**
     * desc: 条件查询(分页)
     * param:
     * return:
     * author: CDN
     * date: 2019-09-26
     */
    @RequestMapping("getPageList")
    public JsonResult getPageList(@RequestParam  Map<String, Object> map) {
        PageInfo<Menu> pageInfo = menuService.find(map);
        return JsonResult.buildSuccess(pageInfo);
    }

    /**
     * desc: 单条记录
     * param:
     * return:
     * author: CDN
     * date: 2019-09-26
     */
    @RequestMapping("findSingle")
    public JsonResult findSingle(@RequestBody  Map<String, Object> map) {
        return JsonResult.buildSuccess(menuService.findSingle(map));
    }
}
