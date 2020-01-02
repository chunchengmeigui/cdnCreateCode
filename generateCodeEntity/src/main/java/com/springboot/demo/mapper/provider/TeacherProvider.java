package com.springboot.demo.mapper.provider;

import com.springboot.demo.entity.Teacher;
import org.apache.ibatis.jdbc.SQL;
import java.lang.reflect.Field;
import com.springboot.demo.utils.StrUtils;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * desc：
 * author CDN
 * create 2020-01-02
 * version 1.0.0
 */
public class TeacherProvider {

    private final static String tableName = "teacher";

      /**
     * desc: 新增
     * param:
     * author: CDN
     * date: 2020-01-02
     */
    public String insert(Teacher teacher) {
        SQL sql = new SQL();
        if (!StrUtils.isNullOrEmpty(teacher)) {
            sql.INSERT_INTO(tableName);
            for (Field field : teacher.getClass().getDeclaredFields()) {
                sql.VALUES(StrUtils.camel2Underline(field.getName()), "#{" + field.getName() + "}");
            }
        }
        return sql.toString();
    }

  /**
     * desc:条件删除
     * param:
     * return:
     * author: CDN
     * date: 2020-01-02
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
     * date: 2020-01-02
     */
    public String update(Teacher teacher) {
        SQL sql = new SQL();
        if (!StrUtils.isNullOrEmpty(teacher)) {
            sql.UPDATE(tableName).WHERE("tec_id=#{tecId}");
            for (Field field : teacher.getClass().getDeclaredFields()) {
                if("tecId".equals(field.getName())){
                     continue ;
                 }
                sql.SET(StrUtils.camel2Underline(field.getName()) + "=#{" + field.getName() + "}");
            }
        }
        return sql.toString();
    }

   /**
     * desc: 查询
     * date: 2020-01-02
     */
    public  String find(Map<String, Object> map) {
        SQL sql = new SQL().SELECT("*").FROM(tableName);
        if (map != null) {
           if (!StrUtils.isNullOrEmpty(map.get("tecId"))){
            sql.WHERE("tec_id=#{tecId}");
	        }
           if (!StrUtils.isNullOrEmpty(map.get("name"))){
            sql.WHERE("name=#{name}");
	        }
           if (!StrUtils.isNullOrEmpty(map.get("age"))){
            sql.WHERE("age=#{age}");
	        }
           if (!StrUtils.isNullOrEmpty(map.get("createTime"))){
            sql.WHERE("create_time=#{createTime}");
	        }
                 }
        return sql.toString();
    }



    /**
     * desc: 查询单条记录
     * author: CDN
     * date: 2020-01-02
     */
    public String findSingle(Map<String, Object> map) {
        SQL sql = new SQL().SELECT("*").FROM(tableName);
        if (!StrUtils.isNullOrEmpty(map)) {
            sql.WHERE("tecId=#{tec_id}");
        }
        return sql.toString();
    }

    
}