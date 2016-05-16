package com.nju.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nju.model.UserInfo;
 


public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> {
	
	@Override
	protected void save(Connection conn, PreparedStatement stmt, UserInfo userInfo, String... params) throws SQLException {
		// TODO Auto-generated method stub
		String sql ="insert into user_degree_info(realName,sex,universityName,schoolName,major,level,startDate,date,authorId) values(?,?,?,?,?,?,?,now(),?)";
		String[] labels = userInfo.getLabel().split("-");
		stmt = conn.prepareStatement(sql);
		stmt.setString(1,userInfo.getName());
		stmt.setString(2,userInfo.getSex());
		stmt.setString(3,labels[1]);
		stmt.setString(4,userInfo.getSubSchoolName());
		stmt.setString(5,userInfo.getMajor());
		stmt.setString(6,labels[0]);
		
		DateFormat format = new SimpleDateFormat("yyyy'年'M'月'");
		try {
			Date date = format.parse(userInfo.getDate());
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(date);
			stmt.setInt(7,cal.get(Calendar.YEAR));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			stmt.setInt(7,0);
		}
		stmt.setInt(8,userInfo.getAuthorId());
		
		stmt.execute();
	}
 
	@Override
	protected UserInfo query(Connection conn, PreparedStatement stmt, ResultSet set, String... params)
			throws SQLException {
		UserInfo userInfo = null;
		String sql ="SELECT name FROM userInfo WHERE label_id=?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1,params[0]);
	    set = stmt.executeQuery();
		if(set.next()) {
			userInfo = new UserInfo();
			userInfo.setName(set.getString(1));
		}
		return userInfo;
	}

	 

	@Override
	protected void update(Connection conn, PreparedStatement stmt, UserInfo t, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete(Connection conn, PreparedStatement stmt, UserInfo t, String... params)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
