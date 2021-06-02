//
//
// TOP Development
// SelectionType.java
//
//

package com.top.jarvised.Enums;

public enum SelectionType {

    Student {
        @Override
        public String toString() {
            return "Student";
        }
    },
    Teacher {
        @Override
        public String toString() {
            return "Teacher";
        }
    }

}
