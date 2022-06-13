package com.autonavi.minimap;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;


import java.util.List;

/**
 * Created by wanghh on 2017/11/10.
 */

public class ThirdNavigationUtil {
    public static final String amapPkg = "com.autonavi.minimap";
    public static final String baiduMapPkg = "com.baidu.BaiduMap";
    public static final String amapCheJiPkg = "com.autonavi.amapautoclone";
    public static final String tencentMapPkg = "com.tencent.map";
    public static final String sogouMapPkg = "com.sogou.map.android.maps";
    public class  MapAppType {
        static final int AMAP_APP = 0;
        static final int BDMAP_APP = 1;
        static final int AMAP_AUTO = 2;
        static final int TXMAP_APP = 3;
        static final int SOUGOU_APP = 4;
    }
    /**
     * 启动第三方地图app,并开始导航(第三方地图)
     * @param context
     * @param lat
     * @param lon
     * @param destinationTitle
     * @param type             ConstUtil.MapAppType.AMAP_APP
     */


    public static void openThirdAppNavigation(final Context context, final String lat,
                                              final String lon, final String destinationTitle,
                                              int type) {
        //L.d("openThirdAppNavigation","type = "+type);
        //VoiceRobot.getInstance(context).stopSpeakRecordStartAwake();
        //网页高德
        //MainActivity.toast(context,"导航到："+destinationTitle.toString());
//      getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://uri.amap.com/navigation?from=116.478346,39.997361,startpoint&to=116.3246,39.966577,endpoint&via=116.402796,39.936915,midwaypoint&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0")));
        final PackageManager packageManager = context.getPackageManager();
        Object ConstUtil;
        if (type == MapAppType.AMAP_APP) {
            //高德地图app
            Intent amapIntent = new Intent(Intent.ACTION_VIEW,
                    android.net.Uri.parse("androidamap://navi?sourceApplication="
                            + context.getString(R.string.app_name) + "&poiname="
                            + destinationTitle + "&lat=" + lat + "&lon=" + lon + "&dev=0&style=2"));
            amapIntent.setPackage(amapPkg);
            startAct(context,amapPkg,packageManager,amapIntent,type);
//            List<ResolveInfo> activities = packageManager.queryIntentActivities(amapIntent, 0);
//            boolean isValid = !activities.isEmpty();
//            if (isValid) {
//                ConstUtil.isNeedXunHangBroadCast = false;
//                FucUtil.addToRecentIntentsByPackageName(context.getApplicationContext(), amapPkg);
//                context.startActivity(amapIntent);
//            } else {
//                MyToastUtils.show(context, "请先下载安装高德地图！");
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ConstUtil.DOWNLOAD_AMAP_URL)));
//            }
        } else if (type == MapAppType.BDMAP_APP) {
            //百度地图app
            double[] gaodeToBaiduPoint = gaoDeToBaidu(Double.parseDouble(lat), Double.parseDouble(lon));
            Intent baiduIntent = new Intent(Intent.ACTION_VIEW);
//            baiduIntent.setData(Uri.parse("baidumap://map/navi?query=" + destinationTitle));//
//            DecimalFormat df = new DecimalFormat("0.0000");
//            baiduIntent.setData(Uri.parse("baidumap://map/navi?location=" + df.format(GaodeToBaiduPoint[0]) + "," + df.format(GaodeToBaiduPoint[1])));
            baiduIntent.setData(Uri.parse("baidumap://map/navi?location=" + gaodeToBaiduPoint[0] + "," + gaodeToBaiduPoint[1]));
            startAct(context,baiduMapPkg,packageManager,baiduIntent,type);
//            List<ResolveInfo> activities = packageManager.queryIntentActivities(baiduIntent, 0);
//            boolean isValid = !activities.isEmpty();
//            if (isValid) {
//                ConstUtil.isNeedXunHangBroadCast = false;
//                FucUtil.addToRecentIntentsByPackageName(context.getApplicationContext(), baiduMapPkg);
//                context.startActivity(baiduIntent);
//            } else {
//                MyToastUtils.show(context, "请先下载安装百度地图！");
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ConstUtil.DOWNLOAD_BAIDU_URL)));
//            }
        } else if (type == MapAppType.AMAP_AUTO) {
            //高德地图车机版 oppo手机不支持
            //高德地图车机版本 使用该包名


            Intent launchIntent = new Intent();
            String packname = ProperTies.getProperties(context).getProperty("packagename");
            launchIntent.setComponent(
                    new ComponentName(packname,
                            "com.autonavi.auto.remote.fill.UsbFillActivity"));
            startAct(context,packname,packageManager,launchIntent,type);

//            List<ResolveInfo> activities = packageManager.queryIntentActivities(launchIntent, 0);
//            boolean isValid = !activities.isEmpty();
//            if (isValid) {
//                ConstUtil.isNeedXunHangBroadCast = false;
//                FucUtil.addToRecentIntentsByPackageName(context.getApplicationContext(), amapCheJiePkg);
//                context.startActivity(launchIntent);
//            } else {
//                MyToastUtils.show(context, "请先下载安装高德地图车机版！");
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ConstUtil.DOWNLOAD_AMAP_AUTO_URL)));
//            }
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    //这里用显示 intent,因为车机版是注册的静态广播
                    ComponentName cn = new ComponentName(packname
                            ,"com.autonavi.amapauto.adapter.internal.AmapAutoBroadcastReceiver");
                    intent.setComponent(cn);

                    intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");

                    boolean bCustom = Boolean.valueOf(ProperTies.getProperties(context).getProperty("Custom"));
                    if (bCustom)
                    {
                        /**
                         * https://lbs.amap.com/api/amap-auto/guide/android/navi
                         * 这个10007 是先规划,在开始导航
                         */
                        intent.putExtra("KEY_TYPE", 10007);
                        intent.putExtra("EXTRA_DNAM", destinationTitle);
                        intent.putExtra("ENTRY_LAT", Double.parseDouble(lat));
                        intent.putExtra("ENTRY_LON", Double.parseDouble(lon));
                        intent.putExtra("EXTRA_DEV", 0);
                        intent.putExtra("EXTRA_M", -1);
                    }else
                    {
                        /**
                         * 10038 这个可以直接发起导航,不用规划
                         */
                        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        intent.putExtra("KEY_TYPE", 10038);
                        intent.putExtra("POINAME", destinationTitle);
                        intent.putExtra("LAT", Double.parseDouble(lat));
                        intent.putExtra("LON", Double.parseDouble(lon));
                        intent.putExtra("DEV", 0);
                        intent.putExtra("STYLE", 0);
                        intent.putExtra("SOURCE_APP", context.getString(R.string.app_name));
                    }

                    context.sendBroadcast(intent);
