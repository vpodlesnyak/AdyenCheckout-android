package adyen.com.adyenpaysdk.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by andrei on 12/24/15.
 */
public class LuhnTest {

    @Test
    public void testValidLuhnCheck() {
        String validCardNumber = "378282246310005";

        assertEquals(Luhn.check(validCardNumber), true);
    }

    @Test
    public void testInvalidLuhnCheck() {
        String invalidCardNumber = "5555 4444 3333 1112";

        assertEquals(Luhn.check(invalidCardNumber), false);
    }
}