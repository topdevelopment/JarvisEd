//
//
// TOP Development
// TeacherDetailsFragment.java
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
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.Enums.ScreensID;
import com.top.jarvised.R;

public class TeacherDetailsFragment extends BaseFragment {

    /*
     *
     * Member Variables
     *
     */

    private JarvisTeacher mCurrentTeacher;



    /*
     *
     * Constructor
     *
     */

    public static TeacherDetailsFragment newInstance(JarvisTeacher teacher) {

        Bundle args = new Bundle();

        TeacherDetailsFragment fragment = new TeacherDetailsFragment();
        fragment.setArguments(args);
        fragment.mCurrentTeacher = teacher;
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

        View layoutView = inflater.inflate(R.layout.fragment_add_teacher, container, false);
        setHasOptionsMenu(true);

        EditText et = layoutView.findViewById(R.id.edittext_fullname);
        et.setText(mCurrentTeacher.getFullName());

        et = layoutView.findViewById(R.id.edittext_email);
        et.setText(mCurrentTeacher.getContactEmail());

        et = layoutView.findViewById(R.id.edittext_confirm_email);
        et.setVisibility(View.GONE);

        Button b = layoutView.findViewById(R.id.button_birthday);
        b.setText(mCurrentTeacher.getBirthday());

        b = layoutView.findViewById(R.id.button_subject);
        b.setText(mCurrentTeacher.getSubject());

        b = layoutView.findViewById(R.id.button_cancel);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMACallback.selectScreen(ScreensID.TeacherConfig);

            }
        });

        b = layoutView.findViewById(R.id.button_create);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        return layoutView;

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
