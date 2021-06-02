//
//
// TOP Development
// AddStudentFragment.java
//
//

package com.top.jarvised.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.top.jarvised.Callbacks.AddStudentTeacherCallback;
import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.FirebaseCommCallback;
import com.top.jarvised.CustomObjects.JarvisAdmin;
import com.top.jarvised.Enums.DialogFragmentType;
import com.top.jarvised.Enums.ScreensID;
import com.top.jarvised.R;
import com.top.jarvised.Services.FirebaseCommService;

import java.util.ArrayList;

public class AddStudentFragment
        extends BaseFragment
        implements FirebaseCommCallback, DialogFragmentCallback {

    /*
     *
     * Member Variables
     *
     */

    private AddStudentTeacherCallback mAddStudentTeacherCallback = null;
    private String mBirthday = "";



    /*
     *
     * Constructor
     *
     */

    public static AddStudentFragment newInstance() {

        Bundle args = new Bundle();

        AddStudentFragment fragment = new AddStudentFragment();
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

        if (context instanceof AddStudentTeacherCallback) {
            mAddStudentTeacherCallback = (AddStudentTeacherCallback) context;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_add_student, container, false);
        setHasOptionsMenu(true);
        final AddStudentFragment frag = this;

        final CheckBox conditionCB = layoutView.findViewById(R.id.checkbox_condition);
        final CheckBox atriskCB = layoutView.findViewById(R.id.checkbox_at_risk);
        final EditText etFullName = layoutView.findViewById(R.id.edittext_fullname);
        final EditText etEmail = layoutView.findViewById(R.id.edittext_email);
        final EditText etConfirmEmail = layoutView.findViewById(R.id.edittext_confirm_email);

        conditionCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    atriskCB.setChecked(false);
                }

            }
        });

        atriskCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    conditionCB.setChecked(false);
                }

            }
        });

        Button b = layoutView.findViewById(R.id.button_birthday);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mAddStudentTeacherCallback.birthdayButtonSelected();
                mBirthday = "01/01/2020";

            }
        });

        b = layoutView.findViewById(R.id.button_create);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname = etFullName.getText().toString();
                String email = etEmail.getText().toString();
                String confirmEmail = etConfirmEmail.getText().toString();
                if (attemptCreateStudent(fullname, email, confirmEmail, conditionCB.isChecked())) {

                    mMACallback.startDialogFragment(DialogFragmentType.LOADING, frag);
                    if (mMACallback.getCurrentUser() instanceof JarvisAdmin) {

                        JarvisAdmin admin = (JarvisAdmin) mMACallback.getCurrentUser();
                        mMACallback.getFirebaseCommService(frag).addNewStudent(admin.getContactEmail(),
                                admin.getStudentList(), fullname, email, mBirthday, conditionCB.isChecked());

                    }

                }

            }
        });

        b = layoutView.findViewById(R.id.button_cancel);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMACallback.selectScreen(ScreensID.StudentConfig);

            }
        });

        return layoutView;
    }



    /*
     *
     * Class Methods
     *
     */

    @Override // DialogFragmentCallback Interface Method
    public void stringDialogResponse(String response) {

        // do nothing

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponse(FirebaseCommService.FirebaseMethod method) {

        mMACallback.closeAllDialogs();
        if (method == FirebaseCommService.FirebaseMethod.ADD_NEW_STUDENT) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_student_created));

        } else {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseSingle(FirebaseCommService.FirebaseMethod method, Object object) {

        mMACallback.closeAllDialogs();
        mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseList(FirebaseCommService.FirebaseMethod method, ArrayList objectList) {

        mMACallback.closeAllDialogs();
        mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

    }

    @Override // FirebaseCommCallback Interface Method
    public void errorResponse(FirebaseCommService.FirebaseMethod method) {

        mMACallback.closeAllDialogs();
        mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

    }

    private boolean attemptCreateStudent(String fullname, String email1, String email2, boolean condition) {

        if (fullname.isEmpty()) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_fullname));
            return false;

        }

        if (mBirthday.isEmpty()) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_birthday));
            return false;

        }

        if (email1.isEmpty()) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_email));
            return false;

        }

        if (email2.isEmpty()) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_email));
            return false;

        }

        if (!isValidEmail(email1, email2)) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_invalid_email));
            return false;

        }

        return true;

    }

    private boolean isValidEmail(String email1, String email2) {

        if (email1.matches(email2)) {

            //todo: validate email

            return true;

        }

        return false;

    }

}
