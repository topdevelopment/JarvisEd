//
//
// TOP Development
// ScreensID.java
//
//

package com.top.jarvised.Enums;

public enum ScreensID {

    Login {
        @Override
        public String toString() {
            return "Login";
        }
    }, SignupStepOne {
        @Override
        public String toString() {
            return "SignUpStepOne";
        }
    }, SignupStepTwo {
        @Override
        public String toString() {
            return "SignUpStepTwo";
        }
    }, SignupStepThree {
        @Override
        public String toString() {
            return "SignUpStepThree";
        }
    }, Dashboard {
        @Override
        public String toString() {
            return "Dashboard";
        }
    }, StudentConfig {
        @Override
        public String toString() {
            return "StudentConfiguration";
        }
    }, TeacherConfig {
        @Override
        public String toString() {
            return "TeacherConfiguration";
        }
    }, StudentDetails {
        @Override
        public String toString() {
            return "Student Details";
        }
    }, TeacherDetails {
        @Override
        public String toString() {
            return "Teacher Details";
        }
    }, AddStudent {
        @Override
        public String toString() {
            return "Add Student";
        }
    }, AddTeacher {
        @Override
        public String toString() {
            return "Add Teacher";
        }
    }, EditStudent {
        @Override
        public String toString() {
            return "Edit Student";
        }
    }, EditTeacher {
        @Override
        public String toString() {
            return "Edit Teacher";
        }
    }, ClassConfig {
        @Override
        public String toString() {
            return "ClassConfiguration";
        }
    }, ClassAttachments {
        @Override
        public String toString() {
            return "ClassAttachments";
        }
    }

}
