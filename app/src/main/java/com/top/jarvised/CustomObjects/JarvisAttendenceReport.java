//
//
// TOP Development
// JarvisAttendenceReport.java
//
//

package com.top.jarvised.CustomObjects;

public class JarvisAttendenceReport extends JarvisReport {

    /*
     *
     * Member Variables
     *
     */

    private String mClassSubject;
    private boolean mWasPresent;



    /*
     *
     * Constructor
     *
     */

    public JarvisAttendenceReport(ReportType type, String description, String date, String time, String reportedBy, String subject, boolean wasPresent) {

        super(type,description, date, time, reportedBy);
        mClassSubject = subject;
        mWasPresent = wasPresent;

    }



    /*
     *
     * Class Methods
     *
     */

    public String getClassSubject() {

        return mClassSubject;

    }

    public boolean getWasPresent() {

        return mWasPresent;

    }

}
