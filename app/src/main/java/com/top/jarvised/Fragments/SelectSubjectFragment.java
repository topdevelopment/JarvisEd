//
//
// TOP Development
// SelectSubjectFragment.java
//
//

package com.top.jarvised.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectSubjectFragment extends Fragment {

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

    public static SelectSubjectFragment newInstance() {

        Bundle args = new Bundle();

        SelectSubjectFragment fragment = new SelectSubjectFragment();
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

        return super.onCreateView(inflater, container, savedInstanceState);

    }



    /*
     *
     * Class Methods
     *
     */

}
