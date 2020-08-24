package com.luo.Controller;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


import com.luo.domain.UserInfo;
import com.luo.utils.*;
import org.apache.ibatis.session.SqlSession;
import org.aspectj.lang.JoinPoint;

public class UserLogin extends JDialog implements ActionListener {

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


    //LoginBackImage bgimage=null; //背景图片
    paint p=null;  //闪屏

    JLabel jb1,jb2,jb3;
    JTextField juserName;
    JPasswordField   jPwd;

    JButton jconfirm,jcancel;

    MyFont myFont = new MyFont();

    public static void main(String[] args) {

        UserLogin login=new UserLogin();
        System.out.println("启动成功");

    }

    public UserLogin()
    {
        jb1=new JLabel("用户账号:");
        jb1.setFont(myFont.f1);
        //设置jb1在容器中的位置和大小,注意：只有在空布局中才使用此设置
        jb1.setBounds(60, 190, 150, 30);
        this.add(jb1);


        juserName=new JTextField();
        juserName.setBounds(180,190,120,30);
        //设置下凹
        juserName.setBorder(BorderFactory.createLoweredBevelBorder());
        this.add(juserName);



        jb3=new JLabel("用户密码:");
        jb3.setBounds(60,240,150,30);
        jb3.setFont(myFont.f1);
        this.add(jb3);

        jPwd=new JPasswordField();
        jPwd.setBounds(180,240,120,30);
        //设置边框下凹
        jPwd.setBorder(BorderFactory.createLoweredBevelBorder());
        this.add(jPwd);


        jconfirm=new JButton("确定");
        jconfirm.setBounds(105,300,75,30);
        jconfirm.setFont(myFont.f1);
        jconfirm.addActionListener(this);
        this.add(jconfirm);

        jcancel=new JButton("取消");
        jcancel.setBounds(215,300,75,30);
        jcancel.setFont(myFont.f1);
        jcancel.addActionListener(this);
        this.add(jcancel);


        //空布局
        this.setLayout(null);
        this.setSize(360,360);

        //加背景图片
//        bgimage=new LoginBackImage();
//        bgimage.setBounds(0, 0, 360, 360);
//        this.add(bgimage);

        //加闪屏效果
        p=new paint();
        this.add(p);
        //取掉边框修饰效果
        this.setUndecorated(true);
        //窗体居中
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id=null;

        if(e.getSource()==jconfirm)
        {
            //取出用户输入的账号和密码
            String username = this.juserName.getText().trim();
            String pwd =new String(this.jPwd.getPassword()) ;
            //定义重数据库取的密码(和上面的pwd做对比，如果一样，就需要跳到下一页面)


            UserInfo userInfo = session.selectOne("testOne",username);

            if(userInfo==null){
                JOptionPane.showMessageDialog(this,"账号不存在或者密码不匹配，请重新输入","确定",JOptionPane.INFORMATION_MESSAGE);
            }else{
                if(pwd.equals(userInfo.getPassword())){

                    new Index();
                    this.dispose();
                }else{
                    JOptionPane.showMessageDialog(this,"账号存在但是密码不匹配，请重新输入","确定",JOptionPane.INFORMATION_MESSAGE);
                }
            }

        }else if(e.getSource()==jcancel)
        {

            System.exit(0);
        }
    }
}
/*
 * 登陆截面的背景图片
 */
//class LoginBackImage extends JPanel
//{
//    Image bg=null;
//    LoginBackImage()
//    {
//        try {
//            bg=ImageIO.read(new File("Images/login.gif"));
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//
//    }
//    public void paintComponent(Graphics g)
//    {
//        g.drawImage(bg, 0, 0,360,360,this);
//    }
//}
