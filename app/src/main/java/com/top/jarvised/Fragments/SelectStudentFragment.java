//
//
// TOP Development
// SelectStudentFragment.java
//
//

package com.top.jarvised.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.top.jarvised.Enums.SelectionType;
import com.top.jarvised.R;

public class SelectStudentFragment extends BaseFragment {

    /*
     *
     * Member Variables
     *
     */

    private SelectionType mSelectionType;



    /*
     *
     * Constructor
     *
     */

    public static SelectStudentFragment newInstance(SelectionType type) {

        Bundle args = new Bundle();

        SelectStudentFragment fragment = new SelectStudentFragment();
        fragment.setArguments(args);
        fragment.mSelectionType = type;
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

        View layoutView = inflater.inflate(R.layout.fragment_selection, container, false);

        //todo:

        return layoutView;

    }



    /*
     *
     * Class Methods
     *
     */



}
