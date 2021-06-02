//
//
// TOP Development
// StudentPageFragment.java
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

import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.R;

import java.util.List;

public class StudentPageFragment extends Fragment {

    /*
     *
     * Member Variables
     *
     */

    private JarvisStudent mStudent;
    private int position = -1;



    /*
     *
     * Constructor
     *
     */

    public static StudentPageFragment newInstance(JarvisStudent student) {

        Bundle args = new Bundle();

        StudentPageFragment fragment = new StudentPageFragment();
        fragment.setArguments(args);
        fragment.mStudent = student;
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
//        ViewGroup rootView = (ViewGroup) inflater.inflate(
//                R.layout.fragment_screen_slide, container, false);
//
//        ImageView iv = rootView.findViewById(R.id.imageview_logo);
//        TextView tv = rootView.findViewById(R.id.textview_title);
//        rootView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//        iv.setImageDrawable(getResources().getDrawable(R.drawable.student_01));
//        tv.setText(mStudents.get(position).getFullName());

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page_fragment_student_layout, container, false);
        rootView.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        ImageView iv = rootView.findViewById(R.id.imageview_logo);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.student_01));
        TextView tv = rootView.findViewById(R.id.textview_student_name);
        tv.setText(mStudent.getFullName());
        tv = rootView.findViewById(R.id.textview_student_birthday);
        tv.setText(mStudent.getBirthday());

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
