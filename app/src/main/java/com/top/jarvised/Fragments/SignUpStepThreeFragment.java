//
//
// TOP Development
// SignUpStepThreeFragment.java
//
//

package com.top.jarvised.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.FirebaseCommCallback;
import com.top.jarvised.Callbacks.LoginCallback;
import com.top.jarvised.CustomObjects.JarvisAdmin;
import com.top.jarvised.CustomObjects.JarvisUser;
import com.top.jarvised.Enums.DialogFragmentType;
import com.top.jarvised.Enums.ScreensID;
import com.top.jarvised.MainActivity;
import com.top.jarvised.R;
import com.top.jarvised.Services.FirebaseCommService;

import java.util.ArrayList;

public class SignUpStepThreeFragment
        extends BaseFragment
        implements FirebaseCommCallback, DialogFragmentCallback {

    /*
     *
     * Member Variables
     *
     */

    private LoginCallback mLoginCallback = null;
    private int mSuccessfulResponses = 0;



    /*
     *
     * Constructor
     *
     */

    public static SignUpStepThreeFragment newInstance() {

        Bundle args = new Bundle();

        SignUpStepThreeFragment fragment = new SignUpStepThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }



    /*
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof LoginCallback) {

            mLoginCallback = (LoginCallback) context;

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_signup_new_user_details, container, false);

        final EditText fullNameET = layoutView.findViewById(R.id.edittext_fullname);
        final EditText emailET = layoutView.findViewById(R.id.edittext_email);
        final EditText confirmEmailET = layoutView.findViewById(R.id.edittext_confirm_email);
        final EditText passwordET = layoutView.findViewById(R.id.edittext_password);
        final EditText confirmPasswordET = layoutView.findViewById(R.id.edittext_confirm_password);

        Button b = layoutView.findViewById(R.id.button_create_account);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptCreateAccount(fullNameET.getText().toString(), emailET.getText().toString(),
                        confirmEmailET.getText().toString(), passwordET.getText().toString(), confirmPasswordET.getText().toString());

            }
        });

        b = layoutView.findViewById(R.id.button_cancel);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSignupCallback.cancelSignup();

            }
        });

        return layoutView;

    }



    /*
     *
     * Class Methods
     *
     */

    private void attemptCreateAccount(String fullName, String email, String confirmEmail, String password, String confirmPassword) {

        if (fullName.trim().isEmpty()) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_signup_no_fullname));
            return;

        }

        if (email.trim().isEmpty() || confirmEmail.trim().isEmpty()) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_signup_no_email));
            return;

        }

        if (!email.trim().matches(confirmEmail.trim())) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_signup_email_mismatch));
            return;

        }

        if (password.trim().isEmpty() || confirmPassword.trim().isEmpty()) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_signup_no_password));
            return;

        }

        if (password.length() < 6) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_signup_password_length_short));
            return;

        }

        if (!password.trim().matches(confirmPassword.trim())) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_signup_password_mismatch));
            return;

        }

        mMACallback.startDialogFragment(DialogFragmentType.LOADING, this);
        mMACallback.getFirebaseCommService(this).createAccount(email, password,
                mSignupCallback.getSignupAnswer(ScreensID.SignupStepOne),
                mSignupCallback.getSignupAnswer(ScreensID.SignupStepTwo), fullName);

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponse(FirebaseCommService.FirebaseMethod method) {

        Log.i(MainActivity.LOG_TAG, "Success from Method: " + method.toString() + " in Callback Method: successResponse");

        if (method == FirebaseCommService.FirebaseMethod.CREATE_ACCOUNT) {

            if (mSuccessfulResponses == 0) mSuccessfulResponses++;
            else if (mSuccessfulResponses == 1) {

                final SignUpStepThreeFragment frag = this;
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        View v = getView();
                        if (v != null) {

                            EditText emailET = v.findViewById(R.id.edittext_email);
                            mMACallback.getFirebaseCommService(frag).getUserInfo(emailET.getText().toString());

                        }

                    }
                });

            }

        } else {

            mMACallback.closeAllDialogs();
            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseSingle(FirebaseCommService.FirebaseMethod method, Object object) {

        Log.i(MainActivity.LOG_TAG, "Success from Method: " + method.toString() + " in Callback Method: successResponseSingle");

        if (method == FirebaseCommService.FirebaseMethod.GET_USER_INFO) {

            if (object instanceof JarvisUser) {

                mMACallback.closeAllDialogs();
                mLoginCallback.setCurrentUser((JarvisUser) object);

            }

        } else {

            mMACallback.closeAllDialogs();
            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseList(FirebaseCommService.FirebaseMethod method, ArrayList objectList) {

        Log.i(MainActivity.LOG_TAG, "Success from Method: " + method.toString() + " in Callback Method: successResponseList");

        mMACallback.closeAllDialogs();
        mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

    }

    @Override // FirebaseCommCallback Interface Method
    public void errorResponse(FirebaseCommService.FirebaseMethod method) {

        Log.i(MainActivity.LOG_TAG, "Error from Method: " + method.toString() + " in Callback Method: errorResponse");

    }

    @Override // DialogFragmentCallback Interface Method
    public void stringDialogResponse(String response) {

        if (!response.isEmpty()) {

            mMACallback.closeAllDialogs();
            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

}
