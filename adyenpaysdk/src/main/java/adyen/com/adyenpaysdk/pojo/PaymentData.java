package adyen.com.adyenpaysdk.pojo;


import adyen.com.adyenpaysdk.exceptions.EncrypterException;
import adyen.com.adyenpaysdk.exceptions.NoPublicKeyExeption;

/**
 * Created by andrei on 11/26/15.
 */
public interface PaymentData {

    public String serialize() throws EncrypterException, NoPublicKeyExeption;

}
