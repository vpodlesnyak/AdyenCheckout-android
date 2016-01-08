package adyen.com.adyenpaysdk.util;

/**
 * Created by andrei on 12/21/15.
 */
public enum Currency {

    USD("$"),
    EUR("€"),
    GBP("£");

    private String currencySign;

    private Currency(String sign) {
        this.currencySign = sign;
    }

    public String getCurrencySign() {
        return this.currencySign;
    }

}
