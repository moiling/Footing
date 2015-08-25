package team.far.footing.util;

import android.content.Context;
import android.location.LocationManager;

import team.far.footing.app.APP;

/**
 * Created by moi on 2015/8/25.
 */
public class GPSUtils {

    public static boolean isGpsEnable() {
        LocationManager locationManager = ((LocationManager) APP.getContext().getSystemService(Context.LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
