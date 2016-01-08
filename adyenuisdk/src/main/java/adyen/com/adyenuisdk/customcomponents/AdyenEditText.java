package adyen.com.adyenuisdk.customcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import adyen.com.adyenuisdk.listener.EditTextImeBackListener;


/**
 * Created by andrei on 12/4/15.
 */
public class AdyenEditText extends EditText {

    private EditTextImeBackListener mOnImeBack;

    public AdyenEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public AdyenEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public AdyenEditText(Context context) {
        super(context);

    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mOnImeBack != null) {
                mOnImeBack.onImeBack(this, this.getText().toString());
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnEditTextImeBackListener(EditTextImeBackListener listener) {
        mOnImeBack = listener;
    }

}
