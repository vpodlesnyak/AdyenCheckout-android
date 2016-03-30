package adyen.com.adyenpaysdk.pojo;

import adyen.com.adyenpaysdk.util.Currency;

/**
 * Created by andrei on 12/9/15.
 */
public class CheckoutRequest {

    private float checkoutAmount;
    private Currency currency;
    private int brandColor;
    private int brandLogo;
    private String token;
    private boolean testBackend;
    private String title;


    public float getCheckoutAmount() {
        return checkoutAmount;
    }

    public void setCheckoutAmount(float checkoutAmount) {
        this.checkoutAmount = checkoutAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getBrandColor() {
        return brandColor;
    }

    public void setBrandColor(int brandColor) {
        this.brandColor = brandColor;
    }

    public int getBrandLogo() {
        return brandLogo;
    }

    public void setTitle(String _title) {
        this.title = _title;
    }

    public String getTitleText() {
        return title;
    }

    public void setBrandLogo(int brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isTestBackend() {
        return testBackend;
    }

    public void setTestBackend(boolean testBackend) {
        this.testBackend = testBackend;
    }
}
