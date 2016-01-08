package adyen.com.adyenpaysdk.pojo;

import adyen.com.adyenpaysdk.util.Currency;

/**
 * Created by andrei on 12/21/15.
 */
public class CheckoutResponse {

    private String paymentData;
    private float amount;
    private Currency currency;


    public String getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(String paymentData) {
        this.paymentData = paymentData;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
