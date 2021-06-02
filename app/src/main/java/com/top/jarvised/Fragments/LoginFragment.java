//
//
// TOP Development
// LoginCallback.java
//
//

package com.top.jarvised.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.FirebaseCommCallback;
import com.top.jarvised.Callbacks.LoginCallback;
import com.top.jarvised.CustomObjects.JarvisAdmin;
import com.top.jarvised.CustomObjects.JarvisParent;
import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.CustomObjects.JarvisUser;
import com.top.jarvised.Enums.DialogFragmentType;
import com.top.jarvised.Enums.ScreensID;
import com.top.jarvised.MainActivity;
import com.top.jarvised.R;
import com.top.jarvised.Services.FirebaseCommService;

import java.util.ArrayList;

public class LoginFragment
        extends BaseFragment
        implements FirebaseCommCallback, DialogFragmentCallback {

    /*
     *
     * Member Variables
     *
     */

    private FirebaseCommService mFirebaseService = null;
    private LoginCallback mLoginCallback = null;



    /*
     *
     * Constructor
     *
     */

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
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

        View layoutView = inflater.inflate(R.layout.fragment_login, container, false);
        final DialogFragmentCallback loginFrag = this;
        final EditText emailET = layoutView.findViewById(R.id.edittext_email);
        final EditText passwordET = layoutView.findViewById(R.id.edittext_password);

        passwordET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            String email = emailET.getText().toString();
                            String password = passwordET.getText().toString();
                            attemptLogin(email, password);
                            return true;
                        default:
                            break;
                    }
                }

                return false;
            }
        });

        Button b = layoutView.findViewById(R.id.button_help);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        b = layoutView.findViewById(R.id.button_login);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                attemptLogin(email, password);

            }
        });

        b = layoutView.findViewById(R.id.button_signup);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMACallback.selectScreen(ScreensID.SignupStepOne);

            }
        });

        return layoutView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mFirebaseService = mMACallback.getFirebaseCommService(this);

    }



    /*
     *
     * Class Methods
     *
     */

    private void attemptLogin(String email, String password) {

        if (email.isEmpty()) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_invalid_email));

        } else {

            if (password.isEmpty()) {

                mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_password));

            } else {

                mMACallback.startDialogFragment(DialogFragmentType.LOADING, this);
                mFirebaseService.attemptLogin(email, password);

            }

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponse(FirebaseCommService.FirebaseMethod method) {

        // there should be no responses going here

        mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseSingle(FirebaseCommService.FirebaseMethod method, Object object) {

        if (method == FirebaseCommService.FirebaseMethod.GET_USER_INFO) {

            if (object instanceof JarvisUser) {

                Log.i(MainActivity.LOG_TAG, "object IS instance of JarvisUser");
                mMACallback.closeAllDialogs();
                mLoginCallback.setCurrentUser((JarvisUser) object);

            } else {

                Log.i(MainActivity.LOG_TAG, "object IS NOT instance of JarvisUser");
                mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

            }

        } else {

            // there should be no responses going here

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseList(FirebaseCommService.FirebaseMethod method, ArrayList objectList) {

        // there should be no responses going here

    }

    @Override // FirebaseCommCallback Interface Method
    public void errorResponse(FirebaseCommService.FirebaseMethod method) {

        mMACallback.closeAllDialogs();
        if (method == FirebaseCommService.FirebaseMethod.LOGIN) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_failed_login));

        } else if (method == FirebaseCommService.FirebaseMethod.GET_USER_INFO) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_failed_retrieving_user_info));

        } else {

            // there should be no other responses

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // DialogFragmentCallback Interface Method
    public void stringDialogResponse(String response) {

        // nothing should happen here

    }

}
