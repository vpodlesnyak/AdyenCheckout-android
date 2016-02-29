package adyen.com.adyenpaysdk.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import adyen.com.adyenpaysdk.controllers.NetworkController;


/**
 * Created by andrei on 11/10/15.
 */
public class PaymentServiceImpl implements PaymentService {

    private static final String tag = PaymentServiceImpl.class.getSimpleName();

    private static final String SUCCESS = "ok";

    private Context context;

    public PaymentServiceImpl(final Context context) {
        this.context = context;
    }

    @Override
    public void fetchPublicKey(String hppUrl, final VolleyCallback callback) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, hppUrl, (String)null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(tag, response.toString());

                        try {
                            String status = response.getString("status");
                            if(SUCCESS.equals(status)) {
                                callback.onSuccess(response);
                            }
                        } catch (JSONException e) {
                            Log.e(tag, e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(tag, "Error: " + error.getMessage());
                    }
                });

        NetworkController.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public interface VolleyCallback {
        void onSuccess(JSONObject result);

        void onError(String resultCode, String message);
    }

}