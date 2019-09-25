package com.springboot.demo.mapper.provider;

import com.springboot.demo.entity.Menu;
import org.apache.ibatis.jdbc.SQL;
import java.lang.reflect.Field;
import com.springboot.demo.utils.StrUtils;
import java.util.Map;

/**
 * desc：
 * author CDN
 * create 2019-09-26
 * version 1.0.0
 */
public class MenuProvider {

    private final static String tableName = "menu";

      /**
     * desc: 新增
     * param:
     * author: CDN
     * date: 2019-09-26
     */
    public String insert(Map<String, Object> map) {
        SQL sql = new SQL();
        if (!StrUtils.isNullOrEmpty(map)) {
            sql.INSERT_INTO(tableName);
           if (!StrUtils.isNullOrEmpty(map.get("menuId"))){
               sql.VALUES("menu_id","#{menuId}");
           }
           if (!StrUtils.isNullOrEmpty(map.get("sName"))){
               sql.VALUES("s_name","#{sName}");
           }
           if (!StrUtils.isNullOrEmpty(map.get("studentPid"))){
               sql.VALUES("student_pid","#{studentPid}");
           }
           if (!StrUtils.isNullOrEmpty(map.get("status"))){
               sql.VALUES("status","#{status}");
           }
        }
        return sql.toString();
    }

  

  /**
     * desc:条件删除
     * param:
     * return:
     * author: CDN
     * date: 2019-09-26
     */
    public  String deleteByCondition(Map<String, Object> map) {
        SQL sql = new SQL();
        if (!StrUtils.isNullOrEmpty(map)) {
            sql.DELETE_FROM(tableName);
            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
                sql.WHERE(stringObjectEntry.getKey()+"="+stringObjectEntry.getValue());
            }
        }
        return sql.toString();
    }

  /**
     * desc: 根据主键更新
     * param:
     * return:
     * author: CDN
     * date: 2019-09-26
     */
    public String update(Menu menu) {
        SQL sql = new SQL();
        if (!StrUtils.isNullOrEmpty(menu)) {
            sql.UPDATE(tableName).WHERE("menu_id=#{menuId}");
            for (Field field : menu.getClass().getDeclaredFields()) {
                if("menuId".equals(field.getName())){
                     continue ;
                 }
                if("serialVersionUID".equals(field.getName())){
                     continue ;
                 }
                sql.SET(StrUtils.camel2Underline(field.getName()) + "=#{" + field.getName() + "}");
            }
        }
        return sql.toString();
    }

   /**
     * desc: 查询
     * date: 2019-09-26
     */
    public  String find(Map<String, Object> map) {
        SQL sql = new SQL().SELECT("*").FROM(tableName);
        if (map != null) {
           if (!StrUtils.isNullOrEmpty(map.get("menuId"))){
            sql.WHERE("menu_id=#{menuId}");
	        }
           if (!StrUtils.isNullOrEmpty(map.get("sName"))){
            sql.WHERE("s_name=#{sName}");
	        }
           if (!StrUtils.isNullOrEmpty(map.get("studentPid"))){
            sql.WHERE("student_pid=#{studentPid}");
	        }
           if (!StrUtils.isNullOrEmpty(map.get("status"))){
            sql.WHERE("status=#{status}");
	        }
                 }
        return sql.toString();
    }



    /**
     * desc: 查询单条记录
     * author: CDN
     * date: 2019-09-26
     */
    public String findSingle(Map<String, Object> map) {
        SQL sql = new SQL().SELECT("*").FROM(tableName);
        if (!StrUtils.isNullOrEmpty(map)) {
            sql.WHERE("menuId=#{menu_id}");
        }
        return sql.toString();
    }

    
}