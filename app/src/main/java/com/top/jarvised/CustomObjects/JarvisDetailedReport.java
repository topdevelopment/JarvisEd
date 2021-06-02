//
//
// TOP Development
// JarvisDetailedReport.java
//
//

package com.top.jarvised.CustomObjects;

public class JarvisDetailedReport extends JarvisReport {

    /*
     *
     * Member Variables
     *
     */

    private String mStatus;



    /*
     *
     * Constructor
     *
     */

    public JarvisDetailedReport(ReportType type, String description, String date, String time, String reportedBy, String status) {

        super(type, description, date, time, reportedBy);
        mStatus = status;

    }



    /*
     *
     * Class Methods
     *
     */

    private String getStatus() {

        return mStatus;

    }

}
