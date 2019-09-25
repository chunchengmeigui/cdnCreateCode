package com.springboot.demo.entity;
import java.lang.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

   /**
    * @ time：2019-09-26 00:42:37
    * @ author：CDN 
    * @ desc：menu 实体类
    */ 

public class Menu implements Serializable{

	private static final long serialVersionUID =  7693456757152339369L;

	@JsonProperty("menuId")
	private String menuId;

	@JsonProperty("sName")
	private String sName;

	@JsonProperty("studentPid")
	private Integer studentPid;

	@JsonProperty("status")
	private Integer status;

	public void setMenuId(String menuId){
	this.menuId=menuId;
	}
	public String getMenuId(){
		return menuId;
	}
	public void setSName(String sName){
	this.sName=sName;
	}
	public String getSName(){
		return sName;
	}
	public void setStudentPid(Integer studentPid){
	this.studentPid=studentPid;
	}
	public Integer getStudentPid(){
		return studentPid;
	}
	public void setStatus(Integer status){
	this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
}

