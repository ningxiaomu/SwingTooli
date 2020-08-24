package com.luo.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.io.IOException;

public class BaseClient {
    private static final Logger log = Logger.getLogger(BaseClient.class);

    /**
     * id -->传你在sqlMapper里的select id
     * caseid --> 你测试用例的id
     *
     * @param testuri
     * @throws IOException
     */

    public static String Client(String testuri) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();
        boolean flag;
        String result = null;
        //建立session连接
        SqlSession session = DatabaseUtil.getSqlSession();
        //获取用例
        try {
            // get请求
            //建立client对象
            DefaultHttpClient client = new DefaultHttpClient();
            //创建get请求
            HttpGet httpGet = new HttpGet(testuri);
            HttpResponse response = null;
            try {
                response = client.execute(httpGet);
                //判断响应状态码是200或者302
                if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 302) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //释放连接
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}


