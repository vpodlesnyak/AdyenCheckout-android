package adyen.com.adyenuisdk;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import java.util.Calendar;

import adyen.com.adyenpaysdk.Adyen;
import adyen.com.adyenpaysdk.util.CardType;
import adyen.com.adyenuisdk.customcomponents.AdyenEditText;

/**
 * Created by andrei on 12/1/15.
 */
public class CreditCardForm extends LinearLayout {

    private AdyenEditText mCreditCardNo;
    private AdyenEditText mCreditCardExpDate;
    private AdyenEditText mCreditCardCvc;
    private ImageSwitcher mCardType;

    private boolean keyDel = false;
    private static boolean mValidCardNumber = false;
    private static boolean mValidExpiryDate = false;
    private static boolean mValidCvc = false;


    public CreditCardForm(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.payment_form, this);
    }

    public CreditCardForm(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.credit_card_form, this);
        initViews(context, attrs);
    }

    public CreditCardForm(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
        initViews(context, attrs);
    }

    private void initViews(final Context context, AttributeSet attrs) {
        mCreditCardNo = (AdyenEditText)findViewById(R.id.credit_card_no);
        mCreditCardNo.requestFocus();
        mCreditCardExpDate = (AdyenEditText)findViewById(R.id.credit_card_exp_date);
        mCreditCardCvc = (AdyenEditText)findViewById(R.id.credit_card_cvc);

        mCardType = (ImageSwitcher)findViewById(R.id.cardType);
        mCardType.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myImageView = new ImageView(context);
                myImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myImageView.setLayoutParams(new ImageSwitcher.LayoutParams
                        (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                myImageView.setImageResource(R.mipmap.ady_card_unknown);
                myImageView.setTag(R.mipmap.ady_card_unknown);
                return myImageView;
            }
        });
        mCardType.setInAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
        mCardType.setOutAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));

        initFormStyle();
        initCreditCardEditText();
        initExpDateEditText();
        initCvcText();
    }

    private void initFormStyle() {
        mCreditCardNo.getBackground().setColorFilter(getResources().getColor(R.color.light_grey_border),
                PorterDuff.Mode.SRC_ATOP);
        mCreditCardExpDate.getBackground().setColorFilter(getResources().getColor(R.color.light_grey_border),
                PorterDuff.Mode.SRC_ATOP);
        mCreditCardCvc.getBackground().setColorFilter(getResources().getColor(R.color.light_grey_border),
                PorterDuff.Mode.SRC_ATOP);
    }

    private void initCreditCardEditText() {
        mCreditCardNo.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[0-9 ]+")) {
                            return cs;
                        }
                        return "";
                    }
                }
        });

        mCreditCardNo.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    setCardImageByType(s.toString());
                }
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                // Insert char where needed.
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });

        mCreditCardNo.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("OnFocusChange: ", String.valueOf(hasFocus));
                String creditCardNumber = mCreditCardNo.getText().toString();
                if (!TextUtils.isEmpty(creditCardNumber) && !Adyen.getInstance().luhnCheck(creditCardNumber.toString()) && !hasFocus) {
                    mCreditCardNo.setTextColor(getResources().getColor(R.color.red));
                    mValidCardNumber = false;
                } else if (Adyen.getInstance().luhnCheck(creditCardNumber.toString()) || hasFocus) {
                    mCreditCardNo.setTextColor(getResources().getColor(R.color.black));
                    mValidCardNumber = true;
                }
            }
        });

    }

    private void initExpDateEditText() {
        mCreditCardExpDate.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(5)
        });

        mCreditCardExpDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before == 1) {
                    keyDel = true;
                } else {
                    keyDel = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!keyDel) {
                    if (s.length() == 1) {
                        if (Integer.valueOf(s.toString()) > 1) {
                            s.insert(0, "0");
                        }
                    }
                    if (s.length() == 2 && Integer.valueOf(s.toString()) > 12) {
                        s.delete(0, 2);
                    } else if (s.length() == 2) {
                        s.insert(2, "/");
                    }
                }
                if(s.toString().indexOf("/") < 0 && s.length() > 2) {
                    s.insert(2, "/");
                }
                if (s.length() == 5) {
                    if (!validDate()) {
                        mCreditCardExpDate.setTextColor(getResources().getColor(R.color.red));
                        mValidExpiryDate = false;
                    } else {
                        mCreditCardExpDate.setTextColor(getResources().getColor(R.color.black));
                        mValidExpiryDate = true;
                    }
                } else {
                    mCreditCardExpDate.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
    }

    public void initCvcText() {
        mCreditCardCvc.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(3)
        });

        mCreditCardCvc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 3) {
                    mValidCvc = true;
                } else {
                    mValidCvc = false;
                }
            }
        });
    }

    private boolean validDate() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int filledInMonth = Integer.valueOf(mCreditCardExpDate.getText().toString().split("/")[0]);
        int filledInYear = Integer.valueOf("20" + mCreditCardExpDate.getText().toString().split("/")[1]);

        if(filledInYear < year) {
            return false;
        } else if (filledInYear == year && filledInMonth < month) {
            return false;
        } else if (filledInYear == year && filledInMonth > month) {
            return true;
        } else if(filledInYear > year) {
            return true;
        }

        return false;
    }

    private void setCardImageByType(String cardNumber) {
        CardType cardType = CardType.detect(cardNumber.replaceAll(" ", ""));

        switch (cardType) {
            case AMEX:
                mCardType.setImageResource(R.mipmap.ady_card_amex);
                mCardType.setTag(R.mipmap.ady_card_amex);
                break;
            case DINNERS:
                mCardType.setImageResource(R.mipmap.ady_card_diners);
                mCardType.setTag(R.mipmap.ady_card_diners);
                break;
            case DISCOVER:
                mCardType.setImageResource(R.mipmap.ady_card_discover);
                mCardType.setTag(R.mipmap.ady_card_discover);
                break;
            case ELO:
                mCardType.setImageResource(R.mipmap.ady_card_elo);
                mCardType.setTag(R.mipmap.ady_card_elo);
                break;
            case HIPERCARD:
                mCardType.setImageResource(R.mipmap.ady_card_hipercard);
                mCardType.setTag(R.mipmap.ady_card_hipercard);
                break;
            case JCB:
                mCardType.setImageResource(R.mipmap.ady_card_jcb);
                mCardType.setTag(R.mipmap.ady_card_jcb);
                break;
            case VISA:
                mCardType.setImageResource(R.mipmap.ady_card_visa);
                mCardType.setTag(R.mipmap.ady_card_visa);
                break;
            case MASTERCARD:
                mCardType.setImageResource(R.mipmap.ady_card_mastercard);
                mCardType.setTag(R.mipmap.ady_card_mastercard);
                break;
            case UNION_PAY:
                mCardType.setImageResource(R.mipmap.ady_card_unionpay);
                mCardType.setTag(R.mipmap.ady_card_unionpay);
                break;
            default:
                mCardType.setImageResource(R.mipmap.ady_card_unknown);
                break;
        }
    }

    public static boolean isValid() {
        return mValidCardNumber && mValidExpiryDate && mValidCvc;
    }
}
