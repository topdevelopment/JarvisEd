//
//
// TOP Development
// JarvisUser.java
//
//

package com.top.jarvised.CustomObjects;

public class JarvisUser {

    /*
     *
     * Member Variables
     *
     */

    private String mContactEmail;
    private String mFullName;



    /*
     *
     * Public Enum
     *
     */

    public enum UserType {

        STUDENT {
            @Override
            public String toString() {
                return "Student";
            }
        },
        TEACHER {
            @Override
            public String toString() {
                return "Teacher";
            }
        },
        ADMINISTRATOR {
            @Override
            public String toString() {
                return "Administrator";
            }
        },
        PARENT {
            @Override
            public String toString() {
                return "Parent";
            }
        }

    }



    /*
     *
     * Constructor
     *
     */

    public JarvisUser() {

        mContactEmail = "No Email";
        mFullName = "No Name";

    }

    public JarvisUser(String contactEmail, String fullname) {

        mContactEmail = contactEmail;
        mFullName = fullname;

    }



    /*
     *
     * Class Methods
     *
     */

    public String getContactEmail() {

        return mContactEmail;

    }

    public String getFullName() {

        return mFullName;

    }

}
