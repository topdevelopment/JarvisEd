//
//
// TOP Development
// LoadingDialogFragment.java
//
//

package com.top.jarvised.DialogFragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.top.jarvised.Callbacks.MACallback;
import com.top.jarvised.R;

public class LoadingDialogFragment extends DialogFragment {

    /*
     *
     * Constructor
     *
     */

    public LoadingDialogFragment() { }



    /*
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setCancelable(false);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        ProgressDialog dialog = new ProgressDialog(getActivity(), getTheme());
        dialog.setTitle(getResources().getString(R.string.dialog_title_loading));
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return dialog;

    }

}
