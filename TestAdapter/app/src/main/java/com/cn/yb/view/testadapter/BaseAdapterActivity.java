package com.cn.yb.view.testadapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import bean.Student;
import bean.StudentDAO;

public class BaseAdapterActivity extends AppCompatActivity {
    private ListView stu_listView;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter);
        stu_listView = (ListView) findViewById(R.id.student);

        studentList = new StudentDAO().getStudents();


        stu_listView.setAdapter(new MyBaseAdapter(this, studentList));

    }

    class MyBaseAdapter extends BaseAdapter {
        private List<Student> studentL;
        private LayoutInflater lf;

        public MyBaseAdapter(Context context, List<Student> studentL) {
            this.studentL = studentL;
            lf = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return studentList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //  getView方法是绘制项的布局，实质是将数据集的项数据绑定在项的布局的组件上，让其显示
        //  getView方法要执行多少次，或者说要绘制多少个项布局，根据什么？getCount()返回值确定的
        //  参数1是ListView组件的索引，参数2是组件项的布局，参数3，一般不管。
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Student stu = (Student) studentList.get(position);
            ViewHolder holder=null;
            //要用到布局的解析器，将布局转成对象
            if (convertView == null) {
                convertView = lf.inflate(R.layout.item_base_adapter, null);
                holder=new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.tuxiang);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.id = (TextView) convertView.findViewById(R.id.id);
                holder.age = (TextView) convertView.findViewById(R.id.age);
                holder.identify = (TextView) convertView.findViewById(R.id.identify);
            }
            convertView.setTag(position);
            holder.imageView.setImageResource(stu.getTuxiang());
            holder.name.setText(stu.getName());
            holder.id.setText(stu.getId() + "");
            holder.age.setText(stu.getAge() + "");
            holder.identify.setText(stu.getIdtentify());


            return convertView;
        }
    }
    static  class  ViewHolder{
        ImageView imageView = null;
        TextView name = null;
        TextView id = null;
        TextView age = null;
        TextView identify = null;
    }

}
