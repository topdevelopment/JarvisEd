//
//
// TOP Development
// BaseFragment.java
//
//

package com.top.jarvised.Fragments;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.top.jarvised.Callbacks.AddStudentTeacherCallback;
import com.top.jarvised.Callbacks.LoginCallback;
import com.top.jarvised.Callbacks.MACallback;
import com.top.jarvised.Callbacks.SignUpCallback;

public class BaseFragment extends Fragment {

    /*
     *
     * Member Variables
     *
     */

    MACallback mMACallback = null;
    SignUpCallback mSignupCallback = null;
    AddStudentTeacherCallback mAddStudentTeacherCallback = null;
    boolean mErrorOccurred = false;



    /*
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof MACallback) {

            mMACallback = (MACallback) context;

        }

        if ((this instanceof SignUpStepOneFragment ||
                this instanceof SignUpStepTwoFragment ||
                this instanceof SignUpStepThreeFragment)
                && context instanceof SignUpCallback) {

            mSignupCallback = (SignUpCallback) context;

        }

        if ((this instanceof StudentConfigurationFragment ||
                this instanceof TeacherConfigurationFragment ||
                this instanceof AddStudentFragment ||
                this instanceof AddTeacherFragment)
                && context instanceof AddStudentTeacherCallback) {

            mAddStudentTeacherCallback = (AddStudentTeacherCallback) context;

        }

    }



    /*
     *
     * Class Methods
     *
     */



}