//                    MyToastUtils.showDebug("给高德车机发送终点");
                    //L.d("openThirdAppNavigation","给高德车机发送终点 ");
                }
            }).start();

        } else if (type == MapAppType.TXMAP_APP) {
            //腾讯地图
            /**
             * * @param from  选 出发地址
             * @param fromcoord 选 出发经纬度   移动端如果起点名称和起点坐标均未传递，则使用当前定位位置作为起点 如 39.9761,116.3282
             * @param to  必 目标地址
             * @param tocoord  必 目标经纬度 39.9761,116.3282
             * @param policy  选  本参数取决于type参数的取值
             *               公交：type=bus，  policy有以下取值 ,0：较快捷 ,1：少换乘 ,2：少步行 ,3：不坐地铁
             *               驾车：type=drive，policy有以下取值 ,0：较快捷 ,1：无高速 ,2：距离   ,policy的取值缺省为0
             * @param coord_type  选 坐标类型，取值如下：1 GPS  2 腾讯坐标（默认）  如果用户指定该参数为非腾讯地图坐标系，
             *                    则URI API自动进行坐标处理，以便准确对应到腾讯地图底图上。
             * @param type  必 公交：bus  驾车：drive  步行：walk（仅适用移动端）
             * @param referer  必  调用来源，一般为您的应用名称，为了保障对您的服务，请务必填写！
            ---------------------
            官方文档地址:https://lbs.qq.com/uri_v1/guide-mobile-navAndRoute.html
            原文：https://blog.csdn.net/lu1024188315/article/details/78496455?utm_source=copy
             */
            String url1 = "qqmap://map/routeplan?fromcoord=CurrentLocation&type=drive&to=" + destinationTitle + "&tocoord=" + lat+ "," +
                    lon  + "&policy=2&referer=yunjiaHUD";
            Intent qqMapIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url1));
