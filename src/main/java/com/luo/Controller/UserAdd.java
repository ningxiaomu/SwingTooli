package com.luo.Controller;

import com.luo.domain.UserInfo;
import com.luo.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import sun.awt.image.GifImageDecoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
public class UserAdd extends JDialog implements ActionListener {
    /**
     * @param args
     */
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
    JLabel jl1,jl2,jl3,jl4,jl5;
    JButton jb1,jb2;
    JTextField jtf1,jtf2,jtf3,jtf4,jtf5;
    JPanel jp1,jp2,jp3;

    //构造函数

    /**
     *  @param owner 父窗口
     * @param title title名字
     * @param model 是否未模式
     */

    public UserAdd(UserShow owner, String title, boolean model){
        //super(owner, title,model);
        jl1 = new JLabel("用户名");
        jl2 = new JLabel("密码");
        jl3 = new JLabel("邮箱");
        jl4 = new JLabel("手机号");
        jl5 = new JLabel("状态");

        jb1 = new JButton("确定");
        jb1.addActionListener(this);
        jb2 = new JButton("取消");
        jb2.addActionListener(this);

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

        //设置布局
        jp1.setLayout(new GridLayout(5,1));
        jp2.setLayout(new GridLayout(5,1));

        //添加组件
        jp1.add(jl1);
        jp1.add(jl2);
        jp1.add(jl3);
        jp1.add(jl4);
        jp1.add(jl5);

        jtf1 = new JTextField();
        jtf2 = new JTextField();
        jtf3 = new JTextField();
        jtf4 = new JTextField();
        jtf5 = new JTextField();

        jp2.add(jtf1);
        jp2.add(jtf2);
        jp2.add(jtf3);
        jp2.add(jtf4);
        jp2.add(jtf5);

        jp3.add(jb1);
        jp3.add(jb2);

        this.add(jp1,BorderLayout.WEST);
        this.add(jp2,BorderLayout.CENTER);
        this.add(jp3,BorderLayout.SOUTH);



        //展现
        this.setSize(300,200);
        this.setLocation(500,500);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb1){
            String username = this.jtf1.getText().trim();
            String password = this.jtf2.getText().trim();
            String email = this.jtf3.getText().trim();
            String phoneNum = this.jtf4.getText().trim();
            String status = this.jtf5.getText().trim();
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setEmail(email);
            userInfo.setPhoneNum(phoneNum);
            Integer sta = null;
            try{
                sta = Integer.valueOf(status);
            }catch (Exception e1){
                e1.printStackTrace();
            }


            if(sta!=0&&sta!=1){
                JOptionPane.showMessageDialog(this,"状态只能是1或者0","确定",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else if(sta==null) {
                JOptionPane.showMessageDialog(this,"状态只能是1或者0","确定",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else {
                userInfo.setStatus(sta);
                int result = session.insert("insertUser",userInfo);
                session.commit();
                if(result==1){
                    //添加成功
                    JOptionPane.showMessageDialog(this,"添加成功","确定",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    //添加失败
                    JOptionPane.showMessageDialog(this,"添加失败","确定",JOptionPane.INFORMATION_MESSAGE);
                }
                this.dispose();
            }
        }else if(e.getSource()==jb2){
            this.dispose();
        }
    }
}
