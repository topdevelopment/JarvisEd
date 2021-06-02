//
//
// TOP Development
// JarvisStudent.java
//
//

package com.top.jarvised.CustomObjects;

import java.util.ArrayList;
import java.util.List;

public class JarvisStudent extends JarvisUser {

    /*
     *
     * Member Variables
     *
     */

    private String mBirthday;
    private boolean mCondition;
    private List<JarvisReport> mReports;



    /*
     *
     * Constructor
     *
     */

    public JarvisStudent(String fullname, String birthday, String email, boolean condition) {

        super(email, fullname);
        mBirthday = birthday;
        mCondition = condition;
        mReports = new ArrayList<>();

    }



    /*
     *
     * Class Methods
     *
     */

    public String getBirthday() {

        return mBirthday;

    }

    public boolean hasCondition() {

        return mCondition;

    }

    public List<JarvisReport> getReports() {

        return mReports;

    }

    public void updateReports(List<JarvisReport> reports) {

        mReports = reports;

    }

}
