package team.far.footing;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.ui.activity.HomeActivity;
import team.far.footing.util.LogUtils;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            LogUtils.e("客户端收到推送内容：" + intent.getStringExtra("msg"));

            try {
                JSONObject jsonObject = new JSONObject(intent.getStringExtra("msg"));
                String msg = jsonObject.getString("alert");
                showNotification(msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void showNotification(String msg) {

        //定义NotificationManager
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) APP.getContext().getSystemService(ns);
        //定义通知栏展现的内容信息
        int icon = R.mipmap.ic_logo;
        CharSequence tickerText = "我的通知栏标题";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        //定义下拉通知栏时要展现的内容信息
        Context context = APP.getContext();
        CharSequence contentTitle = "消息来了";
        CharSequence contentText = msg;
        Intent notificationIntent = new Intent(APP.getContext(), HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(APP.getContext(), 0,
                notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,
                contentIntent);
        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
        mNotificationManager.notify(1, notification);

    }

}