package com.businessmatrix;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

 
public class Connect_Hive_Db {
	/**
	 * 处理JDBC连接
	 * 
	 * @author Alias
	 *
	 */  
	public static int query(String querySql){
		int result= 0;
		Connection con = null;
        Statement sm = null;
        ResultSet rs = null;
		try {
			 con = JdbcDao.getConnection();
			 sm = con.createStatement();
			 rs = sm.executeQuery(querySql);
			if(rs.next())result=rs.getInt(1);//只获取第一个字段
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				JdbcDao.close(con, sm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public static boolean query1(String querySql){
		boolean result= true;
		Connection con = null;
        Statement sm = null;
     
		try {
			 con = JdbcDao.getConnection();
			 sm = con.createStatement();
			 result=  sm.execute(querySql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				JdbcDao.close(con, sm,null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		String sql1="set ngmr.exec.mode=local";
		Connect_Hive_Db.query1(sql1);
		String sql = "SELECT crm_dueDate FROM dc_ods.v_dc_crm_duedate t WHERE t.fundcode='202303' AND t.crm_subtype='100000006'";
		int result = Connect_Hive_Db.query(sql);
		System.out.println(result);
	}
	
	}
     
