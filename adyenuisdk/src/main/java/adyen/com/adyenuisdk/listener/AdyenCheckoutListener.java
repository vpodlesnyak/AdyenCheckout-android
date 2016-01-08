package adyen.com.adyenuisdk.listener;

import adyen.com.adyenpaysdk.pojo.CheckoutResponse;

/**
 * Created by andrei on 12/3/15.
 */
public interface AdyenCheckoutListener {

    void checkoutAuthorizedPayment(CheckoutResponse checkoutResponse);
    void checkoutFailedWithError(String errorMessage);

}
