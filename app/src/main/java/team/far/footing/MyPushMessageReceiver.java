package team.far.footing;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.model.bean.PushBean;
import team.far.footing.service.UpdateService;
import team.far.footing.ui.activity.HomeActivity;
import team.far.footing.util.LogUtils;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {

    private String url;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            LogUtils.e("客户端收到推送内容：" + intent.getStringExtra("msg"));
            Gson gson = new GsonBuilder().create();
            PushBean pushBean = gson.fromJson(intent.getStringExtra("msg"), PushBean.class);
            Log.e("=====>>>", pushBean.getMsg());
            Log.e("=====>>>", pushBean.getVersion());
            Log.e("=====>>>", "" + (pushBean.getUrl() == null));
            //不是推送版本更新
            if (pushBean.getUrl() == null) {
                Intent notificationIntent = new Intent(APP.getContext(), HomeActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(APP.getContext(), 0,
                        notificationIntent, 0);
                showNotification("消息来了", pushBean.getMsg(), contentIntent);
            } else {
                Intent updateIntent = new Intent(context, UpdateService.class);
                updateIntent.putExtra("url", pushBean.getUrl());
                PendingIntent contentIntent = PendingIntent.getService(APP.getContext(), 0,
                        updateIntent, 0);
                showNotification("足下有新版本了哟", "点击下载", contentIntent);
            }
        }
    }

    private void showNotification(String title, String msg, PendingIntent contentIntent) {
        //定义NotificationManager
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) APP.getContext().getSystemService(ns);
        //定义通知栏展现的内容信息
        int icon = R.mipmap.ic_logo;
        CharSequence tickerText = "足下有新消息了哟";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        //定义下拉通知栏时要展现的内容信息
        Context context = APP.getContext();
        CharSequence contentTitle = title;
        CharSequence contentText = msg;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(context, contentTitle, contentText,
                contentIntent);
        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
        mNotificationManager.notify(1, notification);
    }


}