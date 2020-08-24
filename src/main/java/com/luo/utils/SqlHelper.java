/*
 * �����ݿ��������
 * �����ݿ�Ĳ���������crud
 * ���ô洢����
 * 
 *ע�⣺����������ݿ�ʱ���������쳣���ʾδ��������JAR������������һ��ԭ�����SQL������﷨����
 *java.lang.ClassNotFoundException: com.microsoft.jdbc.sqlserver.SQLServerDviver
 * */
package com.luo.utils;
import java.util.*;
import java.sql.*;
public class SqlHelper {

	Connection ct=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	String driver="com.mysql.jdbc.Driver";
	String url="jdbc:mysql://rm-8vb58j90l8o3ydhuyxo.mysql.zhangbei.rds.aliyuncs.com:3306/case";
	String user="luobo";
	String passwd="llp52638487@";
	

	public SqlHelper()
	{
		try {

			Class.forName(driver);
			ct=DriverManager.getConnection(url,user,passwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet query(String sql,String []paras)
	{
		try {
			ps=ct.prepareStatement(sql);

			for(int i=0;i<paras.length;i++)
			{
				//ps.setString(i+1, paras[i]);
				ps.setString(i, paras[i]);
			}

			rs=ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return rs;
	}

	public void close()
	{
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(ct!=null) ct.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
