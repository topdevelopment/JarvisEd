//
//
// TOP Development
// JarvisClass.java
//
//

package com.top.jarvised.CustomObjects;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class JarvisClass {

    /*
     *
     * Member Variables
     *
     */

    private String mTitle;
    private @Nullable String mAttachedTeacherName;
    private @Nullable List<String> mStudentNames;



    /*
     *
     * Constructor
     *
     */

    public JarvisClass(String title, @Nullable String teacherName, @Nullable List<String> studentNames) {

        mTitle = title;
        mAttachedTeacherName = teacherName;
        mStudentNames = studentNames;

    }



    /*
     *
     * Class Methods
     *
     */

    public String getClassTitle() {

        return mTitle;

    }

    public @Nullable String getAttachedTeacher() {

        return mAttachedTeacherName;

    }

    public @Nullable List<String> getStudentNames() {

        return mStudentNames;

    }

}
