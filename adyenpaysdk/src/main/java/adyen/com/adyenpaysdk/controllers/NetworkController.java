package adyen.com.adyenpaysdk.controllers;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by andrei on 11/11/15.
 */
public class NetworkController {

    private static final String TAG = NetworkController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static NetworkController mInstance;

    private Context mContext;

    private NetworkController(Context context) {
        this.mContext = context;
    }

    public static synchronized NetworkController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkController(context.getApplicationContext());
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public Context getGlobalContext() {
        return mContext;
    }

}
