//
//
// TOP Development
// AddAbsenceReportDialogFragment.java
//
//

package com.top.jarvised.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.top.jarvised.Callbacks.MACallback;
import com.top.jarvised.Callbacks.ReportCallback;
import com.top.jarvised.R;

public class AddAbsenceReportDialogFragment extends DialogFragment {

    /*
     *
     * Member Variables
     *
     */

    private ReportCallback mReportCallback;
    private MACallback mMACallback;



    /*
     *
     * Constructor
     *
     */

    public static AddAbsenceReportDialogFragment newInstance(ReportCallback callback) {

        Bundle args = new Bundle();

        AddAbsenceReportDialogFragment fragment = new AddAbsenceReportDialogFragment();
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

        View layoutView = inflater.inflate(R.layout.dialog_add_absence_report_layout, container, false);



        return layoutView;

    }



    /*
     *
     * Class Methods
     *
     */

}
