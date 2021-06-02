//
//
// TOP Development
// AddFocusReportDialogFragment.java
//
//

package com.top.jarvised.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.top.jarvised.Callbacks.MACallback;
import com.top.jarvised.Callbacks.ReportCallback;
import com.top.jarvised.R;

public class AddFocusReportDialogFragment extends DialogFragment {

    /*
     *
     * Member Variables
     *
     */

    private MACallback mMACallback;
    private ReportCallback mReportCallback;



    /*
     *
     * Constructor
     *
     */

    public static AddFocusReportDialogFragment newInstance(ReportCallback callback) {

        Bundle args = new Bundle();

        AddFocusReportDialogFragment fragment = new AddFocusReportDialogFragment();
        fragment.setArguments(args);
        fragment.mReportCallback = callback;
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
        if (context instanceof MACallback) {
            mMACallback = (MACallback) context;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //todo: create view layout

        View layoutView = inflater.inflate(R.layout.dialog_add_focus_report_layout, container, false);



        return layoutView;

    }



    /*
     *
     * Class Methods
     *
     */

}
