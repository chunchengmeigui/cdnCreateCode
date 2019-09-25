package com.springboot.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.demo.utils.MyException;
import com.springboot.demo.utils.StrUtils;
import com.springboot.demo.entity.Menu;
import com.springboot.demo.mapper.MenuMapper;
import com.springboot.demo.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * desc：
 * author CDN
 * create 2019-09-26
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    MenuMapper menuMapper;

    @Override
    public int insert(Map<String, Object> map) {
        if (StrUtils.isNullOrEmpty(map)) {
            throw new MyException("插入数据不能为空！");
        }
         map.put("menuId", UUID.randomUUID());
         
        return menuMapper.insert(map);
    }

    @Override
    public int deleteByCondition(Map<String, Object> map) {
        if ("".equals(map.get("menuId")) || null == map.get("menuId")) {
            throw new MyException("主键不能为空！");
        }
        return menuMapper.deleteByCondition(map);
    }

    @Override
    public int update(Menu obj) {
        if ("".equals(obj.getMenuId() + "") || null == obj.getMenuId()) {
            throw new MyException("主键不能为空！");
        }
        return menuMapper.update(obj);
    }

    @Override
    public  PageInfo<Menu> find(Map<String, Object> map) {

        int pageIndex = 0;
        int pageSize = 10;
        if (map.get("pageIndex") != null) {
            pageIndex = Integer.parseInt(map.get("pageIndex") + "");
        }
        if (map.get("pageSize") != null) {
            pageIndex = Integer.parseInt(map.get("pageSize") + "");
        }
        PageHelper.startPage(pageIndex, pageSize);
        List<Menu> list = menuMapper.find(map);

        return new PageInfo<>(list);
    }

    @Override
    public Menu findSingle(Map<String, Object> map) {
        if ("".equals(map.get("menuId")) || null == map.get("menuId")) {
            throw new MyException("主键不能为空！");
        }
        return menuMapper.findSingle(map);
    }
}
