package com.osf.sp;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class TEST {

	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@bts.chrnu6fdargl.ap-northeast-2.rds.amazonaws.com:1588:ORCL";
	private static final String USER = "bts";
	private static final String PW = "1q2w3e4r!";
	
	@Resource
	private SqlSession ss;
	@Resource
	private DataSource ds;
	
	/*
	 * @Test public void testConnection() throws Exception{
	 * 
	 * Class.forName(DRIVER); try(Connection con = DriverManager.getConnection(URL,
	 * USER, PW)){ System.out.println(con); }catch(Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * }
	 */
	@Test
	public void test() {
		Connection con;
		try {
			con = ds.getConnection();
			assertNotNull(con);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		con = ss.getConnection();
		assertNotNull(con);
		try {
			con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
