package team.far.footing.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import team.far.footing.R;

/**
 * Created by moi on 2015/8/7.
 */
public class BaseActivity extends AppCompatActivity {

    private SystemBarTintManager tintManager;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    protected void userBarTint() {
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        setBarTintColor(getResources().getColor(R.color.primary_color));
    }

    protected void setBarTintColor(int color) {
        tintManager.setTintColor(color);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showProgress(String title){
        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .content("请稍候")
                .theme(Theme.LIGHT)
                .progress(true, 100)
                .cancelable(false)
                .show();
    }

    public void dismissProgress(){
        dialog.dismiss();
    }

}
