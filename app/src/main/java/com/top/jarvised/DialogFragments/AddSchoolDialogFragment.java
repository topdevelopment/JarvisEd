//
//
// TOP Development
// AddSchoolDialogFragment.java
//
//

package com.top.jarvised.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.MACallback;
import com.top.jarvised.MainActivity;
import com.top.jarvised.R;

public class AddSchoolDialogFragment extends DialogFragment {

    /*
     *
     * Member Variables
     *
     */

    private DialogFragmentCallback mDialogCallback;
    private MACallback mMACallback = null;



    /*
     *
     * Constructor
     *
     */

    public AddSchoolDialogFragment(DialogFragmentCallback callback) {

        mDialogCallback = callback;

    }



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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.dialog_fragment_add_school, container, false);

        final EditText et = layoutView.findViewById(R.id.edittext_school_name);
        Button b = layoutView.findViewById(R.id.button_add_school);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String schoolName = et.getText().toString();
                Log.i(MainActivity.LOG_TAG, "School Name Detected: " + schoolName);
                if (schoolName.trim().isEmpty()) {

                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_school_name));

                } else {

                    mDialogCallback.stringDialogResponse(schoolName);

                }

            }
        });

        b = layoutView.findViewById(R.id.button_cancel);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        return layoutView;

    }

}
