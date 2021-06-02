//
//
// TOP Development
// StudentPagerAdapter.java
//
//

package com.top.jarvised.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.Fragments.StudentPageFragment;

import java.util.List;

public class StudentPagerAdapter extends FragmentStatePagerAdapter {

    /*
     *
     * Member Variables
     *
     */

    private List<JarvisStudent> mStudents;



    /*
     *
     * Constructor
     *
     */

    public StudentPagerAdapter(List<JarvisStudent> students, FragmentManager fm) {
        super(fm);
        mStudents = students;
    }



    /*
     *
     * Class Methods
     *
     */

    @Override
    public Fragment getItem(int position) {

        StudentPageFragment frag = StudentPageFragment.newInstance(mStudents.get(position));
        frag.setValue(position);
        return frag;

    }

    @Override
    public int getCount() {

        return mStudents.size();

    }

}
