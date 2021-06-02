//
//
// TOP Development
// JarvisAdmin.java
//
//

package com.top.jarvised.CustomObjects;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class JarvisAdmin extends JarvisUser {

    /*
     *
     * Member Variables
     *
     */

    private List<String> mTeacherList;
    private List<JarvisTeacher> mTeachers;
    private List<String> mStudentList;
    private List<JarvisStudent> mStudents;



    /*
     *
     * Constructor
     *
     */

    public JarvisAdmin(String fullname, String email, List<String> teachers, List<String> students) {

        super(email, fullname);
        mTeacherList = teachers;
        mTeachers = new ArrayList<>();
        mStudentList = students;
        mStudents = new ArrayList<>();

    }



    /*
     *
     * Class Methods
     *
     */

    public List<String> getTeachersList() {

        return mTeacherList;

    }

    public List<JarvisTeacher> getTeachers() {

        return mTeachers;

    }

    public void setTeachers(List<JarvisTeacher> teachers) {

        mTeachers = teachers;

    }

    public List<String> getStudentList() {

        return mStudentList;

    }

    public void setStudents(List<JarvisStudent> students) {

        mStudents = students;

    }

    public List<JarvisStudent> getStudents() {

        return mStudents;

    }



    /*
     *
     * New Class: JarvisParentAdmin
     *
     */

    public class JarvisParentAdmin extends JarvisAdmin {

        /*
         *
         * Constructor
         *
         */

        public JarvisParentAdmin(String fullname, String email, List<String> teachers, List<String> students) {

            super(fullname, email, teachers, students);

        }

    }



    /*
     *
     * New Class: JarvisTeacherAdmin
     *
     */

    public class JarvisTeacherAdmin extends JarvisAdmin {

        /*
         *
         * Constructor
         *
         */

        public JarvisTeacherAdmin(String fullname, String email, List<String> teachers, List<String> students) {

            super(fullname, email, teachers, students);

        }

    }

}
