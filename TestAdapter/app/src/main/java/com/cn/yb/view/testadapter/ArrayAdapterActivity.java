package com.cn.yb.view.testadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * 通过android提供的ArrayAdapter适配器，将数组或集合数据绑定ListView组件上显示
 *
 * 适配器是一种桥模式，将两者不相干的东西连接在一起。一个是数据集（数据或集合），显示的组件
 *
 * 作用：界面和数据的分离，达到重复利用、低耦合的效果。
 *
 * 开发步骤：
 * 1、创建ListView布局界面
 * 2、创建要显示的数据集
 * 3、创建适配器对象并绑定数据到item布局的组件上
 * 4、注册适配器到ListView组件上
 *
 * 注意：ArrayAdapter适配器绑定的数据集只能是String类型的数组或者List<String>集合
 */
public class ArrayAdapterActivity extends AppCompatActivity {
    private ListView lv;
    private String strs[];//数据集
//    private List<String>
// =new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter);
        //创建LisView组件对象
        lv = (ListView)this.findViewById(R.id.simpleAdapter_listivew);
        //从资源文件中得到数据集,扩展 可以从网络、数据库、硬盘、sd卡得到数据集。
        strs=this.getResources().getStringArray(R.array.province);
//        for (int i=0;i<strs.length;i++){
//            str_list.add(strs[i]);
//        }
        //创建ArrayAdapter适配器对象。参数1，表示所在的activity，参数2，表示数据要显示的界面，参数3，要显示的数据集
 //       ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,strs);
        ArrayAdapter ad=new ArrayAdapter(this,R.layout.array_adpter_item_layout,R.id.tv,strs);
//      ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,str_list);
        //注册适配器到组件上
        lv.setAdapter(ad);
    }
    /**
     *
     */
}
