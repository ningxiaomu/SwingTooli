package com.luo.Controller;
import com.alibaba.fastjson.JSONObject;
import com.luo.utils.MyFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.luo.utils.BaseClient;
public class JenkinsShow extends JPanel implements ActionListener {
    //定义组件
    JButton jButton1,jButton2;

    public JenkinsShow(){
        jButton1 = new JButton("一键触发");
        jButton2 = new JButton("测试报告");
        jButton1.addActionListener(this);
        jButton2.addActionListener(this);
        this.add(jButton1);
        this.add(jButton2);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() ==jButton1){

            //构建jenkinsurl
            String buildUrl="http://39.101.203.223:8081/job/api/build?token=664952305";
            String lastCompletedBuildNumberUrl="http://39.101.203.223:8081/job/api/lastCompletedBuild/api/json";
            String lastBuildNumberUrl="http://39.101.203.223:8081/job/api/lastBuild/api/json";

            //判断是否具有构建中的任务


            BaseClient client = new BaseClient();
            try {
                String lastBuildNumber;
                String lastCompletedBuildNumber;

                String a =BaseClient.Client(lastBuildNumberUrl);
                JSONObject jsonObject = JSONObject.parseObject(a);
                lastBuildNumber=jsonObject.getString("id");
                String b =BaseClient.Client(lastCompletedBuildNumberUrl);
                JSONObject jsonObject1 = JSONObject.parseObject(b);
                lastCompletedBuildNumber = jsonObject1.getString("id");
                if(lastBuildNumber.equals(lastCompletedBuildNumber)){
                    BaseClient.Client(buildUrl);
                    JOptionPane.showMessageDialog(this,"请求已发出","确定",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this,"目前Jenkins存在正在构建的项目，请稍后再试！","确定",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else if(e.getSource()==jButton2){
            String lastSuccessfulBuildUrl="http://39.101.203.223:8081/job/api/lastSuccessfulBuild/api/json";
            //测试报告
            Desktop desktop = Desktop.getDesktop();
            try {
                String a =BaseClient.Client(lastSuccessfulBuildUrl);
                JSONObject jsonObject = JSONObject.parseObject(a);
                String lastSuccessfulBuildNumber=jsonObject.getString("id");
                String url = "http://39.101.203.223:8080/"+lastSuccessfulBuildNumber.toString()+"/index.html";
                desktop.browse(new URI(url));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }
}
