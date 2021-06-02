//
//
// TOP Development
// TeacherPagerAdapter.java
//
//

package com.top.jarvised.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.Fragments.TeacherPageFragment;

import java.util.List;

public class TeacherPagerAdapter extends FragmentStatePagerAdapter {

    /*
     *
     * Member Variables
     *
     */

    private List<JarvisTeacher> mTeachers;



    /*
     *
     * Constructor
     *
     */

    public TeacherPagerAdapter(List<JarvisTeacher> teachers, FragmentManager fm) {

        super(fm);
        mTeachers = teachers;

    }



    /*
     *
     * Class Methods
     *
     */

    @Override
    public Fragment getItem(int position) {

        TeacherPageFragment frag = TeacherPageFragment.newInstance(mTeachers.get(position));
        frag.setValue(position);
        return frag;

    }

    @Override
    public int getCount() {

        return mTeachers.size();

    }

}
