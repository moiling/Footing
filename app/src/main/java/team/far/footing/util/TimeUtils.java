package team.far.footing.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luoyy on 2015/8/15 0015.
 */
public class TimeUtils {

    public static final Date getcurrentTime() {
        return new Date(System.currentTimeMillis());
    }


    public static final String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd  HH:mm");
        return simpleDateFormat.format(date);
    }

    public static final boolean isToday(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LogUtils.e(sdf.format(getcurrentTime()));
        if (sdf.format(getcurrentTime()).equals(string)) {
            return true;
        } else {
            return false;
        }
    }

    public static final String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LogUtils.e(sdf.format(getcurrentTime()));
        return sdf.format(getcurrentTime());
    }


    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strMinute + "' " + strSecond + "''";
    }
}
