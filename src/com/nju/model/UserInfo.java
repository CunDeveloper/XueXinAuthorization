package com.nju.model;

/**
 * Created by xiaojuzhang on 2015/11/12.
 */
public class UserInfo {

    private String label;
    private String name;
    private String sex;
    private String schoolName;
    private String subSchoolName;
    private String major;
    private String date;
    private int authorId;
    
    public UserInfo(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchoolNameName() {
        return schoolName;
    }

    public void setSchoolName(String yuanXiaoName) {
        this.schoolName = yuanXiaoName;
    }

    public String getSubSchoolName() {
        return subSchoolName;
    }

    public void setSubSchoolName(String fenYuan) {
        this.subSchoolName = fenYuan;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
}
