package adyen.com.adyenpaysdk.exceptions;

/**
 * Created by andrei on 11/26/15.
 */
public class NoPublicKeyExeption extends Exception {

    private static final long serialVersionUID = 2699577096011945292L;

    /**
     * Wrapping exception for all JCE encryption related exceptions
     * @param message
     */
    public NoPublicKeyExeption(String message) {
        super(message);
    }

}
