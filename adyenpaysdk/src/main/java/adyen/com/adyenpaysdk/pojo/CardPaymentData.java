package adyen.com.adyenpaysdk.pojo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import adyen.com.adyenpaysdk.Adyen;
import adyen.com.adyenpaysdk.exceptions.EncrypterException;
import adyen.com.adyenpaysdk.exceptions.NoPublicKeyExeption;

/**
 * Created by andrei on 11/5/15.
 */
public class CardPaymentData implements PaymentData {

    private static final String tag = CardPaymentData.class.getSimpleName();

    private String number;
    private String expiryMonth;
    private String expiryYear;
    private String cardHolderName;
    private String cvc;
    private Date generationTime;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public Date getGenerationTime() {
        return generationTime;
    }

    public void setGenerationTime(Date generationTime) {
        this.generationTime = generationTime;
    }

    @Override
    public String serialize() throws EncrypterException, NoPublicKeyExeption {
        JSONObject cardJson = new JSONObject();
        String encryptedData = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            cardJson.put("generationtime", simpleDateFormat.format(generationTime));
            cardJson.put("number", number);
            cardJson.put("holderName", cardHolderName);
            cardJson.put("cvc", cvc);
            cardJson.put("expiryMonth", expiryMonth);
            cardJson.put("expiryYear", expiryYear);
            encryptedData = Adyen.getInstance().encryptData(cardJson.toString());
        } catch (JSONException e) {
            Log.e(tag, e.getMessage(), e);
        }


        return encryptedData;
    }

    @Override
    public String toString() {
        JSONObject cardJson = new JSONObject();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            cardJson.put("generationtime", simpleDateFormat.format(generationTime));
            if (number.length() >= 4) {
                cardJson.put("number", number.substring(0, 3));
            }
            cardJson.put("holderName", cardHolderName);
        } catch (JSONException e) {
            Log.e(tag, e.getMessage(), e);
        }

        return cardJson.toString();
    }
}
