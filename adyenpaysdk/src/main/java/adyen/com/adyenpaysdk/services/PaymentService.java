package adyen.com.adyenpaysdk.services;

/**
 * Created by andrei on 11/10/15.
 */
public interface PaymentService {

    void fetchPublicKey(String hppUrl, final PaymentServiceImpl.VolleyCallback callback);

}
