package adyen.com.adyenpaysdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import adyen.com.adyenpaysdk.exceptions.EncrypterException;
import adyen.com.adyenpaysdk.exceptions.NoPublicKeyExeption;
import adyen.com.adyenpaysdk.services.PaymentService;
import adyen.com.adyenpaysdk.services.PaymentServiceImpl;
import adyen.com.adyenpaysdk.util.ClientSideEncrypter;
import adyen.com.adyenpaysdk.util.Luhn;


/**
 * Created by andrei on 11/5/15.
 */
public class Adyen {

    private static Adyen mInstance = null;

    private static final String tag = Adyen.class.getSimpleName();

    private boolean useTestBackend = false;
    private String token;
    private String publicKey;

    public interface CompletionCallback {

        void onSuccess(String result);

        void onError(String error);

    }

    private Adyen() {

    }

    public static Adyen getInstance() {
        if(mInstance == null) {
            mInstance = new Adyen();
        }
        return mInstance;
    }

    public void fetchPublicKey(Context context, final CompletionCallback completion) {
        PaymentService paymentService = new PaymentServiceImpl(context);
        String host = (useTestBackend) ? "test" : "live";
        String url = String.format("https://%s.adyen.com/hpp/cse/%s/json.shtml", host, token);
        paymentService.fetchPublicKey(url, new PaymentServiceImpl.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    publicKey = result.getString("publicKey");
                    completion.onSuccess(publicKey);
                } catch (JSONException e) {
                    Log.e(tag, e.getMessage(), e);
                    completion.onError(e.getMessage());
                }
            }

            @Override
            public void onError(String resultCode, String message) {
                completion.onError(message);
            }
        });
    }

    public String encryptData(String data) throws NoPublicKeyExeption, EncrypterException {
        String encryptedData = null;
        if(!TextUtils.isEmpty(publicKey)) {
            try {
                ClientSideEncrypter encrypter = new ClientSideEncrypter(publicKey);
                encryptedData = encrypter.encrypt(data);
            } catch (EncrypterException e) {
                throw e;
            }
        } else {
            throw new NoPublicKeyExeption("No public key was found!");
        }

        return encryptedData;
    }

    public boolean luhnCheck(String cardNumber) {
        return Luhn.check(cardNumber);
    }

    public boolean isUseTestBackend() {
        return useTestBackend;
    }

    public void setUseTestBackend(boolean useTestBackend) {
        this.useTestBackend = useTestBackend;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
