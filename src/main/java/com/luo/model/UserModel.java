package com.luo.model;

/**
 * 表格处理
 */
import com.luo.domain.UserInfo;
import com.luo.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class UserModel extends AbstractTableModel {
    //建立session连接
    SqlSession session;

    {
        try {
            session = DatabaseUtil.getSqlSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Vector<String> colums; //列
    Vector<Vector> rows; //行



    public void query(){
        //查询所有
        this.colums = new Vector<String>();
        this.colums.add("用户ID");
        this.colums.add("用户名");
        this.colums.add("密码");
        this.colums.add("邮箱");
        this.colums.add("手机号");
        this.colums.add("更新时间");
        this.colums.add("状态");
        this.rows = new Vector<Vector>();


        List<UserInfo> userInfoList = session.selectList("getUserList");
        System.out.println(userInfoList);
        for (int i = 0; i <userInfoList.size() ; i++) {
            Vector<String> temp = new Vector<String>();
            temp.add(userInfoList.get(i).getUserid());
            System.out.println(userInfoList.get(i).getUserid());
            temp.add(userInfoList.get(i).getUsername());
            temp.add(userInfoList.get(i).getPassword());
            temp.add(userInfoList.get(i).getEmail());
            temp.add(userInfoList.get(i).getPhoneNum());
            temp.add(userInfoList.get(i).getJoinTime());
            temp.add(userInfoList.get(i).getStatusStr());
            rows.add(temp);
        }

    }

    public void queryPageUser(Integer pageNum){
        //分页查询
        this.colums = new Vector<String>();
        this.colums.add("用户ID");
        this.colums.add("用户名");
        this.colums.add("密码");
        this.colums.add("邮箱");
        this.colums.add("手机号");
        this.colums.add("更新时间");
        this.colums.add("状态");
        this.rows = new Vector<Vector>();


        List<UserInfo> userInfoList = session.selectList("getUserPageList",pageNum);
        System.out.println(userInfoList);
        for (int i = 0; i <userInfoList.size() ; i++) {
            Vector<String> temp = new Vector<String>();
            temp.add(userInfoList.get(i).getUserid());
            temp.add(userInfoList.get(i).getUsername());
            temp.add(userInfoList.get(i).getPassword());
            temp.add(userInfoList.get(i).getEmail());
            temp.add(userInfoList.get(i).getPhoneNum());
            temp.add(userInfoList.get(i).getJoinTime());
            temp.add(userInfoList.get(i).getStatusStr());
            rows.add(temp);
        }

    }

    public void queryOneUser(String username){
        //查询某一条
        this.colums = new Vector<String>();
        this.colums.add("用户ID");
        this.colums.add("用户名");
        this.colums.add("密码");
        this.colums.add("邮箱");
        this.colums.add("手机号");
        this.colums.add("更新时间");
        this.colums.add("状态");
        this.rows = new Vector<Vector>();


        UserInfo userInfo = session.selectOne("findOneUser",username);
        System.out.println(userInfo);
        if(userInfo==null){
            System.out.println("未找到你要查询的用户信息");
            Vector<String> temp = new Vector<String>();
            temp.add("未找到您所需要的数据");
            temp.add("未找到您所需要的数据");
            temp.add("未找到您所需要的数据");
            temp.add("未找到您所需要的数据");
            temp.add("未找到您所需要的数据");
            temp.add("未找到您所需要的数据");
            temp.add("未找到您所需要的数据");
            rows.add(temp);
        }else {
            Vector<String> temp = new Vector<String>();
            temp.add(userInfo.getUserid());
            temp.add(userInfo.getUsername());
            temp.add(userInfo.getPassword());
            temp.add(userInfo.getEmail());
            temp.add(userInfo.getPhoneNum());
            temp.add(userInfo.getJoinTime());
            temp.add(userInfo.getStatusStr());
            rows.add(temp);
        }
    }




    @Override
    public int getRowCount() {
        return this.rows.size();
    }

    @Override
    public int getColumnCount() {
//        return 0;
        return this.colums.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((String) rows.get(rowIndex).get(columnIndex));
    }

    @Override
    public String getColumnName(int a){
        return this.colums.get(a).toString();
    }
}
