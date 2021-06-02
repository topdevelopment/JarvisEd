//
//
// TOP Development
// TeacherConfigurationFragment.java
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

import com.top.jarvised.Adapters.TeacherListAdapter;
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

public class TeacherConfigurationFragment
        extends BaseFragment
        implements DialogFragmentCallback, FirebaseCommCallback {

    /*
     *
     * Member Variables
     *
     */

    private JarvisTeacher mSelectedTeacher;
    private ArrayList<String> mTeacherEmailList = new ArrayList<>();
    private ArrayList<JarvisTeacher> mTeacherList = new ArrayList<>();
    private TeacherListAdapter mTeacherAdapter;



    /*
     *
     * Constructor
     *
     */

    public static TeacherConfigurationFragment newInstance() {

        Bundle args = new Bundle();

        TeacherConfigurationFragment fragment = new TeacherConfigurationFragment();
        fragment.setArguments(args);
        return fragment;
    }



    /*
     *
     * Lifcycle Methods
     *
     */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        JarvisUser user = mMACallback.getCurrentUser();
        if (user instanceof JarvisAdmin) {

            JarvisAdmin admin  = (JarvisAdmin) user;
            if (admin.getTeachers().size() == 0) {
                getTeachersFromList(admin.getTeachersList());
            } else {
                mTeacherList.addAll(admin.getTeachers());
                mTeacherAdapter.notifyDataSetChanged();
            }


        } else {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_current_teachers));

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

        addButton.setText(R.string.button_add_teacher);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMACallback.selectScreen(ScreensID.AddTeacher);

            }
        });

        editbutton.setText(R.string.button_edit_teacher);
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSelectedTeacher == null) {

                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_teacher_selected));

                } else {

                    mAddStudentTeacherCallback.editTeacherDetails(mSelectedTeacher);

                }

            }
        });

        importButton.setText(R.string.button_import_teachers);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMACallback.postToastMessage(getResources().getString(R.string.toast_message_feature_unavailable));

            }
        });

        final ListView lv = layoutView.findViewById(R.id.listview_selection_data);
        mTeacherAdapter = new TeacherListAdapter(mMACallback.getActivityContext(), mTeacherList);
        lv.setAdapter(mTeacherAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mSelectedTeacher = mTeacherList.get(position);
                mTeacherAdapter.setSelectedIndex(position);
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

        // do nothing

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponse(final FirebaseCommService.FirebaseMethod method) {

        mMACallback.forceRunOnUIThread(new Runnable() {
            @Override
            public void run() {

                mMACallback.closeAllDialogs();
                if (method != FirebaseCommService.FirebaseMethod.GET_ALL_TEACHERS) {

                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

                }

            }
        });

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseSingle(FirebaseCommService.FirebaseMethod method, Object object) {

        if (method == FirebaseCommService.FirebaseMethod.GET_ALL_TEACHERS) {

            if (object instanceof JarvisTeacher) {

                final JarvisTeacher teacher = (JarvisTeacher) object;

                mMACallback.forceRunOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mTeacherList.add(teacher);
                        mAddStudentTeacherCallback.updateTeacherList(mTeacherList);
                        mTeacherAdapter.notifyDataSetChanged();
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
                if (method == FirebaseCommService.FirebaseMethod.GET_ALL_TEACHERS) {

                    if (!mErrorOccurred) {

                        mErrorOccurred = true;
                        mMACallback.postToastMessage(getResources().getString(R.string.toast_message_error_pulling_teachers));

                    }

                } else {

                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

                }

            }
        });

    }

    private void getTeachersFromList(List<String> list) {

        if (list.size() > 0) {

            mMACallback.getFirebaseCommService(this).getAllTeachers(list);

        } else {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_current_teachers));

        }

    }

}
