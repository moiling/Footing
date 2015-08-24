package team.far.footing.util;

/**
 * Created by luoyy on 2015/8/24 0024.
 */
public class LevelUtils {


    public static int getLevel(int experience) {
        for (int i = 1; ; i++) {
            int a = i * i * 200 + 40;
            int b = (i + 1) * (i + 1) * 200 + 40;
            if (i == 1 && experience <= a) {
                return i;
            }

            if (experience >= a && experience <= b) {
                return i;
            }
        }

    }
}
