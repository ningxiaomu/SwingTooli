package com.luo.model;

import com.luo.domain.MyCaseInfo;
import com.luo.domain.UserInfo;
import com.luo.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;


public class CaseModel extends AbstractTableModel{
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
        this.colums = new Vector<String>();
        this.colums.add("用例ID");
        this.colums.add("用例名");
        this.colums.add("所属项目");
        this.colums.add("域名");
        this.colums.add("状态");
        this.rows = new Vector<Vector>();


        List<MyCaseInfo> caseInfoList = session.selectList("getCaseList");
        System.out.println(caseInfoList);
        for (int i = 0; i <caseInfoList.size() ; i++) {
            Vector<String> temp = new Vector<String>();
            temp.add(caseInfoList.get(i).getCaseid());
            temp.add(caseInfoList.get(i).getCaseName());
            temp.add(caseInfoList.get(i).getProject());
            temp.add(caseInfoList.get(i).getDomain());
            temp.add(caseInfoList.get(i).getStatusStr());
            rows.add(temp);
        }

    }

    @Override
    public int getRowCount() {
        return this.rows.size();
    }

    @Override
    public int getColumnCount() {

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
