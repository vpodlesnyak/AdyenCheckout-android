package adyen.com.pay.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import adyen.com.adyenpaysdk.controllers.NetworkController;
import adyen.com.pay.ConfigLoader;


/**
 * Created by andrei on 11/10/15.
 */
public class PaymentServiceImpl implements PaymentService {

    private static final String tag = PaymentServiceImpl.class.getSimpleName();

    private JSONObject configuration;

    private Context context;

    public PaymentServiceImpl(final Context context) {
        this.context = context;
        getConfiguration();
    }

    @Override
    public void pay(JSONObject paymentRequest, final VolleyCallback callback) {
        String paymentUrl = getPaymentUrl();

        Log.i(tag, "Payment url: " + paymentUrl);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(paymentUrl, paymentRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(tag, "Error: " + error.getMessage());
                callback.onError("Declined", "Bad request");
            }
        });

        NetworkController.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private String getPaymentUrl() {
        String paymentUrl = null;

        try {
            paymentUrl = configuration.getString("paymentRequestUrl");
        } catch (JSONException e) {
            Log.e(tag, e.getMessage(), e);
        }

        return paymentUrl;
    }

    private void getConfiguration() {
        ConfigLoader configLoader = new ConfigLoader();
        this.configuration = configLoader.loadJsonConfiguration(context);
    }

    public interface VolleyCallback {
        void onSuccess(JSONObject result);

        void onError(String resultCode, String message);
    }

}