package app.android.com.mytest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/11/5.
 */
public class MyTestDemo extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mainActivity;
    private TextView tvTest;

    //定义无参的构造方法
    public MyTestDemo(){
        //必须初始化父类
        super(MainActivity.class);
    }
    //2.setup() 初始化方法

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity=this.getActivity();
        tvTest=(TextView)mainActivity.findViewById(R.id.tvTest);
    }

    public void testPreconditions() {
        assertNotNull("mainActivity is null", mainActivity);
        assertNotNull("tvTest is null", tvTest);
    }
    public void testMyFirstTestTextView_labelText() {
        final String expected =mainActivity.getString(
                R.string.app_name);
        final String actual = tvTest.getText().toString();
        assertEquals(expected, actual);
    }

//    public MyTestDemo(Class<MainActivity> activityClass) {
//        super(activityClass);
//    }
}
