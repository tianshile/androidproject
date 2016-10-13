package bean;

import com.cn.yb.view.testadapter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/9/20.
 */
public class StudentDAO {
    public List<Student> getStudents() {
        List<Student> studentList = new ArrayList<Student>();
        Student st1 = new Student();
        st1.setId(10001);
        st1.setName("张三");
        st1.setIdtentify("610111111111111111");
        st1.setAge(24);
        st1.setTuxiang(R.drawable.c_11);
        studentList.add(st1);
        Student st2 = new Student();
        st2.setId(10002);
        st2.setName("李四");
        st2.setIdtentify("610111111111111111");
        st2.setAge(32);
        st2.setTuxiang(R.drawable.c_12);
        studentList.add(st2);
        Student st3 = new Student();
        st3.setId(10003);
        st3.setName("王五");
        st3.setIdtentify("610111111111111111");
        st3.setAge(24);
        st3.setTuxiang(R.drawable.c_13);
        studentList.add(st3);
        Student st4 = new Student();
        st4.setId(10004);
        st4.setName("薛六");
        st4.setIdtentify("610111111111111111");
        st4.setAge(46);
        st4.setTuxiang(R.drawable.c_14);
        studentList.add(st4);
        Student st5 = new Student();
        st5.setId(10005);
        st5.setName("赵七");
        st5.setIdtentify("610111111111111111");
        st5.setAge(24);
        st5.setTuxiang(R.drawable.c_21);
        studentList.add(st5);
        return studentList;
    }

}
