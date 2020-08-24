package com.luo.Controller;

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;
import com.luo.utils.MyFont;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

public class Index extends JFrame implements ActionListener,MouseListener{
    //定义光标
    Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
    //定义组件
    JMenuBar jMenuBar;
    //一级菜单
    JMenu jMenu1,jMenu2,jMenu3,jMenu4,jMenu5;
    //二级菜单
    JMenuItem jMenuItem1,jMenuItem2,jMenuItem3,jMenuItem4;
    //需要的五个Jpanel
    JPanel jPanel1,jPanel2,jPanel3,jPanel4,jPanel5;
    //显示当前时间
    JLabel showTime;
    javax.swing.Timer t;

    //定义P1里的Jlable
    JLabel P1_l1,P1_l2,P1_l3,P1_l4;

    JSplitPane jsp;

    CardLayout cardLayout;
    public static void main(String[] args) {
        Index index = new Index();

    }
    public Index(){
        //设置大小
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setSize(w,h-40);
        //显示
        this.setVisible(true);
        //关闭应用，退出JVM
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置title
        this.setTitle("主页");

        //创建一级菜单
        jMenu1 = new JMenu("调度");
        jMenu1.setFont(MyFont.f1);
        //创建他的二级菜单
        jMenuItem1 = new JMenuItem("调度1");
        jMenuItem1.setFont(MyFont.f2);
        jMenuItem2 = new JMenuItem("调度2");
        jMenuItem2.setFont(MyFont.f2);
        jMenuItem3 = new JMenuItem("调度3");
        jMenuItem3.setFont(MyFont.f2);
        jMenuItem4 = new JMenuItem("调度4");
        jMenuItem4.setFont(MyFont.f2);
        //将二级菜单加入到一级菜单
        jMenu1.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jMenuItem3);
        jMenu1.add(jMenuItem4);
        jMenu2 = new JMenu("交易");
        jMenu2.setFont(MyFont.f1);
        jMenu3 = new JMenu("终端");
        jMenu3.setFont(MyFont.f1);
        jMenu4 = new JMenu("任务");
        jMenu4.setFont(MyFont.f1);
        jMenu5 = new JMenu("结算");
        jMenu5.setFont(MyFont.f1);

        //把一级菜单加入到大的JmenuBar
        jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        jMenuBar.add(jMenu3);
        jMenuBar.add(jMenu4);
        jMenuBar.add(jMenu5);

        //把JmenuBar添加到JFrame中去
        this.setJMenuBar(jMenuBar);

        //处理P1面板
        jPanel1 = new JPanel(new BorderLayout());
        this.jPanel1.setLayout(new GridLayout(4,1));
        P1_l1 = new JLabel("登录管理",0);
        P1_l1.setEnabled(false);
        P1_l1.setFont(MyFont.f0);
        P1_l1.addMouseListener(this);
        P1_l1.setCursor(cursor);


        P1_l2 = new JLabel("接口管理",0);
        P1_l2.setEnabled(false);
        P1_l2.addMouseListener(this);
        P1_l2.setFont(MyFont.f0);
        P1_l2.setCursor(cursor);



        P1_l3 = new JLabel("jenkins",0);
        P1_l3.setEnabled(false);
        P1_l3.addMouseListener(this);
        P1_l3.setCursor(cursor);
        P1_l3.setFont(MyFont.f0);
        P1_l4 = new JLabel("帮助",0);
        P1_l4.setEnabled(false);
        P1_l4.setFont(MyFont.f0);
        jPanel1.add(P1_l1);
        jPanel1.add(P1_l2);
        jPanel1.add(P1_l3);
        jPanel1.add(P1_l4);

        //处理P2  P3  P4面板
        jPanel4 = new JPanel(new BorderLayout());
        jPanel2 = new JPanel(new CardLayout());

        this.cardLayout = new CardLayout();
        jPanel3 = new JPanel(this.cardLayout);


        //对P3做添加几个JLable
        JLabel re = new JLabel(new ImageIcon("C:\\Users\\Administrator\\IdeaProjects\\MyTool\\src\\main\\java\\com\\luo\\Images\\login.gif"));
        JLabel re2 = new JLabel(new ImageIcon("C:\\Users\\Administrator\\IdeaProjects\\MyTool\\src\\main\\java\\com\\luo\\Images\\1.png"));
        UserShow userShow = new UserShow();
        CaseShow caseShow = new CaseShow();
        JenkinsShow jenkinsShow = new JenkinsShow();
        jPanel3.add(userShow,"1");
        jPanel3.add(caseShow,"2");
        jPanel3.add(jenkinsShow,"3");
        //把P2  P3加入到P4中去
        jPanel4.add(jPanel2,"West");
        jPanel4.add(jPanel3,"Center");
        //做一个拆分窗口
        jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,jPanel1,jPanel4);
        jsp.setDividerSize(0);
        //指定左边占多大
        jsp.setDividerLocation(240);



        //处理P5面板
        jPanel5 = new JPanel(new BorderLayout());
        t = new Timer(1000,this);
        t.start();
        showTime = new JLabel(Calendar.getInstance().getTime().toLocaleString());
        jPanel5.add(showTime,"East");
        jPanel5.setFont(MyFont.f0);

        //从JFrame中取得Container
        Container container = this.getContentPane();
        container.add(jsp,"Center");
        container.add(jPanel5,"South");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.showTime.setText("当前时间："+Calendar.getInstance().getTime().toLocaleString()+"   ");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==P1_l1){
            this.cardLayout.show(jPanel3,"1");
        }else if(e.getSource()==P1_l2){
            this.cardLayout.show(jPanel3,"2");
        }else if(e.getSource()==P1_l3){
            this.cardLayout.show(jPanel3,"3");
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==P1_l1){
            this.P1_l1.setEnabled(true);
        }else if(e.getSource()==P1_l2){
            this.P1_l2.setEnabled(true);
        }else if(e.getSource()==P1_l3){
            this.P1_l3.setEnabled(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==P1_l1){
            this.P1_l1.setEnabled(false);
        }else if(e.getSource()==P1_l2){
            this.P1_l2.setEnabled(false);
        }else if(e.getSource()==P1_l3){
            this.P1_l3.setEnabled(false);
        }
    }
}
