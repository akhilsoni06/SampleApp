package resturantapp.com.resturantapp.utils;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Akhil Soni on 22-12-2015.
 */
public class VolleyHelper {
    private static VolleyHelper mInstance;
    public RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private  Context mCtx;

    private VolleyHelper(final Context context) {
        mCtx = context;
        mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(context));
    }

    public static synchronized VolleyHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyHelper(context);
        }
        return mInstance;
    }

    private VolleyHelper() {
    }


    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void cancelRequest(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            mRequestQueue.cancelAll(tag);
        }

    }
}
