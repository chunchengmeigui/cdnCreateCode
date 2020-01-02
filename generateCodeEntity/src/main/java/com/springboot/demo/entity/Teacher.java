package com.springboot.demo.entity;
import java.util.Date;
import java.sql.*;
import java.lang.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

   /**
    * @ time：2020-01-02 14:06:24
    * @ author：CDN 
    * @ desc：teacher 实体类
    */ 

public class Teacher  implements  Serializable{

	private static final long serialVersionUID =  3279668117081010709L;

	@JsonProperty("tecId")
	private Integer tecId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("age")
	private Integer age;

	@JsonProperty("createTime")
	private Date createTime;

	public void setTecId(Integer tecId){
	this.tecId=tecId;
	}
	public Integer getTecId(){
		return tecId;
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
}

