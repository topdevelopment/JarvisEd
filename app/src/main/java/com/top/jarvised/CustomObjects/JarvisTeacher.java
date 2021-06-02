//
//
// TOP Development
// JarvisTeacher.java
//
//

package com.top.jarvised.CustomObjects;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class JarvisTeacher extends JarvisUser {

    /*
     *
     * Member Variables
     *
     */

    private String mBirthday;
    private String mSubject;
    private List<String> mStudentsList;
    private List<JarvisStudent> mStudents;



    /*
     *
     * Constructor
     *
     */

    public JarvisTeacher(String fullname, String birthday, String email, String subject, List<String> students) {

        super(email, fullname);
        mBirthday = birthday;
        mSubject = subject;
        mStudentsList = students;
        mStudents = new ArrayList<>();

    }



    /*
     *
     * Class Methods
     *
     */

    public String getBirthday() {

        return mBirthday;

    }

    public String getSubject() {

        return mSubject;

    }

    public List<String> getStudentsList() {

        return mStudentsList;

    }

    public List<JarvisStudent> getStudents() {

        return mStudents;

    }

    public void setStudents(List<JarvisStudent> students) {

        mStudents = students;

    }

}
