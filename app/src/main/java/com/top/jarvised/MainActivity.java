package com.top.jarvised;

//
//
// TOP Development
// MainActivity.java
//
//

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.top.jarvised.Adapters.TeacherPagerAdapter;
import com.top.jarvised.Callbacks.AddStudentTeacherCallback;
import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.FirebaseCommCallback;
import com.top.jarvised.Callbacks.LoginCallback;
import com.top.jarvised.Callbacks.MACallback;
import com.top.jarvised.Callbacks.ReportCallback;
import com.top.jarvised.Callbacks.SignUpCallback;
import com.top.jarvised.Adapters.DrawerListAdapter;
import com.top.jarvised.CustomObjects.DrawerListContent;
import com.top.jarvised.CustomObjects.JarvisAdmin;
import com.top.jarvised.CustomObjects.JarvisParent;
import com.top.jarvised.CustomObjects.JarvisReport;
import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.CustomObjects.JarvisUser;
import com.top.jarvised.DialogFragments.AddAbsenceReportDialogFragment;
import com.top.jarvised.DialogFragments.AddClassDialogFragment;
import com.top.jarvised.DialogFragments.AddReportDialogFragment;
import com.top.jarvised.DialogFragments.AddSchoolDialogFragment;
import com.top.jarvised.DialogFragments.LoadingDialogFragment;
import com.top.jarvised.DialogFragments.SelectStudentDialogFragment;
import com.top.jarvised.DialogFragments.SelectTeacherDialogFragment;
import com.top.jarvised.Enums.DialogFragmentType;
import com.top.jarvised.Enums.ScreensID;
import com.top.jarvised.Fragments.AddStudentFragment;
import com.top.jarvised.Fragments.AddTeacherFragment;
import com.top.jarvised.Fragments.ClassConfigurationFragment;
import com.top.jarvised.Fragments.DashboardFragment;
import com.top.jarvised.Fragments.LoginFragment;
import com.top.jarvised.Fragments.SignUpStepOneFragment;
import com.top.jarvised.Fragments.SignUpStepThreeFragment;
import com.top.jarvised.Fragments.SignUpStepTwoFragment;
import com.top.jarvised.Fragments.StudentConfigurationFragment;
import com.top.jarvised.Fragments.StudentDetailsFragment;
import com.top.jarvised.Fragments.TeacherConfigurationFragment;
import com.top.jarvised.Fragments.TeacherDetailsFragment;
import com.top.jarvised.Services.FirebaseCommService;

