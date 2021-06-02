//
//
// TOP Development
// FirebaseCommType.java
//
//

package com.top.jarvised.Enums;

public enum FirebaseCommType {

    ADD_NEW_STUDENT {
        @Override
        public String toString() {
            return "Add New Student";
        }
    }, ADD_NEW_TEACHER {
        @Override
        public String toString() {
            return "Add New Teacher";
        }
    }

}
