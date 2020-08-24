package com.luo.Controller;

import com.luo.model.CaseModel;
import com.luo.model.UserModel;
import com.luo.utils.MyFont;

import javax.swing.*;
import java.awt.*;

/**
 * 用于显示Case
 */
public class CaseShow extends JPanel {
    //定义需要的组件
    JPanel P1,P2,P3,P4,P5;
    JLabel P1_lab1,P3_lab1;
    JTextField P1_jtf1;
    JButton P1_jb1,P4_jb1,P4_jb2,P4_jb3,P4_jb4;
    JTable jTable;
    JScrollPane jScrollPane;

    //构造函数
    public CaseShow(){
        P1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        P1_lab1 = new JLabel("请输入用例ID");
        P1_lab1.setFont(MyFont.f2);
        P1_jtf1 = new JTextField(30);
        P1_jb1 = new JButton("查询");
        P1_jb1.setFont(MyFont.f2);
        //把组件加入到组件
        P1.add(P1_lab1);
        P1.add(P1_jtf1);
        P1.add(P1_jb1);

        //处理中间
        CaseModel caseModel = new CaseModel();
        caseModel.query();
        jTable = new JTable(caseModel);
        P2 = new JPanel(new BorderLayout());
        jScrollPane = new JScrollPane(jTable);
        P2.add(jScrollPane);


        //处理南边
        P3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        P3_lab1 = new JLabel("总记录条数:");
        P3.add(P3_lab1);

        P4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        P4_jb1 = new JButton("修改");
        P4_jb2 = new JButton("删除");
        P4_jb3 = new JButton("添加");
        P4_jb4 = new JButton("详细");
        P4.add(P4_jb1);
        P4.add(P4_jb2);
        P4.add(P4_jb3);
        P4.add(P4_jb4);

        P5 = new JPanel(new BorderLayout());
        P5.add(P3,"West");
        P5.add(P4,"East");
        this.setLayout(new BorderLayout());
//        this.setBackground(Color.BLUE);
        this.add(P1,"North");
        this.add(P2,"Center");
        this.add(P5,"South");

        this.setVisible(true);
    }
}
