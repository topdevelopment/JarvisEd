//
//
// TOP Development
// JarvisParent.java
//
//

package com.top.jarvised.CustomObjects;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class JarvisParent extends JarvisUser {

    /*
     *
     * Member Variables
     *
     */

    private List<String> mStudentList;
    private List<JarvisStudent> mStudents;



    /*
     *
     * Constructor
     *
     */

    public JarvisParent(String fullname, String email, List<String> students) {

        super(email, fullname);
        mStudentList = students;
        mStudents = new ArrayList<>();

    }



    /*
     *
     * Class Methods
     *
     */

    public List<String> getStudentsList() {

        return mStudentList;

    }

    public List<JarvisStudent> getStudents() {

        return mStudents;

    }

    public void setStudents(List<JarvisStudent> students) {

        mStudents = students;

    }

}
