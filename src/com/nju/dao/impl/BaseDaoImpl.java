package com.nju.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nju.util.C3PODataSource;
import com.nju.util.Constant;

public abstract class BaseDaoImpl<T> {

	protected abstract void save(Connection conn,PreparedStatement stmt,T t,String...params) throws SQLException;
	protected abstract T query(Connection conn,PreparedStatement stmt,ResultSet set,String...params) throws SQLException;
	protected abstract void delete(Connection conn,PreparedStatement stmt,T t,String...params) throws SQLException;
	protected abstract void update(Connection conn,PreparedStatement stmt,T t,String...params) throws SQLException;
	
	public int save(T t,String...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = Constant.SQL_EXE_FALIURE;
		try {
			conn = C3PODataSource.getConn();
			save(conn,stmt,t,params);
			result = Constant.SQL_EXE_OK;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if (stmt != null)
					stmt.close();
				if (conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	 }
	
	 
	public int delete(T t,String...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = Constant.SQL_EXE_FALIURE;
		try {
			conn = C3PODataSource.getConn();
			delete(conn,stmt,t,params);
			result = Constant.SQL_EXE_OK;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if (stmt != null)
					stmt.close();
				if (conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	 }
	
	public int update(T t,String...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = Constant.SQL_EXE_FALIURE;
		try {
			conn = C3PODataSource.getConn();
			update(conn,stmt,t,params);
			result = Constant.SQL_EXE_OK;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if (stmt != null)
					stmt.close();
				if (conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return result;
	 }
	
	public T query(String...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		T t = null;
		try {
			conn = C3PODataSource.getConn();
			t = query(conn,stmt,resultSet,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			try {
				if(resultSet != null)
					resultSet.close();
				if (stmt != null)
					stmt.close();
				if (conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return t;
	 }
}
