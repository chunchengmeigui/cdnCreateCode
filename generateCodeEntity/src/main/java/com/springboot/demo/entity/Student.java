package com.springboot.demo.entity;
import java.util.Date;
import java.sql.*;
import java.lang.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

   /**
    * @ time：2020-01-02 14:06:24
    * @ author：CDN 
    * @ desc：student 实体类
    */ 

public class Student  implements  Serializable{

	private static final long serialVersionUID =  4728880777620803479L;

	@JsonProperty("stuId")
	private Integer stuId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("age")
	private Integer age;

	@JsonProperty("createTime")
	private Date createTime;

	@JsonProperty("valid")
	private Integer valid;

	public void setStuId(Integer stuId){
	this.stuId=stuId;
	}
	public Integer getStuId(){
		return stuId;
	}
	public void setName(String name){
	this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setAge(Integer age){
	this.age=age;
	}
	public Integer getAge(){
		return age;
	}
	public void setCreateTime(Date createTime){
	this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setValid(Integer valid){
	this.valid=valid;
	}
	public Integer getValid(){
		return valid;
	}
}

