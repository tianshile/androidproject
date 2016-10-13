package com.cn.yb.view.testadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 通过android提供的SimpleAdapter适配器，将数组或集合数据绑定ListView组件上显示
 * <p/>
 * 适配器是一种桥模式，将两者不相干的东西连接在一起。
 * <p/>
 * 作用：界面和数据的分离，达到重复利用、低耦合的效果。
 * <p/>
 * 开发步骤：
 * 1、创建ListView布局界面
 * 2、自定义ListView组件中要显示的item布局
 * 2、创建要显示的数据集
 * 3、创建适配器对象并绑定数据到item布局的组件上
 * 4、注册适配器到ListView组件上
 * <p/>
 * 注意：SimpleAdapter适配器绑定的数据集只能是List<Map<String,Object>>集合
 */
public class SimpleAdapterActivity extends AppCompatActivity {
    private ListView lv;
    private List<Map<String, Object>> str_list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter);
        //创建LisView组件对象
        lv = (ListView) this.findViewById(R.id.simpleAdapter_listivew);
        //自己创建数据集,扩展 可以从网络、数据库、硬盘、sd卡得到数据集。
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("img", R.drawable.c_01);
        map.put("title", "小宗");
        map.put("info", "电台DJ");
        str_list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.c_02);
        map.put("title", "貂蝉");
        map.put("info", "四大美女");
        str_list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.c_11);
        map.put("title", "奶茶");
        map.put("info", "清纯妹妹");
        str_list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.c_12);
        map.put("title", "大黄");
        map.put("info", "是小狗");
        str_list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.c_13);
        map.put("title", "hello");
        map.put("info", "every thing");
        str_list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.c_14);
        map.put("title", "world");
        map.put("info", "hello world");
        str_list.add(map);

        //创建SimpleAdapter适配器对象。参数1，表示所在的activity，参数2，表示数据集，参数3，要显示数据的布局，参数4，数据集中要被显示的Map键，参数5，布局上的绑定数据的组件
        //SimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, str_list, R.layout.item_simple_adapter, new String[]{"img", "title", "info"}, new int[]{R.id.imageView, R.id.title, R.id.info});
        //注册适配器到组件上
        lv.setAdapter(simpleAdapter);
    }


    /**
     *
     */
}
