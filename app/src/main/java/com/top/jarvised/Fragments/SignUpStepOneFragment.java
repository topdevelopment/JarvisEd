//
//
// TOP Development
// SignUpStepOneFragment.java
//
//

package com.top.jarvised.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Enums.DialogFragmentType;
import com.top.jarvised.Enums.ScreensID;
import com.top.jarvised.R;

public class SignUpStepOneFragment
        extends BaseFragment {

    /*
     *
     * Member Variables
     *
     */

    /*
     *
     * Constructor
     *
     */

    public static SignUpStepOneFragment newInstance() {

        Bundle args = new Bundle();

        SignUpStepOneFragment fragment = new SignUpStepOneFragment();
        fragment.setArguments(args);
        return fragment;
    }



    /*
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_signup_i_am_a, container, false);

        Button b = layoutView.findViewById(R.id.button_parent);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSignupCallback.signupAnswer(ScreensID.SignupStepOne, "Parent");

            }
        });

        b = layoutView.findViewById(R.id.button_student);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSignupCallback.signupAnswer(ScreensID.SignupStepOne, "Student");

            }
        });

        b = layoutView.findViewById(R.id.button_teacher);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSignupCallback.signupAnswer(ScreensID.SignupStepOne, "Teacher");

            }
        });

        b = layoutView.findViewById(R.id.button_administrator);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSignupCallback.signupAnswer(ScreensID.SignupStepOne, "Administrator");

            }
        });

        b = layoutView.findViewById(R.id.button_just_curious);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSignupCallback.signupAnswer(ScreensID.SignupStepOne, "Just Curious");

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

}
