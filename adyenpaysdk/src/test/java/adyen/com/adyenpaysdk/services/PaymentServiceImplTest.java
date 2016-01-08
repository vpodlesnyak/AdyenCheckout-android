package adyen.com.adyenpaysdk.services;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import adyen.com.adyenpaysdk.BuildConfig;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

/**
 * Created by andrei on 12/24/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=22)
public class PaymentServiceImplTest {

    @Test
    public void testFetchPublicKey() throws Exception {
        PaymentService paymentService = mock(PaymentServiceImpl.class);
        PaymentServiceImpl.VolleyCallback callback = mock(PaymentServiceImpl.VolleyCallback.class);
        String url = "https://test.adyen.com/hpp/cse/8714279602311541/json.shtml";

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                PaymentServiceImpl.VolleyCallback callback = (PaymentServiceImpl.VolleyCallback)invocation.getArguments()[1];
                callback.onSuccess(new JSONObject());
                return null;
            }
        }).when(paymentService).fetchPublicKey(url, callback);

        paymentService.fetchPublicKey(url, callback);

        verify(callback, only()).onSuccess((JSONObject) any());
    }
}