import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements MACallback, LoginCallback, SignUpCallback, AddStudentTeacherCallback {

    /*
     *
     * Member Variables
     *
     */

    public static String LOG_TAG = "JARVIS_ED";
    private Bundle mSignUpAnswersBundle;
    private Toast mToast = null;
    private JarvisUser mCurrentUser = null;
    private DialogFragment mDialogFragment = null;
    private DrawerLayout mDrawerLayout = null;
    private boolean mIsDrawerOpen = false;



    /*
     *
     * Lifecycle Methods
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectScreen(ScreensID.Login);

        mDrawerLayout = findViewById(R.id.drawer_layout);

    }



    /*
     *
     * Class Methods
     *
     */

    @Override // MACallback Interface Method
    public Context getActivityContext() {

        return this;

    }

    @Override // MACallback Interface Method
    public void selectScreen(ScreensID screenId) {

        Fragment frag = null;

        if (screenId == ScreensID.Login) {

            frag = LoginFragment.newInstance();

        } else if (screenId == ScreensID.SignupStepOne) {

            mSignUpAnswersBundle = new Bundle();
            frag = SignUpStepOneFragment.newInstance();

        } else if (screenId == ScreensID.SignupStepTwo) {

            frag = SignUpStepTwoFragment.newInstance();

        } else if (screenId == ScreensID.SignupStepThree) {

            frag = SignUpStepThreeFragment.newInstance();

        } else if (screenId == ScreensID.Dashboard) {

            frag = DashboardFragment.newInstance();

        } else if (screenId == ScreensID.StudentConfig) {

            frag = StudentConfigurationFragment.newInstance();

        } else if (screenId == ScreensID.TeacherConfig) {

            frag = TeacherConfigurationFragment.newInstance();

        } else if (screenId == ScreensID.AddStudent) {

            frag = AddStudentFragment.newInstance();

        } else if (screenId == ScreensID.AddTeacher) {

            frag = AddTeacherFragment.newInstance();

        } else if (screenId == ScreensID.ClassConfig) {

            frag = ClassConfigurationFragment.newInstance();

        }

        if (frag != null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, frag)
                    .addToBackStack(null).commit();

        }

    }

    @Override // MACallback Interface Method
    public void postToastMessage(String message) {

        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();

    }

    @Override // MACallback Interface Method
    public FirebaseCommService getFirebaseCommService(FirebaseCommCallback callback) {

        return new FirebaseCommService(callback);

    }

    @Override // MACallback Interface Method
    public void startDialogFragment(DialogFragmentType type, DialogFragmentCallback dialogCallback) {

        if (type == DialogFragmentType.ADD_NEW_SCHOOL) {

            mDialogFragment = new AddSchoolDialogFragment(dialogCallback);
            mDialogFragment.show(getSupportFragmentManager(), "AddSchoolDialogFragment");

        } else if (type == DialogFragmentType.LOADING) {

            mDialogFragment = new LoadingDialogFragment();
            mDialogFragment.show(getSupportFragmentManager(), "LoadingDialogFragment");

        } else if (type == DialogFragmentType.ADD_NEW_CLASS) {

            mDialogFragment = new AddClassDialogFragment(dialogCallback);
            mDialogFragment.show(getSupportFragmentManager(), "AddClassDialogFragment");

        } else if (type == DialogFragmentType.ADD_NEW_REPORT) {

            mDialogFragment = new AddReportDialogFragment(dialogCallback);
            mDialogFragment.show(getSupportFragmentManager(), "AddAReportDialogFragment");

        } else if (type == DialogFragmentType.SELECT_USER) {

            if (mCurrentUser instanceof JarvisParent) {
                JarvisParent parent = (JarvisParent) mCurrentUser;
                mDialogFragment = new SelectStudentDialogFragment(dialogCallback, parent.getStudents());
            } else if (mCurrentUser instanceof JarvisTeacher) {
                JarvisTeacher teacher = (JarvisTeacher) mCurrentUser;
                mDialogFragment = new SelectStudentDialogFragment(dialogCallback, teacher.getStudents());
            } else if (mCurrentUser instanceof JarvisAdmin) {
                JarvisAdmin admin = (JarvisAdmin) mCurrentUser;
                mDialogFragment = new SelectTeacherDialogFragment(dialogCallback, admin.getTeachers());
            }
            mDialogFragment.show(getSupportFragmentManager(), "SelectUserDialogFragment");

        } else {

            postToastMessage(getResources().getString(R.string.toast_message_invalid_operation));

        }

        //todo: need dialog fragment for subject
        //todo: need dialog fragment for birthday

    }

    @Override // MACallback Interface Method
    public JarvisUser getCurrentUser() {

        return mCurrentUser;

    }

    @Override // MACallback Interface Method
    public void closeAllDialogs() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mDialogFragment != null && mDialogFragment.getDialog() != null && mDialogFragment.getDialog().isShowing()) {

                    Log.i(MainActivity.LOG_TAG, "Dismissing Dialog Fragment...");
                    mDialogFragment.dismiss();

                }

                if (mToast != null) {

                    Log.i(MainActivity.LOG_TAG, "Dismissing Toast Message...");
                    mToast.cancel();

                }

                hideKeyboard(MainActivity.this);

            }
        });

    }

    @Override // MACallback Interface Method
    public void toggleNavigationDrawer() {

        if (mIsDrawerOpen) {
            mDrawerLayout.closeDrawers();
            mIsDrawerOpen = false;
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
            mIsDrawerOpen = true;
        }

    }

    @Override // MACallback Interface Method
    public void showKeyboardForET(EditText et) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);

    }

    @Override // MACallback Interface Method
    public void startReportDialog(JarvisReport.ReportType type, ReportCallback callback) {

        if (type == JarvisReport.ReportType.ATTENDENCE) {

            mDialogFragment = AddAbsenceReportDialogFragment.newInstance(callback);
            mDialogFragment.show(getSupportFragmentManager(), "AddAbsenceDialogFragment");

        } else if (type == JarvisReport.ReportType.SIP) {



        } else if (type == JarvisReport.ReportType.CONFLICT) {



        } else if (type == JarvisReport.ReportType.SECLUDED) {



        } else if (type == JarvisReport.ReportType.EXPELLED) {



        } else {

            postToastMessage(getResources().getString(R.string.toast_message_invalid_operation));

        }

    }

    @Override // MACallback Interface Method
    public void forceRunOnUIThread(Runnable runnable) {

        runOnUiThread(runnable);

    }

    @Override // SignUpCallback Interface Method
    public void signupAnswer(ScreensID screenID, String answer) {

        mSignUpAnswersBundle.putString(screenID.toString(), answer);

        if (screenID == ScreensID.SignupStepOne) {

            if (answer.matches("Student") ||
                    answer.matches("Parent") ||
                    answer.matches("Teacher") ||
                    answer.matches("Administrator")) {

                mSignUpAnswersBundle.putString(ScreensID.SignupStepOne.toString(), answer);
                selectScreen(ScreensID.SignupStepTwo);

            } else if (answer.matches("Just Curious")) {

                //todo: move user to dashboard and mock tutorial
                //todo: requires a method to instantiate dashboard with knowledge that it ends session after
                //todo: the user gets the starting tutorial

            } else {

                mSignUpAnswersBundle.putString(ScreensID.SignupStepOne.toString(), null);
                postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

            }

        } else if (screenID == ScreensID.SignupStepTwo) {

            if (answer != null && !answer.isEmpty()) {

                mSignUpAnswersBundle.putString(ScreensID.SignupStepTwo.toString(), answer);
                selectScreen(ScreensID.SignupStepThree);

            } else {

                mSignUpAnswersBundle.putString(ScreensID.SignupStepTwo.toString(), null);
                postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

            }

        } else {

            closeAllDialogs();
            postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // SignUpCallback Interface Method
    public void cancelSignup() {

        mSignUpAnswersBundle.clear();
        selectScreen(ScreensID.Login);

    }

    @Override // SignUpCallback Interface Method
    public String getSignupAnswer(ScreensID screenID) {

        if (mSignUpAnswersBundle.containsKey(screenID.toString())) {

            return mSignUpAnswersBundle.getString(screenID.toString());

        } else {

            return "";

        }

    }

    @Override // AddStudentTeacherCallback Interface Method
    public void viewStudentDetails(JarvisStudent student) {

        if (student == null) return;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, StudentDetailsFragment.newInstance(student))
                .addToBackStack(null).commit();

    }

    @Override // AddStudentTeacherCallback Interface Method
    public void viewTeacherDetails(JarvisTeacher teacher) {

        if (teacher == null) return;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, TeacherDetailsFragment.newInstance(teacher))
                .addToBackStack(null).commit();

    }

    @Override // AddStudentTeacherCallback Interface Method
    public void editStudentDetails(JarvisStudent student) {



    }

    @Override // AddStudentTeacherCallback Interface Method
    public void editTeacherDetails(JarvisTeacher teacher) {



    }

    @Override // AddStudentTeacherCallback Interface Method
    public boolean updateStudentList(List<JarvisStudent> students) {

        if (mCurrentUser instanceof JarvisParent) {

            JarvisParent parent = (JarvisParent) mCurrentUser;
            parent.setStudents(students);

        } else if (mCurrentUser instanceof JarvisTeacher) {

            JarvisTeacher teacher = (JarvisTeacher) mCurrentUser;
            teacher.setStudents(students);

        } else if (mCurrentUser instanceof JarvisAdmin) {

            JarvisAdmin admin = (JarvisAdmin) mCurrentUser;
            admin.setStudents(students);

        }

        return true;

    }

    @Override // AddStudentTeacherCallback Interface Method
    public boolean updateTeacherList(List<JarvisTeacher> teachers) {

        if (mCurrentUser instanceof JarvisParent || mCurrentUser instanceof JarvisTeacher) {

            return false;

        } else if (mCurrentUser instanceof JarvisAdmin) {

            JarvisAdmin admin = (JarvisAdmin) mCurrentUser;
            admin.setTeachers(teachers);

        }

        return true;

    }

    @Override // LoginCallback Interface Method
    public void setCurrentUser(JarvisUser user) {

        mCurrentUser = user;
        selectScreen(ScreensID.Dashboard);
        ListView drawerList = findViewById(R.id.left_drawer);
        final DrawerListContent content;
        if (user instanceof JarvisStudent) {
            content = new DrawerListContent(this, true, false, false);
        } else if (user instanceof JarvisTeacher) {
            content = new DrawerListContent(this, true, true, false);
        } else if (user instanceof JarvisParent) {
            content = new DrawerListContent(this, true, true, false);
        } else if (user instanceof JarvisAdmin) {
            content = new DrawerListContent(this, true, true, true);
        } else {
            content = new DrawerListContent(this, false, false, false);
        }

        drawerList.setAdapter(new DrawerListAdapter(this, R.layout.menu_drawer_item, content.getItems()));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toggleNavigationDrawer();
                selectScreen(content.getItems().get(position).getScreenId());
            }
        });

    }

    public static void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}
