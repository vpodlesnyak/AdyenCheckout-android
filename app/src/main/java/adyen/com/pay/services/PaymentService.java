package adyen.com.pay.services;

import org.json.JSONObject;

/**
 * Created by andrei on 11/10/15.
 */
public interface PaymentService {

    void pay(JSONObject paymentRequest, final PaymentServiceImpl.VolleyCallback callback);

}