//            qqMapIntent.setPackage(tencentMapPkg);
//            qqMapIntent.setData(android.net.Uri.parse(url1));
            startAct(context,tencentMapPkg,packageManager,qqMapIntent,type);
            //谷歌地图app
//            Intent googleIntent = new Intent(Intent.ACTION_VIEW);
//            googleIntent.setData(Uri.parse("google.navigation:q=" + Double.parseDouble(lat) + "," + Double.parseDouble(lon)));
//            googleIntent.setPackage("com.google.android.apps.maps");
////            if (googleIntent.resolveActivity(context.getPackageManager()) != null) {
////                context.startActivity(googleIntent);
////            }
//            List<ResolveInfo> activities = packageManager.queryIntentActivities(googleIntent, 0);
//            boolean isValid = !activities.isEmpty();
//            if (isValid) {
//                ConstUtil.isNeedXunHangBroadCast = false;
//                FucUtil.addToRecentIntentsByPackageName(context.getApplicationContext(), "com.google.android.apps.maps");
//                context.startActivity(googleIntent);
//            } else {
//                MyToastUtils.show(context, "请先下载安装谷歌地图！");
//            }
        }else if (type == MapAppType.SOUGOU_APP){
            //搜狗地图app

            String sgStr = "geo:"+lat+","+lon;
            Intent sogouIntent = new Intent(Intent.ACTION_VIEW,
                    android.net.Uri.parse(sgStr));
            sogouIntent.setPackage(sogouMapPkg);
            startAct(context,sogouMapPkg,packageManager,sogouIntent,type);
        }
    }

    private static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }


    private static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }


    /**
     * 保存intent到ConstUtil.mAppRecentIntents,启动第三方app
     * @param context
     * @param pkgName
     * @param packageManager
     * @param mapIntent
     * @param type
     */
    private static void startAct(Context context,String pkgName,PackageManager packageManager
            ,Intent mapIntent,int type) {
//        amapIntent.setPackage(pkgName);
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
        boolean isValid = !activities.isEmpty();
        if (isValid) {
            //ConstUtil.isNeedXunHangBroadCast = false;
            //FucUtil.addToRecentIntentsByPackageName(context.getApplicationContext(), pkgName);
            context.startActivity(mapIntent);
        } else {
//            if (type == 1) {
//                MyToastUtils.show( "请先下载安装高德地图！");
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ConstUtil.DOWNLOAD_AMAP_URL)));
//            } else if (type == 2){
//                MyToastUtils.show( "请先下载安装百度地图！");
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ConstUtil.DOWNLOAD_BAIDU_URL)));
//            } else if (type == 3) {
//                MyToastUtils.show( "请先下载安装高德地图车机版！");
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ConstUtil.DOWNLOAD_AMAP_AUTO_URL)));
//            } else if (type == 4) {
//                MyToastUtils.show( "请先下载安装腾讯地图！");
//                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ConstUtil.DOWNLOAD_TENMAP_URL)));
//            }

        }
    }
}
