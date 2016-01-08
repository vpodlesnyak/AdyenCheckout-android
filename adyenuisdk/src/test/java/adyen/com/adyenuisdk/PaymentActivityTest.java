package adyen.com.adyenuisdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowInputMethodManager;

import adyen.com.adyenpaysdk.util.Currency;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by andrei on 1/7/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22, manifest = "src/main/AndroidManifest.xml")
public class PaymentActivityTest {

    private PaymentActivity paymentActivity;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application.getApplicationContext(), PaymentActivity.class);
        Bundle arguments = new Bundle();
        arguments.putInt("backgroundColor", R.color.nespresso_grey);
        arguments.putInt("logo", R.mipmap.nespresso_logo);
        arguments.putFloat("amount", 1f);
        arguments.putString("currency", Currency.EUR.toString());
        arguments.putString("token", "testToken");
        arguments.putBoolean("useTestBackend", true);
        intent.putExtras(arguments);
        paymentActivity = Robolectric.buildActivity(PaymentActivity.class).withIntent(intent).create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(paymentActivity);
    }

    @Test
    public void checkSoftKeyboardPresent() throws Exception {
        InputMethodManager imm = (InputMethodManager) paymentActivity.getSystemService(RuntimeEnvironment.application.getApplicationContext().INPUT_METHOD_SERVICE);
        ShadowInputMethodManager shadowInputMethodManager = Shadows.shadowOf(imm);
        assertTrue(shadowInputMethodManager.isSoftInputVisible());
    }

    @After
    public void tearDown() throws Exception {

    }
}