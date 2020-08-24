package com.luo.Controller;

/**
 * 用于显示用户
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import com.luo.model.UserModel;
import com.luo.utils.DatabaseUtil;
import com.luo.utils.MyFont;
import org.apache.ibatis.session.SqlSession;

public class UserShow extends JPanel implements ActionListener{
    //建立session连接
    SqlSession session;

    {
        try {
            session = DatabaseUtil.getSqlSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //定义需要的组件
    JPanel P1,P2,P3,P4,P5,P6;
    JLabel P1_lab1,P3_lab1,P1_lab2;
    JTextField P1_jtf1;
    JButton P1_jb1,P4_jb1,P4_jb2,P4_jb3,P4_jb4,P4_jb5,P4_jb6,P4_jb7,P4_jb8;
    JTable jTable;
    JScrollPane jScrollPane;

    UserModel userModel;

    //页码处理
    int userCount; //总的用户数
    int totalPage; //总的页码
    int currentPage=0;//当前页码

    //构造函数
    public UserShow(){
        P1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        P1_lab1 = new JLabel("请输入账号");
        P1_lab1.setFont(MyFont.f2);
        P1_jtf1 = new JTextField(30);

        P1_lab2 = new JLabel("当前页码：");

        P1_jb1 = new JButton("查询");
        P1_jb1.addActionListener(this);
        P1_jb1.setFont(MyFont.f2);
        //把组件加入到组件
        P1.add(P1_lab1);
        P1.add(P1_jtf1);
        P1.add(P1_jb1);

        //处理中间


        userModel = new UserModel();
        userModel.query();
        jTable = new JTable(userModel);
        P2 = new JPanel(new BorderLayout());
        jScrollPane = new JScrollPane(jTable);
        P2.add(jScrollPane);


        //处理南边
        //获取总的记录条数
        userCount = session.selectOne("getUserCount");
        //获取总的页码
        if(userCount%10==0){
            totalPage = userCount/50;
        }else {
            totalPage = userCount/50+1;
        }
        //当前页码
        currentPage = 1;


        P3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        P3_lab1 = new JLabel("总记录条数:"+userCount+"; 每页条数:10");
        P3.add(P3_lab1);

        //首页/上一页/下一页/尾页

        P6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        P4_jb7 = new JButton("首页");
        P4_jb7.addActionListener(this);


        P4_jb5 = new JButton("上一页");
        P4_jb5.addActionListener(this);
        P4_jb6 = new JButton("下一页");
        P4_jb6.addActionListener(this);

        P4_jb8 = new JButton("尾页");
        P4_jb8.addActionListener(this);

        P6.add(P4_jb7);
        P6.add(P4_jb5);
//        for (int i = 0; i <totalPage ; i++) {
//            JButton jButton = new MyButton(i+1);
//            //注册监听时间
//            jButton.addActionListener(this);
//            //获取button的值
//            String value = jButton.getText();
//            P6.add(jButton);
//        }

        P6.add(P4_jb6);
        P6.add(P4_jb8);

        P4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        P4_jb1 = new JButton("修改");
        P4_jb1.addActionListener(this);
        P4_jb2 = new JButton("删除");
        P4_jb2.addActionListener(this);
        P4_jb3 = new JButton("添加");
        P4_jb3.addActionListener(this);
        P4_jb4 = new JButton("刷新");
        P4_jb4.addActionListener(this);
        P4.add(P4_jb1);
        P4.add(P4_jb2);
        P4.add(P4_jb3);
        P4.add(P4_jb4);

        P5 = new JPanel(new BorderLayout());
        P5.add(P3,"West");
        P5.add(P6,"Center");
        P5.add(P4,"East");
        this.setLayout(new BorderLayout());
//        this.setBackground(Color.BLUE);
        this.add(P1,"North");
        this.add(P2,"Center");
        this.add(P5,"South");

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==P1_jb1){
            //根据用户名查询某一条记录
            String username = this.P1_jtf1.getText().trim();
            userModel = new UserModel();
            userModel.queryOneUser(username);
            jTable.setModel(userModel);

        }
        //点击添加
        else if(e.getSource()==P4_jb3){
            UserAdd userAdd = new UserAdd(this,"添加用户",true);
            userModel = new UserModel();
            userModel.queryPageUser(currentPage*10);
            jTable.setModel(userModel);
        }else if(e.getSource()==P4_jb2){
            //删除
            //获取选中的那行
            int row = this.jTable.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this,"请选择一行","确定",JOptionPane.INFORMATION_MESSAGE);
                return;
            }


            //二次确认
            int r=JOptionPane.showConfirmDialog(this,"确认删除此条数据","再次确认",JOptionPane.YES_NO_OPTION);
            if(r==JOptionPane.YES_OPTION){
                //是真的要删除
                //根据boolean值判断是否删除成功
                String userid = (String) userModel.getValueAt(row,0);
                int flag = session.delete("delUserById",userid);
                session.commit();
                String tag = "成功删除："+userid;
                if(flag == 1){
                    JOptionPane.showMessageDialog(this,tag,"确定",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this,"删除失败","确定",JOptionPane.INFORMATION_MESSAGE);
                }

            }else if(r==JOptionPane.NO_OPTION){
                //取消删除
                this.disable();
            }
            userModel = new UserModel();
            userModel.queryPageUser(currentPage*10);
            jTable.setModel(userModel);
        }else if(e.getSource()==P4_jb1){
            //修改
            //获取选中的那行
            int row = this.jTable.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this,"请选择一行","确定",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            UserModify userModify = new UserModify(this,"修改用户",true,userModel,row);
            userModel = new UserModel();
            userModel.queryPageUser(currentPage*10);
            jTable.setModel(userModel);
        } else if(e.getSource() == P4_jb4){
            //刷新
            userModel = new UserModel();
            userModel.queryPageUser(currentPage*10);
            jTable.setModel(userModel);
        }else if(e.getSource() == P4_jb6){
            //下一页
            //判断是否是最后一页
            userCount = session.selectOne("getUserCount");
            totalPage = userCount/10;
            if(currentPage == totalPage){
                //是最后一页
                JOptionPane.showMessageDialog(this,"已是最后一页","确定",JOptionPane.INFORMATION_MESSAGE);
                return;
            }else {
                //不是最后一页

                Integer pageNum = 10;
                pageNum = ((currentPage+1)*10);
                userModel = new UserModel();
                userModel.queryPageUser(pageNum);
                jTable.setModel(userModel);
                currentPage++;


            }
        }else if(e.getSource() == P4_jb5){
            //上一页
            //判断是否是第一页
            if(currentPage==0){
                //是
                JOptionPane.showMessageDialog(this,"已是第一页","确定",JOptionPane.INFORMATION_MESSAGE);
                return;
            }else{
                userCount = session.selectOne("getUserCount");
                //获取总的页码
                if(userCount%10==0){
                    totalPage = userCount/10;
                }else {
                    totalPage = userCount/10+1;
                }

                //当前页码
                Integer pageNum = 10;
                pageNum = ((currentPage-1)*10);
                userModel = new UserModel();
                userModel.queryPageUser(pageNum);
                jTable.setModel(userModel);
                currentPage--;
            }
        }else if(e.getSource()==P4_jb7){
            //首页
            currentPage=0;
            userModel = new UserModel();
            userModel.queryPageUser(0);
            jTable.setModel(userModel);
        }else if(e.getSource()==P4_jb8){
            //尾页
            userCount = session.selectOne("getUserCount");
            totalPage = userCount/10;
            currentPage = totalPage;
            userModel = new UserModel();
            userModel.queryPageUser(totalPage*10);
            jTable.setModel(userModel);

        }
    }
}

//自定义一个BUTTON类
class MyButton extends JButton{
    public MyButton (int index){
        JButton jButton = new JButton(String.valueOf(index));
        jButton.setSize(10,10);
        add(jButton);
    }
        }
