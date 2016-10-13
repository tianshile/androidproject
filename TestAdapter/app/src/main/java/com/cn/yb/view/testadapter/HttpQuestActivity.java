package com.cn.yb.view.testadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HttpQuestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_quest);
    }


    private String getHttp(){
        //1、你要访问的url地址
        String url="http://localhost:8088/shopping/shop?request=login&uname=admin&pass=admin";
        //2、创建请求的对象
        HttpGet get=new HttpGet(url);


    }


}
