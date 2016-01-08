package adyen.com.adyenuisdk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import adyen.com.adyenpaysdk.util.Currency;
import adyen.com.adyenuisdk.customcomponents.AdyenEditText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by andrei on 1/8/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22, manifest = "src/main/AndroidManifest.xml")
public class CreditCardFormTest {

    private PaymentActivity paymentActivity;
    private LinearLayout creditCardForm;

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
        creditCardForm = paymentActivity.getmPaymentForm();
    }

    @Test
    public void checkFormNotNull() throws Exception {
        assertNotNull(creditCardForm);
    }

    @Test
    public void checkCorrectCardIcon() throws Exception {
        AdyenEditText mCreditCardNo = (AdyenEditText)creditCardForm.findViewById(R.id.credit_card_no);
        mCreditCardNo.requestFocus();
        ImageSwitcher mCardType = (ImageSwitcher)creditCardForm.findViewById(R.id.cardType);
        assertNotNull(mCreditCardNo);
        assertNotNull(mCardType);
        ImageView cardIconView = (ImageView)mCardType.getCurrentView();
        assertNotNull(cardIconView);
        int imageViewResourceId = (Integer)cardIconView.getTag();
        assertEquals(R.mipmap.ady_card_unknown, imageViewResourceId);
    }

    @After
    public void tearDown() throws Exception {

    }
}