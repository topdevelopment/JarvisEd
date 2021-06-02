//
//
// TOP Development
// AddStudentTeacherCallback.java
//
//

package com.top.jarvised.Callbacks;

import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.CustomObjects.JarvisTeacher;

import java.util.List;

public interface AddStudentTeacherCallback {

    void viewStudentDetails(JarvisStudent student);
    void viewTeacherDetails(JarvisTeacher teacher);
    void editStudentDetails(JarvisStudent student);
    void editTeacherDetails(JarvisTeacher teacher);
    boolean updateStudentList(List<JarvisStudent> students);
    boolean updateTeacherList(List<JarvisTeacher> teachers);

}
