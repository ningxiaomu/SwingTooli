package com.luo.Controller;

import java.awt.*;
import javax.swing.*;
public class CataLog extends JWindow implements Runnable  {

    /**
     * @param args
     */

    paint p=null;
    public static void main(String[] args) {

        CataLog ct=new CataLog();
        Thread t=new Thread(ct);
        t.start();
    }


    public CataLog()
    {
        this.setSize(400,240);

        //加闪屏
        p=new paint();
        this.add(p);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    @Override
    public void run() {

        while(true)
        {

            try {
                Thread.sleep(30*500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //启动登陆窗口
            new UserLogin();
            this.dispose();
        }
    }

}

