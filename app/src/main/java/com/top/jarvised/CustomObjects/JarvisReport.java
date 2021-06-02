//
//
// TOP Development
// JarvisReport.java
//
//

package com.top.jarvised.CustomObjects;

public class JarvisReport {

    /*
     *
     * Member Variables
     *
     */

    private String mReportDate;
    private String mReportTime;
    private ReportType mReportType;
    private String mReportDescription;
    private String mReportedBy;



    /*
     *
     * Custom Enum: ReportType
     *
     */

    public enum ReportType {

        ATTENDENCE {
            @Override
            public String toString() {
                return "Attendence";
            }
        },
        SIP {
            @Override
            public String toString() {
                return "Situation in Progress";
            }
        },
        CONFLICT {
            @Override
            public String toString() {
                return "Conflict";
            }
        },
        SECLUDED {
            @Override
            public String toString() {
                return "Secluded";
            }
        },
        EXPELLED {
            @Override
            public String toString() {
                return "Expelled";
            }
        }

    }



    /*
     *
     * Constructor
     *
     */

    public JarvisReport(ReportType type, String description, String date, String time, String reportedBy) {

        mReportType = type;
        mReportDescription = description;
        mReportDate = date;
        mReportTime = time;
        mReportedBy = reportedBy;

    }



    /*
     *
     * Class Methods
     *
     */

    public ReportType getReportType() {

        return mReportType;

    }

    public String getReportDescription() {

        return mReportDescription;

    }

    public String getReportDate() {

        return mReportDate;

    }

    public String getReportTime() {

        return mReportTime;

    }

    public String getWhoReported() {

        return mReportedBy;

    }

}
