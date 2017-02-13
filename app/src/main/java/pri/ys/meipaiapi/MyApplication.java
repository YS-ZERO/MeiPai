package pri.ys.meipaiapi;

import android.app.Application;

import org.xutils.x;


/**
 * Created by YS-WENJIE on 2016-11-28.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);   //xUtils 3.0 初始化
        x.Ext.setDebug(true); // 是否输出debug日志，这句代码可以不要



    }


}
