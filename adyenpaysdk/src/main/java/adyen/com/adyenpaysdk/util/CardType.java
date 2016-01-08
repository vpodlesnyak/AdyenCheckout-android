package adyen.com.adyenpaysdk.util;

import java.util.regex.Pattern;

/**
 * Created by andrei on 11/24/15.
 */
public enum CardType {

    AMEX("^3[47][0-9]{5,}$"),
    DINNERS("^3(?:0[0-5]|[68][0-9])[0-9]{4,}$"),
    DISCOVER("^6(?:011|5[0-9]{2})[0-9]{3,}$"),
    ELO("^((((636368)|(438935)|(504175)|(451416)|(636297))[0-9]{0,10})|((5067)|(4576)|(4011))[0-9]{0,12})$"),
    HIPERCARD("^(606282|3841)[0-9]{5,}$"),
    JCB("^(?:2131|1800|35[0-9]{3})[0-9]{3,}$"),
    VISA("^4[0-9]{6,}([0-9]{3})?$"),
    MASTERCARD("^(5[1-5][0-9]{4}|677189)[0-9]{5,}$"),
    UNION_PAY("^(62|88)[0-9]{5,}$"),
    UNKNOWN("");

    private Pattern pattern;

    CardType(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public static CardType detect(String cardNumber) {
        for (CardType cardType : CardType.values()) {
            if (null == cardType.pattern) {
                continue;
            }

            if (cardType.pattern.matcher(cardNumber).matches()) {
                return cardType;
            }
        }

        return UNKNOWN;
    }

}
