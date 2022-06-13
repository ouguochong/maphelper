package com.autonavi.minimap;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener{


    public EditText m_packagename;
    public CheckBox m_checkbox;
    public String packname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_packagename = (EditText)findViewById(R.id.editTextTextPersonName);
        m_checkbox = (CheckBox)findViewById(R.id.checkBox);

        // 添加内容的监听事件
        m_checkbox.setOnCheckedChangeListener(this);

        m_packagename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d("username: ", s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                packname = s.toString();
                ProperTies.setProperties(getApplicationContext(),"packagename",packname);
            }
        });

        packname = ProperTies.getProperties(this).getProperty("packagename");
        boolean bCustom = Boolean.valueOf(ProperTies.getProperties(this).getProperty("Custom"));
        m_checkbox.setChecked(bCustom);
        toast(this, packname);
        m_packagename.setText(packname);

        if(checkThirdParty(getIntent()))
        {
            StartAPP(packname);
            finish();
            return;
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        // 获取内容
        String str = compoundButton.getText().toString();
        if (str.equals("自选导航方案"))
        {
            // 是否选中
            if (compoundButton.isChecked()) {
                ProperTies.setProperties(getApplicationContext(),"Custom","true");
                //Toast.makeText(this, str, Toast.LENGTH_LONG).show();
            }
            else {
                ProperTies.setProperties(getApplicationContext(),"Custom","false");
            }
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        toast(this,"onNewIntent");
        checkThirdParty(intent);
    }

    private void StartAPP(String packname)
    {
        Intent mIntent = getPackageManager().getLaunchIntentForPackage(packname);
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

    /**
     * 需要在onCreate和onNewIntent都执行
     */
    private boolean checkThirdParty(Intent intent) {
        String key = intent.getStringExtra("key");
        String pn = intent.getStringExtra("packageName");
        toast(this, "pn："+ pn + "，key：" + key);
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(pn)) {
            //没有key说明该app未对接
            if (intent.getSourceBounds() != null) {
                toast(this, "launcher启动");
                return false;
            } else {
                //Log.e(TAG, "未对接的app启动");
            }
        } else {
            if (key.equals("通知栏")) {
                toast(this,"通知栏启动");
            } else {
                toast(this,"已对接第三方启动：" + pn + "，key：" + key);
            }
        }
        return true;
    }
    public static void  toast(Context context, CharSequence text)
    {
        boolean flag = false;
        if (flag)
        {
            Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
        }

    }
    public void onclick(View view) {
        //ThirdNavigationUtil.openThirdAppNavigation(this,"23.55898142360027","113.60613897442818","从化儿童公园",2);
        a("从化儿童公园","23.55898142360027","113.60613897442818");
        toast(this, "测试");
    }
    public void a(String str) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("androidamap://poi?sourceApplication=softname&keywords=" + str + "&dev=0"));
            intent.setPackage("com.autonavi.minimap");
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            //Log.e(a, "error while startActivity");
            //c.a(this.b).a("TEXT_SEND_VOICE_BACKGROUND", "搜索地点失败，请稍后再试", 0, "EXIT");
        }
    }

    private static double[] a(double d, double d2) {
        return new double[]{d, d2};
    }

    private String b(String str) {
        String str2;
        if (!str.contains(".")) {
            return str;
        }
        int indexOf = str.indexOf(".");
        if (str.substring(indexOf + 1).length() >= 6) {
            str2 = str.substring(0, indexOf + 7);
        } else {
            String str3 = "";
            for (int i = 0; i < 6 - str.substring(indexOf + 1).length(); i++) {
                str3 = str3 + "0";
            }
            str2 = str + str3;
        }
        //Log.e(a, "gaode map data " + str + " after deal--->" + str2);
        return str2;
    }

    public void a(String str, String str2, String str3) {
        double doubleValue = Double.valueOf(str2).doubleValue();
        double doubleValue2 = Double.valueOf(str3).doubleValue();
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("amapuri://route/plan/?did=BGVIS2&dlat=" + String.valueOf(Double.valueOf(b(String.valueOf(a(doubleValue, doubleValue2)[0]))).doubleValue()) + "&dlon=" + String.valueOf(Double.valueOf(b(String.valueOf(a(doubleValue, doubleValue2)[1]))).doubleValue()) + "&dname=" + str + "&dev=0&t=0"));
            intent.setPackage("com.autonavi.minimap");
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            startActivity(intent);
            //c.a(this.b).d(true);
        } catch (Exception e) {
            e.printStackTrace();
            //Log.e(a, "error while startActivity");
            //c.a(this.b).a("TEXT_SEND_VOICE_BACKGROUND", "发起导航失败，请稍后再试", 0, "EXIT");
        }
    }
}