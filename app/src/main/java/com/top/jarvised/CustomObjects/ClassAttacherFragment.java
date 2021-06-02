//
//
// TOP Development
// ClassAttacherFragment.java
//
//

package com.top.jarvised.CustomObjects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.top.jarvised.Fragments.BaseFragment;
import com.top.jarvised.R;

import java.util.ArrayList;

public class ClassAttacherFragment extends BaseFragment {

    /*
     *
     * Member Variables
     *
     */

    private ArrayList<JarvisClass> mClassList = new ArrayList<>();



    /*
     *
     * Constructor
     *
     */

    public static ClassAttacherFragment newInstance() {

        Bundle args = new Bundle();

        ClassAttacherFragment fragment = new ClassAttacherFragment();
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


        setHasOptionsMenu(true);

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_dashboard, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);

    }



    /*
     *
     * Class Methods
     *
     */

}
