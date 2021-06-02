//
//
// TOP Development
// TeacherPageFragment.java
//
//

package com.top.jarvised.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.R;

import java.util.List;

public class TeacherPageFragment extends Fragment {

    /*
     *
     * Member Variables
     *
     */

    private JarvisTeacher mTeacher;
    private int position = -1;



    /*
     *
     * Constructor
     *
     */

    public static TeacherPageFragment newInstance(JarvisTeacher teacher) {

        Bundle args = new Bundle();

        TeacherPageFragment fragment = new TeacherPageFragment();
        fragment.setArguments(args);
        fragment.mTeacher = teacher;
        return fragment;
    }



    /*
     *
     * Lifecycle Methods
     *
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page_fragment_teacher_layout, container, false);
        rootView.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        ImageView iv = rootView.findViewById(R.id.imageview_logo);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.student_01));
        TextView tv = rootView.findViewById(R.id.textview_teacher_name);
        tv.setText(mTeacher.getFullName());
        tv = rootView.findViewById(R.id.textview_number_of_students);
        tv.setText("Number of Students: " + mTeacher.getStudentsList().size());

        return rootView;
    }


    /*
     *
     * Class Methods
     *
     */

    public void setValue(int pos) {
        position = pos;
    }

}
