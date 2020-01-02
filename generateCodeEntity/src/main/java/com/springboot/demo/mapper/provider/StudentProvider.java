package com.springboot.demo.mapper.provider;

import com.springboot.demo.entity.Student;
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
public class StudentProvider {

    private final static String tableName = "student";

    /**
     * desc: 新增
     * param:
     * author: CDN
     * date: 2020-01-02
     */
    public String insert(Student student) {
        SQL sql = new SQL();
        if (!StrUtils.isNullOrEmpty(student)) {
            sql.INSERT_INTO(tableName);
            for (Field field : student.getClass().getDeclaredFields()) {
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
    public String deleteByCondition(Map<String, Object> map) {
        SQL sql = new SQL();
        if (!StrUtils.isNullOrEmpty(map)) {
            sql.DELETE_FROM(tableName);
            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
                sql.WHERE(stringObjectEntry.getKey() + "=" + stringObjectEntry.getValue());
            }
        }
        return sql.toString();
    }

    /**
     * desc: 逻辑删除
     * param: 2 删除  1 正常
     * return:
     * author: CDN
     * date: 2019/9/20
     */
    public String logicDelete(Map<String, Object> map) {
        SQL sql = new SQL();
        if (!StrUtils.isNullOrEmpty(map)) {
            sql.UPDATE(tableName).SET("valid =2 ").WHERE("stu_id=#{stuId}");
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
    public String update(Student student) {
        SQL sql = new SQL();
        if (!StrUtils.isNullOrEmpty(student)) {
            sql.UPDATE(tableName).WHERE("stu_id=#{stuId}");
            for (Field field : student.getClass().getDeclaredFields()) {
                if ("stuId".equals(field.getName())) {
                    continue;
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
    public String find(Map<String, Object> map) {
        SQL sql = new SQL().SELECT("*").FROM(tableName);
        if (map != null) {
            if (!StrUtils.isNullOrEmpty(map.get("stuId"))) {
                sql.WHERE("stu_id=#{stuId}");
            }
            if (!StrUtils.isNullOrEmpty(map.get("name"))) {
                sql.WHERE("name like concat('%',#{name},'%')");
            }
            if (!StrUtils.isNullOrEmpty(map.get("age"))) {
                sql.WHERE("age=#{age}");
            }
            if (!StrUtils.isNullOrEmpty(map.get("createTime"))) {
                sql.WHERE("create_time=#{createTime}");
            }
            if (!StrUtils.isNullOrEmpty(map.get("valid"))) {
                sql.WHERE("valid=#{valid}");
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
            sql.WHERE("stuId=#{stu_id}");
        }
        return sql.toString();
    }


}