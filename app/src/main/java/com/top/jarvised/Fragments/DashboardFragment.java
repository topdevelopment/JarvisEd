//
//
// TOP Development
// DashboardFragment.java
//
//

package com.top.jarvised.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.top.jarvised.Adapters.TeacherListAdapter;
import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.FirebaseCommCallback;
import com.top.jarvised.Callbacks.ReportCallback;
import com.top.jarvised.CustomObjects.JarvisAdmin;
import com.top.jarvised.CustomObjects.JarvisParent;
import com.top.jarvised.CustomObjects.JarvisReport;
import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.CustomObjects.JarvisUser;
import com.top.jarvised.Adapters.StudentPagerAdapter;
import com.top.jarvised.Adapters.TeacherPagerAdapter;
import com.top.jarvised.CustomObjects.ZoomOutPageTransformer;
import com.top.jarvised.Enums.DialogFragmentType;
import com.top.jarvised.MainActivity;
import com.top.jarvised.R;
import com.top.jarvised.Services.FirebaseCommService;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment
        extends BaseFragment
        implements DialogFragmentCallback, FirebaseCommCallback, ReportCallback {

    /*
     *
     * Member Variables
     *
     */

    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private List<JarvisStudent> mTempStudentList = new ArrayList<>();
    private List<JarvisTeacher> mTempTeacherList = new ArrayList<>();



    /*
     *
     * Constructor
     *
     */

    public static DashboardFragment newInstance() {

        Bundle args = new Bundle();

        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }



    /*
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        JarvisUser user = mMACallback.getCurrentUser();
        final DashboardFragment frag = this;
        if (user instanceof JarvisStudent) {

            //todo: determine what to pull as a student

        } else if (user instanceof JarvisTeacher) {

            JarvisTeacher teacher = (JarvisTeacher) user;
            if (teacher.getStudentsList().size() == 0) {
                mMACallback.startDialogFragment(DialogFragmentType.LOADING, this);
                mMACallback.getFirebaseCommService(this).getAllStudents(teacher.getStudentsList());
            }

        } else if (user instanceof JarvisParent) {

            JarvisParent parent = (JarvisParent) user;
            if (parent.getStudentsList().size() == 0) {
                mMACallback.startDialogFragment(DialogFragmentType.LOADING, this);
                mMACallback.getFirebaseCommService(this).getAllStudents(parent.getStudentsList());
            }

        } else if (user instanceof JarvisAdmin) {

            JarvisAdmin admin = (JarvisAdmin) user;
            Log.i(MainActivity.LOG_TAG, "number of teachers: " + admin.getTeachers().size());
            if (admin.getTeachers().size() == 0) {

                //mMACallback.startDialogFragment(DialogFragmentType.LOADING, frag);
                Log.i(MainActivity.LOG_TAG, "Start Get All Teachers");
                mMACallback.getFirebaseCommService(this).getAllTeachers(admin.getTeachersList());

            } else {

                mTempTeacherList = admin.getTeachers();
                pagerAdapter = new TeacherPagerAdapter(mTempTeacherList, getFragmentManager());
                mPager.setAdapter(pagerAdapter);

            }

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //todo: switch the dashboard layout depending upon the type of user

        View layoutView = inflater.inflate(R.layout.fragment_dashboard_admin, container, false);
        mPager = layoutView.findViewById(R.id.pager_dashboard);
        final DashboardFragment frag = this;
        setHasOptionsMenu(true);

        Button b = layoutView.findViewById(R.id.button_select);
        JarvisUser user = mMACallback.getCurrentUser();
        if (user instanceof JarvisStudent) {

            JarvisStudent student = (JarvisStudent) user;

        } else if (user instanceof JarvisTeacher) {

            JarvisTeacher teacher = (JarvisTeacher) user;
            b.setText(R.string.button_select_student);

        } else if (user instanceof JarvisParent) {

            JarvisParent parent = (JarvisParent) user;
            b.setText(R.string.button_select_student);

        } else if (user instanceof JarvisAdmin) {

            JarvisAdmin admin = (JarvisAdmin) user;
            b.setText(R.string.button_select_teacher);

        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            mMACallback.startDialogFragment(DialogFragmentType.SELECT_USER, frag);

            }
        });

        pagerAdapter = new TeacherPagerAdapter(mTempTeacherList, getFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        return layoutView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_dashboard, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle().toString().matches(getResources().getString(R.string.menu_item_menu))) {

            mMACallback.toggleNavigationDrawer();

        }

        return super.onOptionsItemSelected(item);

    }



    /*
     *
     * Class Methods
     *
     */

    @Override // DialogFragmentCallback Interface Method
    public void stringDialogResponse(String response) {

        if (!response.isEmpty()) {

            if (mMACallback.getCurrentUser() instanceof JarvisStudent) {

            } else if (mMACallback.getCurrentUser() instanceof JarvisTeacher) {

                int index = -1;
                JarvisTeacher teacher = (JarvisTeacher) mMACallback.getCurrentUser();
                for (int i = 0; i < teacher.getStudents().size(); i++) {
                    if (teacher.getStudents().get(i).getFullName().matches(response)) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    mPager.setCurrentItem(index);
                } else {
                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));
                }

            } else if (mMACallback.getCurrentUser() instanceof JarvisParent) {

                int index = -1;
                JarvisParent parent = (JarvisParent) mMACallback.getCurrentUser();
                for (int i = 0; i < parent.getStudents().size(); i++) {
                    if (parent.getStudents().get(i).getFullName().matches(response)) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    mPager.setCurrentItem(index);
                } else {
                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));
                }

            } else if (mMACallback.getCurrentUser() instanceof JarvisAdmin) {

                int index = -1;
                JarvisAdmin admin = (JarvisAdmin) mMACallback.getCurrentUser();
                for (int i = 0; i < admin.getTeachers().size(); i++) {
                    if (admin.getTeachers().get(i).getFullName().matches(response)) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    mPager.setCurrentItem(index);
                } else {
                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));
                }

            }

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponse(FirebaseCommService.FirebaseMethod method) {

        //mMACallback.closeAllDialogs();
        if (method == FirebaseCommService.FirebaseMethod.GET_ALL_STUDENTS) {

            if (mMACallback.getCurrentUser() instanceof JarvisTeacher) {

                final JarvisTeacher teacher = (JarvisTeacher) mMACallback.getCurrentUser();
                teacher.setStudents(mTempStudentList);

            } else if (mMACallback.getCurrentUser() instanceof JarvisParent) {

                final JarvisParent parent = (JarvisParent) mMACallback.getCurrentUser();
                parent.setStudents(mTempStudentList);

            }

            //getActivity().runOnUiThread(mUpdateRunnable);

        } else if (method == FirebaseCommService.FirebaseMethod.GET_ALL_TEACHERS) {

            if (mMACallback.getCurrentUser() instanceof JarvisAdmin) {

                final JarvisAdmin admin = (JarvisAdmin) mMACallback.getCurrentUser();
                admin.setTeachers(mTempTeacherList);

            }

            //getActivity().runOnUiThread(mUpdateRunnable);

        } else {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseSingle(FirebaseCommService.FirebaseMethod method, Object object) {

        if (method == FirebaseCommService.FirebaseMethod.GET_ALL_TEACHERS) {

            if (object instanceof JarvisTeacher) {
                mTempTeacherList.add((JarvisTeacher) object);
                pagerAdapter.notifyDataSetChanged();
            }

        } else if (method == FirebaseCommService.FirebaseMethod.GET_ALL_STUDENTS) {

            if (object instanceof JarvisStudent) {
                mTempStudentList.add((JarvisStudent) object);
                pagerAdapter.notifyDataSetChanged();
            }

        } else {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mMACallback.closeAllDialogs();
                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

                }
            });

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseList(FirebaseCommService.FirebaseMethod method, ArrayList objectList) {

        // nothing should come here

    }

    @Override // ReportCallback Interface Method
    public void sendReport(JarvisReport report) {



    }

    @Override // FirebaseCommCallback Interface Method
    public void errorResponse(FirebaseCommService.FirebaseMethod method) {

        mMACallback.closeAllDialogs();
        mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

    }

    private void pagerMoveNext() {

        mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);

    }

    private void pagerMoveBack() {

        mPager.setCurrentItem(mPager.getCurrentItem() -1, true);

    }

}
