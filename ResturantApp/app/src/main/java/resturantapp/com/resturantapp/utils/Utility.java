package resturantapp.com.resturantapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

import java.math.BigDecimal;
import java.math.RoundingMode;

import resturantapp.com.resturantapp.R;

/**
 * Created by Akhil Soni on 22-12-2015.
 */
public class Utility {

    public static double convertKmToMeter(double km) {
        return round((km / 1000), 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void showNoNetworkAlertForHome(final Context ctx) {
        try {
            new AlertDialog.Builder(ctx).setTitle(R.string.app_name).setMessage(R.string.no_internet)
                    .setPositiveButton(ctx.getResources().getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((Activity) ctx).finish();
                        }
                    }).create().show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

}
