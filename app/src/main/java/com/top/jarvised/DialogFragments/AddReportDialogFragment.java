//
//
// TOP Development
// AddReportDialogFragment.java
//
//

package com.top.jarvised.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.MACallback;
import com.top.jarvised.MainActivity;
import com.top.jarvised.R;

public class AddReportDialogFragment extends DialogFragment {

    /*
     *
     * Member Variables
     *
     */

    private DialogFragmentCallback mDialogCallback;
    private MACallback mMACallback;



    /*
     *
     * Constructor
     *
     */

    public AddReportDialogFragment(DialogFragmentCallback callback) {

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

        View layoutView = inflater.inflate(R.layout.dialog_fragment_add_report, container, false);

        Button b = layoutView.findViewById(R.id.button_absence);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialogCallback.stringDialogResponse("Absense");

            }
        });

        b = layoutView.findViewById(R.id.button_isolation);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialogCallback.stringDialogResponse("Isolation");

            }
        });

        b = layoutView.findViewById(R.id.button_focus);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialogCallback.stringDialogResponse("Focus");

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
