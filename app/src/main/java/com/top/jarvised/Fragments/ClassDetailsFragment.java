//
//
// TOP Development
// ClassDetailsFragment.java
//
//

package com.top.jarvised.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.top.jarvised.CustomObjects.JarvisClass;
import com.top.jarvised.R;

public class ClassDetailsFragment extends BaseFragment {

    /*
     *
     * Member Variables
     *
     */

    private JarvisClass mSelectedClass;

    /*
     *
     * Constructor
     *
     */

    public static ClassDetailsFragment newInstance(JarvisClass selectedClass) {

        Bundle args = new Bundle();

        ClassDetailsFragment fragment = new ClassDetailsFragment();
        fragment.setArguments(args);
        fragment.mSelectedClass = selectedClass;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_dashboard, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle().toString().matches(getResources().getString(R.string.menu_item_menu))) {

            mMACallback.toggleNavigationDrawer();

        }

        return super.onOptionsItemSelected(item);

    }



    /*
     *
     * Class Methods
     *
     */



}
