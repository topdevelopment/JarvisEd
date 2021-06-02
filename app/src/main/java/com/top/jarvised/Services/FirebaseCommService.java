//
//
// TOP Development
// FirebaseCommService.java
//
//

package com.top.jarvised.Services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.top.jarvised.Callbacks.FirebaseCommCallback;
import com.top.jarvised.CustomObjects.JarvisAdmin;
import com.top.jarvised.CustomObjects.JarvisAttendenceReport;
import com.top.jarvised.CustomObjects.JarvisClass;
import com.top.jarvised.CustomObjects.JarvisDetailedReport;
import com.top.jarvised.CustomObjects.JarvisParent;
import com.top.jarvised.CustomObjects.JarvisReport;
import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.CustomObjects.JarvisUser;
import com.top.jarvised.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseCommService {

    /*
     *
     * Member Variables
     *
     */

    private FirebaseCommCallback mCallback;



    /*
     *
     * Database Identifiers
     *
     */

    private static String USERS_IDENTIFIER = "Users";
    private static String STUDENTS_IDENTIFIER = "Students";
    private static String REPORTS_IDENTIFIER = "Reports";
    private static String SCHOOLS_IDENTIFIER = "Schools";
    private static String CLASSES_IDENTIFIER = "Classes";



    /*
     *
     * Database Field Identifiers
     *
     */

    private static String BIRTHDAY_ID = "birthday";
    private static String CONTACT_EMAIL_ID = "contactEmail";
    private static String CONTACT_NAME_ID = "contactFullName";
    private static String FULL_NAME_ID = "fullName";
    private static String PARENT_USER_KEY_ID = "parentUserKey";
    private static String SCHOOL_NAME_ID = "schoolName";
    private static String USER_TYPE_ID = "userType";
    private static String HAS_CONDITION_ID = "hasCondition";
    private static String STUDENTS_ID = "students";
    private static String TEACHERS_ID = "teachers";
    private static String SUBJECT_ID = "subject";
    private static String FLAG_TYPE_ID = "flagType";
    private static String DATE_ID = "date";
    private static String TIME_ID = "time";
    private static String DESCRIPTION_ID = "description";
    private static String STATUS_ID = "status";
    private static String REPORTED_BY_ID = "reportedBy";
    private static String WAS_PRESENT_ID = "wasPresent";
    private static String CLASS_TITLE_ID = "classTitle";
    private static String TEACHER_NAME_ID = "teacherName";
    private static String STUDENT_NAMES_ID = "studentNames";



    /*
     *
     * Local Enum
     *
     */

    public enum FirebaseMethod {

        LOGIN {
            @Override
            public String toString() {
                return "Login";
            }
        },
        DELETE_CLASS {
            @Override
            public String toString() {
                return "Delete Class";
            }
        },
        SIGNUP {
            @Override
            public String toString() {
                return "Signup";
            }
        },
        GET_USER_INFO {
            @Override
            public String toString() {
                return "Get User Info";
            }
        },
        GET_ALL_SCHOOLS {
            @Override
            public String toString() {
                return "Get All Schools";
            }
        },
        GET_ALL_TEACHERS {
            @Override
            public String toString() {
                return "Get All Teachers";
            }
        },
        GET_ALL_STUDENTS {
            @Override
            public String toString() {
                return "Get All Students";
            }
        },
        ADD_NEW_SCHOOL {
            @Override
            public String toString() {
                return "Add New School";
            }
        },
        ADD_NEW_STUDENT {
            @Override
            public String toString() {
                return "Add New Student";
            }
        },
        CREATE_ACCOUNT {
            @Override
            public String toString() {
                return "Create Account";
            }
        },
        GET_STUDENTS_REPORTS {
            @Override
            public String toString() {
                return "Get Students Reports";
            }
        },
        ADD_NEW_TEACHER {
            @Override
            public String toString() {
                return "Add New Teacher";
            }
        },
        GET_ALL_CLASSES {
            @Override
            public String toString() {
                return "Get All Classes";
            }
        },
        ADD_NEW_CLASS {
            @Override
            public String toString() {
                return "Add New Class";
            }
        }

    }



    /*
     *
     * Constructor
     *
     */

    public FirebaseCommService(FirebaseCommCallback callback) {

        mCallback = callback;

    }



    /*
     *
     * Productive Methods
     *
     */

    public void attemptLogin(final String email, final String password) {

        Log.i(MainActivity.LOG_TAG, "Login Attempt | Email: " + email + " | Password: " + password);
        new Thread(new Runnable() {
            @Override
            public void run() {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Log.i(MainActivity.LOG_TAG, "Login Attempt Successful");
                            getUserInfo(email);

                        } else {

                            Log.i(MainActivity.LOG_TAG, "Login Attempt Failed");
                            mCallback.errorResponse(FirebaseMethod.LOGIN);

                        }

                    }
                });

            }
        }).start();

    }

    public void deleteClass(final String email, final String className) {

        Log.i(MainActivity.LOG_TAG, "Delete Class: " + className + " started!");
        new Thread(new Runnable() {
            @Override
            public void run() {

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                database.collection(USERS_IDENTIFIER).document(email).collection(CLASSES_IDENTIFIER).document(className).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    mCallback.successResponse(FirebaseMethod.DELETE_CLASS);

                                } else {

                                    mCallback.errorResponse(FirebaseMethod.DELETE_CLASS);

                                }

                            }
                        });

            }
        }).start();

    }



    /*
     *
     * Getter Methods
     *
     */

    public void getUserInfo(final String email) {

        Log.i(MainActivity.LOG_TAG, "Get User Info Attempt | Email: " + email);
        new Thread(new Runnable() {
            @Override
            public void run() {

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                database.collection(USERS_IDENTIFIER).document(email).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()) {

                                    Log.i(MainActivity.LOG_TAG, "Get User Info Successful");
                                    DocumentSnapshot snapshot = task.getResult();
                                    String fullname = snapshot.getString(FULL_NAME_ID);
                                    String userType = snapshot.getString(USER_TYPE_ID);

                                    Log.i(MainActivity.LOG_TAG, "User Type: " + userType);

                                    JarvisUser user = null;
                                    if (userType.matches(JarvisUser.UserType.STUDENT.toString())) {

                                        String birthday = snapshot.getString(BIRTHDAY_ID);
                                        boolean hasCondition = snapshot.getBoolean(HAS_CONDITION_ID);
                                        user = new JarvisStudent(fullname, birthday, email, hasCondition);

                                        Log.i(MainActivity.LOG_TAG, "Student Data Retrieved | fullName: " + fullname
                                                + " | birthday: " + birthday + " | email: " + email + " | hasCondition: "
                                                + String.valueOf(hasCondition));

                                    } else if (userType.matches(JarvisUser.UserType.ADMINISTRATOR.toString())) {

                                        List<String> teachers;
                                        if (snapshot.contains(TEACHERS_ID)) {
                                            teachers = (List<String>) snapshot.get(TEACHERS_ID);
                                        } else {
                                            teachers = new ArrayList<>();
                                        }

                                        List<String> students;
                                        if (snapshot.contains(STUDENTS_ID)) {
                                            students = (List<String>) snapshot.get(STUDENTS_ID);
                                        } else {
                                            students = new ArrayList<>();
                                        }

                                        user = new JarvisAdmin(fullname, email, teachers, students);

                                        Log.i(MainActivity.LOG_TAG, "Administrator Data Retrieved | fullName: " + fullname
                                                + " | email: " + email);

                                    } else if (userType.matches(JarvisUser.UserType.PARENT.toString())) {

                                        List<String> students;
                                        if (snapshot.contains(STUDENTS_ID)) {
                                            students = (List<String>) snapshot.get(STUDENTS_ID);
                                        } else {
                                            students = new ArrayList<>();
                                        }
                                        user = new JarvisParent(fullname, email, students);

                                        Log.i(MainActivity.LOG_TAG, "Parent Data Retrieved | fullName: " + fullname
                                                + " | email: " + email);

                                    } else if (userType.matches(JarvisUser.UserType.TEACHER.toString())) {

                                        String subject = snapshot.getString(SUBJECT_ID);
                                        String birthday = snapshot.getString(BIRTHDAY_ID);
                                        List<String> students;
                                        if (snapshot.contains(STUDENTS_ID)) {
                                            students = (List<String>) snapshot.get(STUDENTS_ID);
                                        } else {
                                            students = new ArrayList<>();
                                        }
                                        user = new JarvisTeacher(fullname, birthday, email, subject, students);

                                        Log.i(MainActivity.LOG_TAG, "Teacher Data Retrieved | fullName: " + fullname
                                                + " | birthday: " + birthday + " | email: " + email + " | subject: " + subject);

                                    }

                                    if (user != null) {

                                        Log.i(MainActivity.LOG_TAG, "user != null | Sending Success Response...");
                                        mCallback.successResponseSingle(FirebaseMethod.GET_USER_INFO, user);

                                    } else {

                                        Log.i(MainActivity.LOG_TAG, "user == null | Sending Failure Response...");
                                        mCallback.errorResponse(FirebaseMethod.GET_USER_INFO);

                                    }

                                } else {

                                    Log.i(MainActivity.LOG_TAG, "Get User Info Failed");
                                    mCallback.errorResponse(FirebaseMethod.GET_USER_INFO);

                                }

                            }

                        });

            }

        }).start();

    }

    public void getAllSchools() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                database.collection(SCHOOLS_IDENTIFIER).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {

                                    if (task.getResult() != null) {

                                        ArrayList<String> schoolList = new ArrayList<>();
                                        for (QueryDocumentSnapshot snapshot : task.getResult()) {

                                            if (snapshot.get(SCHOOL_NAME_ID) instanceof String) {

                                                schoolList.add(String.valueOf(snapshot.get(SCHOOL_NAME_ID)));

                                            }

                                        }

                                        mCallback.successResponseList(FirebaseMethod.GET_ALL_SCHOOLS, schoolList);

                                    } else {

                                        mCallback.successResponseList(FirebaseMethod.GET_ALL_SCHOOLS, new ArrayList<Object>());

                                    }

                                } else {

                                    mCallback.errorResponse(FirebaseMethod.GET_ALL_SCHOOLS);

                                }

                            }
                        });

            }
        }).start();

    }

    public void getAllTeachers(final List<String> teacherEmailAddresses) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                for (int i = 0; i < teacherEmailAddresses.size(); i++) {

                    final String email = teacherEmailAddresses.get(i);
                    database.collection(USERS_IDENTIFIER).document(teacherEmailAddresses.get(i)).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    if (task.isSuccessful()) {

                                        DocumentSnapshot snapshot = task.getResult();
                                        String fullname = snapshot.getString(FULL_NAME_ID);
                                        String userType = snapshot.getString(USER_TYPE_ID);

                                        if (userType.matches(JarvisUser.UserType.TEACHER.toString())) {

                                            String subject = snapshot.getString(SUBJECT_ID);
                                            String birthday = snapshot.getString(BIRTHDAY_ID);
                                            List<String> students;
                                            if (snapshot.contains(STUDENTS_ID)) {
                                                students = (List<String>) snapshot.get(STUDENTS_ID);
                                            } else {
                                                students = new ArrayList<>();
                                            }
                                            JarvisTeacher user = new JarvisTeacher(fullname, birthday, email, subject, students);

                                            Log.i(MainActivity.LOG_TAG, "Teacher Data Retrieved | fullName: " + fullname
                                                    + " | birthday: " + birthday + " | email: " + email + " | subject: " + subject);

                                            mCallback.successResponseSingle(FirebaseMethod.GET_ALL_TEACHERS, user);

                                        }

                                    } else {

                                        mCallback.errorResponse(FirebaseMethod.GET_ALL_TEACHERS);

                                    }

                                }
                            });

                }

                mCallback.successResponse(FirebaseMethod.GET_ALL_TEACHERS);

            }
        }).start();

    }

    public void getAllStudents(final List<String> studentEmailAddresses) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                for (int i = 0; i < studentEmailAddresses.size(); i++) {

                    final String email = studentEmailAddresses.get(i);
                    database.collection(USERS_IDENTIFIER).document(studentEmailAddresses.get(i)).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    if (task.isSuccessful()) {

                                        DocumentSnapshot snapshot = task.getResult();
                                        String fullname = snapshot.getString(FULL_NAME_ID);
                                        String userType = snapshot.getString(USER_TYPE_ID);

                                        if (userType.matches(JarvisUser.UserType.STUDENT.toString())) {

                                            String birthday = snapshot.getString(BIRTHDAY_ID);
                                            boolean hasCondition = snapshot.getBoolean(HAS_CONDITION_ID);
                                            JarvisStudent user = new JarvisStudent(fullname, birthday, email, hasCondition);

                                            Log.i(MainActivity.LOG_TAG, "Student Data Retrieved | fullName: " + fullname
                                                    + " | birthday: " + birthday + " | email: " + email + " | hasCondition: "
                                                    + String.valueOf(hasCondition));

                                            mCallback.successResponseSingle(FirebaseMethod.GET_ALL_STUDENTS, user);

                                        }

                                    } else {

                                        mCallback.errorResponse(FirebaseMethod.GET_ALL_STUDENTS);

                                    }

                                }
                            });

                }

                mCallback.successResponse(FirebaseMethod.GET_ALL_STUDENTS);

            }
        }).start();

    }

    public void getStudentsReports(final String studentEmail) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                final FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                database.collection(USERS_IDENTIFIER).document(studentEmail).collection(REPORTS_IDENTIFIER).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {

                                    if (task.getResult() != null) {

                                        ArrayList<JarvisReport> reports = new ArrayList<>();
                                        for (int i = 0; i < task.getResult().getDocuments().size(); i++) {

                                            DocumentSnapshot snapshot = task.getResult().getDocuments().get(i);
                                            String flagType = "";
                                            String description = "";
                                            String date = "";
                                            String time = "";
                                            String subject = "";
                                            String reportedBy = "";
                                            boolean wasPresent = true;
                                            JarvisReport.ReportType type = null;
                                            if (snapshot.contains(FLAG_TYPE_ID)) flagType = snapshot.getString(FLAG_TYPE_ID);

                                            if (flagType.matches(JarvisReport.ReportType.ATTENDENCE.toString())) {

                                                type = JarvisReport.ReportType.ATTENDENCE;

                                                if (snapshot.contains(DESCRIPTION_ID)) {
                                                    description = snapshot.getString(DESCRIPTION_ID);
                                                }
                                                if (snapshot.contains(DATE_ID)) {
                                                    date = snapshot.getString(DATE_ID);
                                                }
                                                if (snapshot.contains(TIME_ID)) {
                                                    time = snapshot.getString(TIME_ID);
                                                }
                                                if (snapshot.contains(REPORTED_BY_ID)) {
                                                    reportedBy = snapshot.getString(REPORTED_BY_ID);
                                                }
                                                if (snapshot.contains(SUBJECT_ID)) {
                                                    subject = snapshot.getString(SUBJECT_ID);
                                                }
                                                if (snapshot.contains(WAS_PRESENT_ID)) {
                                                    wasPresent = snapshot.getBoolean(WAS_PRESENT_ID);
                                                }

                                                JarvisAttendenceReport report = new JarvisAttendenceReport(type, description, date, time, reportedBy, subject, wasPresent);
                                                reports.add(report);

                                            } else if (flagType.matches(JarvisReport.ReportType.CONFLICT.toString())
                                                    || flagType.matches(JarvisReport.ReportType.EXPELLED.toString())
                                                    || flagType.matches(JarvisReport.ReportType.SECLUDED.toString())
                                                    || flagType.matches(JarvisReport.ReportType.SIP.toString())) {

                                                if (flagType.matches(JarvisReport.ReportType.CONFLICT.toString())) {
                                                    type = JarvisReport.ReportType.CONFLICT;
                                                } else if (flagType.matches(JarvisReport.ReportType.EXPELLED.toString())) {
                                                    type = JarvisReport.ReportType.EXPELLED;
                                                } else if (flagType.matches(JarvisReport.ReportType.SECLUDED.toString())) {
                                                    type = JarvisReport.ReportType.SECLUDED;
                                                } else if (flagType.matches(JarvisReport.ReportType.SIP.toString())) {
                                                    type = JarvisReport.ReportType.SIP;
                                                }

                                                if (snapshot.contains(DESCRIPTION_ID)) {
                                                    description = snapshot.getString(DESCRIPTION_ID);
                                                }
                                                if (snapshot.contains(DATE_ID)) {
                                                    date = snapshot.getString(DATE_ID);
                                                }
                                                if (snapshot.contains(TIME_ID)) {
                                                    time = snapshot.getString(TIME_ID);
                                                }
                                                if (snapshot.contains(REPORTED_BY_ID)) {
                                                    reportedBy = snapshot.getString(REPORTED_BY_ID);
                                                }
                                                String status = "";
                                                if (snapshot.contains(STATUS_ID)) {
                                                    status = snapshot.getString(STATUS_ID);
                                                }

                                                JarvisDetailedReport report = new JarvisDetailedReport(type, description, date, time, reportedBy, status);
                                                reports.add(report);

                                            }

                                        }

                                        mCallback.successResponseList(FirebaseMethod.GET_STUDENTS_REPORTS, reports);

                                    }

                                } else {

                                    mCallback.errorResponse(FirebaseMethod.GET_STUDENTS_REPORTS);

                                }

                            }
                        });

            }
        }).start();

    }

    public void getAllClasses(final String email) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                database.collection(USERS_IDENTIFIER).document(email).collection(CLASSES_IDENTIFIER).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {

                                    if (task.getResult() != null) {

                                        for (int i = 0; i < task.getResult().getDocuments().size(); i++) {

                                            DocumentSnapshot snapshot = task.getResult().getDocuments().get(i);
                                            String classTitle = "";
                                            String teacherName = "";
                                            List<String> studentNames;

                                            if (snapshot.contains(CLASS_TITLE_ID)) {
                                                classTitle = snapshot.getString(CLASS_TITLE_ID);
                                            }

                                            if (snapshot.contains(TEACHER_NAME_ID)) {
                                                teacherName = snapshot.getString(TEACHER_NAME_ID);
                                            }

                                            if (snapshot.contains(STUDENT_NAMES_ID)) {
                                                studentNames = (List<String>) snapshot.get(STUDENT_NAMES_ID);
                                            } else {
                                                studentNames = new ArrayList<>();
                                            }

                                            JarvisClass jClass = new JarvisClass(classTitle, teacherName, studentNames);

                                            mCallback.successResponseSingle(FirebaseMethod.GET_ALL_CLASSES, jClass);

                                        }

                                        mCallback.successResponse(FirebaseMethod.GET_ALL_CLASSES);

                                    }

                                } else {

                                    mCallback.errorResponse(FirebaseMethod.GET_ALL_CLASSES);

                                }

                            }
                        });

            }
        }).start();

    }



    /*
     *
     * Setter Methods
     *
     */

    public void createAccount(final String email, final String password, final String role,
                              final String schoolName, final String fullname) {

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        new Thread(new Runnable() {
            @Override
            public void run() {

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    mCallback.successResponse(FirebaseMethod.CREATE_ACCOUNT);

                                } else {

                                    mCallback.errorResponse(FirebaseMethod.CREATE_ACCOUNT);
                                    if (task.getException() != null) {
                                        Log.i(MainActivity.LOG_TAG, task.getException().getLocalizedMessage());
                                    }

                                }

                            }
                        });

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Object> newUserData = new HashMap<>();
                newUserData.put(USER_TYPE_ID, role);
                newUserData.put(FULL_NAME_ID, fullname);
                newUserData.put(SCHOOL_NAME_ID, schoolName);

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                database.collection(USERS_IDENTIFIER).document(email).set(newUserData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    mCallback.successResponse(FirebaseMethod.CREATE_ACCOUNT);

                                } else {

                                    mCallback.errorResponse(FirebaseMethod.CREATE_ACCOUNT);

                                }


                            }
                        });

            }
        }).start();

    }

    public void addNewStudent(final String currentUserEmail, final List<String> currentStudentsList, final String studentFullName, final String contactEmail, final String studentBirthday, final boolean hasCondition) {

        Log.i(MainActivity.LOG_TAG, "Add New Student Attempt | Student Full Name: " + studentFullName +
                " | Contact Email: " + contactEmail + " | Student Birthday: " + studentBirthday + " | hasCondition: " + hasCondition);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Object> newStudentData = new HashMap<>();
                newStudentData.put(FULL_NAME_ID, studentFullName);
                newStudentData.put(BIRTHDAY_ID, studentBirthday);
                newStudentData.put(HAS_CONDITION_ID, hasCondition);
                newStudentData.put(USER_TYPE_ID, "Student");

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                database.collection(USERS_IDENTIFIER).document(contactEmail).set(newStudentData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    Log.i(MainActivity.LOG_TAG, "Add New Student Successful");
                                    mCallback.successResponse(FirebaseMethod.ADD_NEW_STUDENT);

                                } else {

                                    Log.i(MainActivity.LOG_TAG, "Add New Student Failed");
                                    mCallback.errorResponse(FirebaseMethod.ADD_NEW_STUDENT);

                                }

                            }
                        });

                Map<String, Object> updateUserData = new HashMap<>();
                currentStudentsList.add(contactEmail);
                updateUserData.put(STUDENTS_ID, currentStudentsList);

                database.collection(USERS_IDENTIFIER).document(currentUserEmail).set(updateUserData, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    mCallback.successResponse(FirebaseMethod.ADD_NEW_STUDENT);

                                } else {

                                    mCallback.errorResponse(FirebaseMethod.ADD_NEW_STUDENT);

                                }

                            }
                        });

            }
        }).start();

    }

    public void addNewTeacher(final String currentUserEmail, final List<String> currentTeachersList, final String teacherFullName, final String contactEmail, final String subject) {

        Log.i(MainActivity.LOG_TAG, "Add New Teacher Attempt | Teacher Full Name: " + teacherFullName +
                " | Contact Email: " + contactEmail + " | Subject: " + subject);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Object> newTeacherData = new HashMap<>();
                newTeacherData.put(FULL_NAME_ID, teacherFullName);
                newTeacherData.put(USER_TYPE_ID, "Teacher");

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                database.collection(USERS_IDENTIFIER).document(contactEmail).set(newTeacherData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    Log.i(MainActivity.LOG_TAG, "Add New Student Successful");
                                    mCallback.successResponse(FirebaseMethod.ADD_NEW_TEACHER);

                                } else {

                                    Log.i(MainActivity.LOG_TAG, "Add New Student Failed");
                                    mCallback.errorResponse(FirebaseMethod.ADD_NEW_TEACHER);

                                }

                            }
                        });

                Map<String, Object> updateUserData = new HashMap<>();
                currentTeachersList.add(contactEmail);
                updateUserData.put(STUDENTS_ID, currentTeachersList);

                database.collection(USERS_IDENTIFIER).document(currentUserEmail).set(updateUserData, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    mCallback.successResponse(FirebaseMethod.ADD_NEW_TEACHER);

                                } else {

                                    mCallback.errorResponse(FirebaseMethod.ADD_NEW_TEACHER);

                                }

                            }
                        });

            }
        }).start();

    }

    public void addNewClass(final String email, final String className) {

        Log.i(MainActivity.LOG_TAG, "Add New Class Attempt | Class Name: " + className);
        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Object> newClassData = new HashMap<>();
                newClassData.put(CLASS_TITLE_ID, className);

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                database.collection(USERS_IDENTIFIER).document(email).collection(CLASSES_IDENTIFIER).document(className).set(newClassData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    mCallback.successResponse(FirebaseMethod.ADD_NEW_CLASS);

                                } else {

                                    mCallback.errorResponse(FirebaseMethod.ADD_NEW_CLASS);

                                }

                            }
                        });

            }
        }).start();

    }

    public void addNewSchool(final String schoolName) {

        Log.i(MainActivity.LOG_TAG, "Add New School Attempt | School Name: " + schoolName);
        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Object> newStudentData = new HashMap<>();
                newStudentData.put(SCHOOL_NAME_ID, schoolName);

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                database.setFirestoreSettings(settings);

                database.collection(SCHOOLS_IDENTIFIER).add(newStudentData)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                if (task.isSuccessful()) {

                                    Log.i(MainActivity.LOG_TAG, "Add New School Successful");
                                    mCallback.successResponse(FirebaseMethod.ADD_NEW_SCHOOL);

                                } else {

                                    Log.i(MainActivity.LOG_TAG, "Add New School Failed");
                                    mCallback.errorResponse(FirebaseMethod.ADD_NEW_SCHOOL);

                                }

                            }
                        });

            }
        }).start();

    }

}
