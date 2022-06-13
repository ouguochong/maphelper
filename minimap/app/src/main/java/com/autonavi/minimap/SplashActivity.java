package com.autonavi.minimap;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;


import java.util.Set;

public class SplashActivity extends Activity {
//    @Override
//    protected void onDestroy () {
//        super.onDestroy();
//    }

    private void StartAPP()
    {
        Intent mIntent = getPackageManager().getLaunchIntentForPackage("com.autonavi.amapautoclone");
        if (mIntent != null) {
            try {
                startActivity(mIntent);
            } catch (ActivityNotFoundException err) {
                Toast t = Toast.makeText(getApplicationContext(),
                        "App is not found", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri data;
        Intent intent = getIntent();
        if (!(intent == null || TextUtils.isEmpty(intent.getDataString()))) {
            //AMapLog.sceneLog(0, 0, "U_schemeStart", "cold", "amap.P00606.0.D014", 1);
        }
        //MainActivity.toast(this,"语音接口");

        String str = "";
        if (!(isTaskRoot() || getIntent() == null)) {
            int flags = getIntent().getFlags() & 4194304;
            if (q() && flags == 4194304) {
                finish();
                //VuiTeachScheme.U("U_SplashActivity_end");
                data = getIntent().getData();
//                if (data != null) {
//                    if (MapApplication.isLaunchStartApp) {
//                        HashMap hashMap = new HashMap();
//                        hashMap.put("currentPackageName", getPackageName());
//                        FlowCustomsAdapter.c(FlowType.LAUNCH, str, hashMap);
//                    }
//                    //LaunchRecord.c(1);
//                    return;
//                }
//                str = data.getHost();
//                if ("navi".equals(str)) {
//                    AMapLog.sceneLog(0, 0, "U_schemeStart", "{\"type\":2}", "amap.P00606.0.D005", 2);
//                } else if ("route".equals(str)) {
//                    if ("/plan".equals(data.getPath())) {
//                        AMapLog.sceneLog(0, 0, "U_schemeStart", "{\"type\":2}", "amap.P00606.0.D004", 2);
//                    }
//                }
                //LaunchRecord.c(2);
                return;
            }
        }
        data = getIntent().getData();
        str = data.getHost();
        //Toast.makeText(this,"导航到："+data.getQueryParameter("dname").toString(),Toast.LENGTH_SHORT).show();
        ThirdNavigationUtil.openThirdAppNavigation(this,data.getQueryParameter("dlat").toString(),data.getQueryParameter("dlon").toString(),data.getQueryParameter("dname").toString(),2);
        //moveTaskToBack(true);//activity   隐藏
        //super.onDestroy();
        finish();
        return;
    }
    public final boolean q() {
        Intent intent = getIntent();
        if (intent == null) {
            return false;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action) || !"android.intent.action.MAIN".equals(action)) {
            return false;
        }
        Set<String> categories = intent.getCategories();
        if (categories != null) {
            if (!categories.isEmpty()) {
                for (String action2 : categories) {
                    if ("android.intent.category.LAUNCHER".equals(action2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}