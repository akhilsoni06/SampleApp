package resturantapp.com.resturantapp.utils;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
    private final Gson mGson = new Gson();
    private final Class<T> mClazz;
    private Map<String, String> mHeaders = new HashMap<>();
    private Map<String, String> mParams = new HashMap<>();
    private final Response.Listener<T> mListener;
    private Context mContext;
    private final int TIME = 60 * 1000;


    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param method
     * @param clazz   Relevant class object, for Gson's reflection
     * @param url     URL of the request to make
     * @param headers Map of request mHeaders
     * @param params
     */
    public GsonRequest(Context ctx, int method, Class<T> clazz, String url, Response.Listener<T> listener,
                       Response.ErrorListener errorListener, Map<String, String> headers,
                       Map<String, String> params) {
        super(method, url, errorListener);
        mClazz = clazz;
        mHeaders = headers;
        mParams = params;
        mListener = listener;
        mContext = ctx.getApplicationContext();
        setRetryPolicy(new DefaultRetryPolicy(TIME, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param method
     * @param clazz  Relevant class object, for Gson's reflection
     * @param url    URL of the request to make
     */
    public GsonRequest(Context ctx, int method, Class<T> clazz, String url, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mClazz = clazz;
        mListener = listener;
        mContext = ctx.getApplicationContext();
        setRetryPolicy(new DefaultRetryPolicy(TIME, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
//
            return Response.success(
                    mGson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }


}

