package resturantapp.com.resturantapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import resturantapp.com.resturantapp.dto.RestaurantItem;
import resturantapp.com.resturantapp.utils.GsonRequest;
import resturantapp.com.resturantapp.utils.VolleyHelper;

/**
 * Created by Akhil Soni on 22-12-2015.
 */
public class RestaurantManager {

    public static final String TAG = "resturant_list";
    private static RestaurantManager sInstance;

    public interface RestaurantsDetailDownloadListener {
        void onDownloadSuccess(RestaurantItem resturantItem);
        void onDownloadFailure();
    }

    public static void downloadNewsDetail(Context context, String url, final RestaurantsDetailDownloadListener listener) {
        GsonRequest<RestaurantItem> request = new GsonRequest<>(context, Request.Method.GET, RestaurantItem.class, url, new Response.Listener<RestaurantItem>() {
            @Override
            public void onResponse(RestaurantItem response) {
                if (response == null) {
                    listener.onDownloadFailure();
                    return;
                }
                listener.onDownloadSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onDownloadFailure();
            }
        });
        request.setTag(TAG);
        VolleyHelper.getInstance(context).addToRequestQueue(request);
    }

}
