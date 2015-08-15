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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(date);
    }
}
