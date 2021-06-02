//
//
// TOP Development
// StudentConfigurationFragment.java
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.top.jarvised.Adapters.StudentListAdapter;
import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.FirebaseCommCallback;
import com.top.jarvised.CustomObjects.JarvisAdmin;
import com.top.jarvised.CustomObjects.JarvisParent;
import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.CustomObjects.JarvisUser;
import com.top.jarvised.Enums.DialogFragmentType;
import com.top.jarvised.Enums.ScreensID;
import com.top.jarvised.MainActivity;
import com.top.jarvised.R;
import com.top.jarvised.Services.FirebaseCommService;

import java.util.ArrayList;
import java.util.List;

public class StudentConfigurationFragment
        extends BaseFragment
        implements DialogFragmentCallback, FirebaseCommCallback {

    /*
     *
     * Member Variables
     *
     */

    private JarvisStudent mSelectedStudent = null;
    private ArrayList<String> mStudentEmailList = new ArrayList<>();
    private ArrayList<JarvisStudent> mStudentList = new ArrayList<>();
    private StudentListAdapter mStudentAdapter;



    /*
     *
     * Constructor
     *
     */

    public static StudentConfigurationFragment newInstance() {

        Bundle args = new Bundle();

        StudentConfigurationFragment fragment = new StudentConfigurationFragment();
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
        if (user instanceof JarvisTeacher) {

            JarvisTeacher teacher = (JarvisTeacher) user;
            if (teacher.getStudents().size() == 0) {
                getStudentsFromList(teacher.getStudentsList());
            } else {
                mStudentList.addAll(teacher.getStudents());
                mStudentAdapter.notifyDataSetChanged();
            }

        } else if (user instanceof JarvisAdmin) {

            JarvisAdmin admin  = (JarvisAdmin) user;
            if (admin.getStudents().size() == 0) {
                getStudentsFromList(admin.getStudentList());
            } else {
                mStudentList.addAll(admin.getStudents());
                mStudentAdapter.notifyDataSetChanged();
            }

        } else if (user instanceof JarvisParent) {

            JarvisParent parent = (JarvisParent) user;
            if (parent.getStudents().size() == 0) {
                getStudentsFromList(parent.getStudentsList());
            } else {
                mStudentList.addAll(parent.getStudents());
                mStudentAdapter.notifyDataSetChanged();
            }

        } else {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_current_students));

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_student_teacher_config, container, false);
        setHasOptionsMenu(true);

        final Button addButton = layoutView.findViewById(R.id.button_add);
        final Button editbutton = layoutView.findViewById(R.id.button_edit);
        final Button importButton = layoutView.findViewById(R.id.button_import);

        addButton.setText(R.string.button_add_student);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMACallback.selectScreen(ScreensID.AddStudent);

            }
        });

        editbutton.setText(R.string.button_edit_student);
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSelectedStudent == null) {

                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_student_selected));

                } else {

                    mAddStudentTeacherCallback.editStudentDetails(mSelectedStudent);

                }

            }
        });

        importButton.setText(R.string.button_import_students);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMACallback.postToastMessage(getResources().getString(R.string.toast_message_feature_unavailable));

            }
        });

        final ListView lv = layoutView.findViewById(R.id.listview_selection_data);
        mStudentAdapter = new StudentListAdapter(mMACallback.getActivityContext(), mStudentList);
        lv.setAdapter(mStudentAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mSelectedStudent = mStudentList.get(position);
                mStudentAdapter.setSelectedIndex(position);
                Log.i(MainActivity.LOG_TAG, "Selected Index: " + position);
                view.setSelected(true);

            }
        });

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

        // do nothing here

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponse(final FirebaseCommService.FirebaseMethod method) {

        mMACallback.forceRunOnUIThread(new Runnable() {
            @Override
            public void run() {

                mMACallback.closeAllDialogs();
                if (method != FirebaseCommService.FirebaseMethod.GET_ALL_STUDENTS) {

                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

                }

            }
        });

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseSingle(FirebaseCommService.FirebaseMethod method, Object object) {

        if (method == FirebaseCommService.FirebaseMethod.GET_ALL_STUDENTS) {

            if (object instanceof JarvisStudent) {

                final JarvisStudent student = (JarvisStudent) object;

                mMACallback.forceRunOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mStudentList.add(student);
                        mAddStudentTeacherCallback.updateStudentList(mStudentList);
                        mStudentAdapter.notifyDataSetChanged();
                    }
                });

            }

        } else {

            errorResponse(method);

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseList(FirebaseCommService.FirebaseMethod method, ArrayList objectList) {

        // no responses should come here

        mMACallback.forceRunOnUIThread(new Runnable() {
            @Override
            public void run() {

                mMACallback.closeAllDialogs();
                mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

            }
        });

    }

    @Override // FirebaseCommCallback Interface Method
    public void errorResponse(final FirebaseCommService.FirebaseMethod method) {

        mMACallback.forceRunOnUIThread(new Runnable() {
            @Override
            public void run() {

                mMACallback.closeAllDialogs();
                if (method == FirebaseCommService.FirebaseMethod.GET_ALL_STUDENTS) {

                    if (!mErrorOccurred) {

                        mErrorOccurred = true;
                        mMACallback.postToastMessage(getResources().getString(R.string.toast_message_error_pulling_students));

                    }

                } else {

                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

                }

            }
        });

    }

    private void getStudentsFromList(List<String> list) {

        if (list.size() > 0) {

            mMACallback.startDialogFragment(DialogFragmentType.LOADING, this);
            mMACallback.getFirebaseCommService(this).getAllStudents(list);

        } else {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_current_students));

        }

    }

}
