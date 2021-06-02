//
//
// TOP Development
// ClassConfigurationFragment.java
//
//

package com.top.jarvised.Fragments;

import android.content.Context;
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

import com.top.jarvised.Adapters.ClassListAdapter;
import com.top.jarvised.Callbacks.ClassConfigCallback;
import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.FirebaseCommCallback;
import com.top.jarvised.CustomObjects.JarvisClass;
import com.top.jarvised.Enums.DialogFragmentType;
import com.top.jarvised.MainActivity;
import com.top.jarvised.R;
import com.top.jarvised.Services.FirebaseCommService;

import java.util.ArrayList;

public class ClassConfigurationFragment
        extends BaseFragment
        implements FirebaseCommCallback, DialogFragmentCallback {

    /*
     *
     * Member Variables
     *
     */

    private ArrayList<JarvisClass> mClasses = new ArrayList<>();
    private JarvisClass mSelectedClass = null;
    private ClassConfigCallback mClassConfigCallback = null;
    private ClassListAdapter mClassAdapter = null;
    private String mClassTitleHolder = "";



    /*
     *
     * Constructor
     *
     */

    public static ClassConfigurationFragment newInstance() {

        Bundle args = new Bundle();

        ClassConfigurationFragment fragment = new ClassConfigurationFragment();
        fragment.setArguments(args);
        return fragment;
    }



    /*
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof ClassConfigCallback) {
            mClassConfigCallback = (ClassConfigCallback) context;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mMACallback.startDialogFragment(DialogFragmentType.LOADING, this);
        mMACallback.getFirebaseCommService(this).getAllClasses(mMACallback.getCurrentUser().getContactEmail());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_student_teacher_config, container, false);
        final ClassConfigurationFragment frag = this;
        setHasOptionsMenu(true);

        Button b = layoutView.findViewById(R.id.button_add);
        b.setText(getResources().getString(R.string.button_create));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMACallback.startDialogFragment(DialogFragmentType.ADD_NEW_CLASS, frag);

            }
        });

        b = layoutView.findViewById(R.id.button_edit);
        b.setText(getResources().getString(R.string.button_delete));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSelectedClass != null) {

                    mClassTitleHolder = mSelectedClass.getClassTitle();
                    mMACallback.startDialogFragment(DialogFragmentType.LOADING, frag);
                    mMACallback.getFirebaseCommService(frag)
                            .deleteClass(mMACallback.getCurrentUser().getContactEmail(), mSelectedClass.getClassTitle());

                } else {

                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_class_selected));

                }

            }
        });

        b = layoutView.findViewById(R.id.button_import);
        b.setText(getResources().getString(R.string.button_attach_teacher));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSelectedClass != null) {

                    mClassConfigCallback.attachClass(mSelectedClass);

                } else {

                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_class_selected));

                }

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

    @Override // FirebaseCommCallback Interface Method
    public void successResponse(FirebaseCommService.FirebaseMethod method) {

        mMACallback.closeAllDialogs();
        if (method == FirebaseCommService.FirebaseMethod.ADD_NEW_CLASS) {

            mClasses.add(new JarvisClass(mClassTitleHolder, null, null));
            mClassAdapter.notifyDataSetChanged();
            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_class_created_successfully));


        } else if (method == FirebaseCommService.FirebaseMethod.GET_ALL_CLASSES) {

            View v = getView();
            if (v != null) {

                final ListView lv = v.findViewById(R.id.listview_selection_data);
                mClassAdapter = new ClassListAdapter(mMACallback.getActivityContext(), R.layout.selection_row_item, mClasses);
                lv.setAdapter(mClassAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        mSelectedClass = mClasses.get(position);
                        lv.setItemChecked(position, true);

                    }
                });

            }

        } else if (method == FirebaseCommService.FirebaseMethod.DELETE_CLASS) {

            int index = -1;
            for (int i = 0; i < mClasses.size(); i++) {
                if (mClasses.get(i).getClassTitle().matches(mClassTitleHolder)) {
                    index = i;
                    break;
                }
            }

            if (mClasses.size() == (index - 1)) {
                mSelectedClass = mClasses.get(index - 1);
                View v = getView();
                if (v != null) {
                    ListView lv = v.findViewById(R.id.listview_selection_data);
                    lv.setItemChecked((index - 1), true);
                }
            }

            mClasses.remove(index);
            mClassAdapter.notifyDataSetChanged();
            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_class_deleted_successfully));

        } else {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseSingle(FirebaseCommService.FirebaseMethod method, Object object) {

        if (method == FirebaseCommService.FirebaseMethod.GET_ALL_CLASSES) {

            if (object instanceof JarvisClass) {

                JarvisClass thisClass = (JarvisClass) object;
                mClasses.add(thisClass);

            }

        } else if (method == FirebaseCommService.FirebaseMethod.DELETE_CLASS) {

            mMACallback.closeAllDialogs();
            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_class_deleted_successfully));

        } else {

            mMACallback.closeAllDialogs();
            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseList(FirebaseCommService.FirebaseMethod method, ArrayList objectList) {

        mMACallback.closeAllDialogs();
        mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

    }

    @Override // FirebaseCommCallback Interface Method
    public void errorResponse(FirebaseCommService.FirebaseMethod method) {

        mMACallback.closeAllDialogs();
        if (method == FirebaseCommService.FirebaseMethod.GET_ALL_CLASSES) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_classes_found));

        } else if (method == FirebaseCommService.FirebaseMethod.DELETE_CLASS) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_class_deleted_failed));

        } else {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // DialogFragmentCallback Interface Method
    public void stringDialogResponse(String response) {

        if (!response.trim().isEmpty() && response.trim().length() > 0) {

            mClassTitleHolder = response;
            mMACallback.closeAllDialogs();
            mMACallback.startDialogFragment(DialogFragmentType.LOADING, this);
            mMACallback.getFirebaseCommService(this).addNewClass(mMACallback.getCurrentUser().getContactEmail(), response);

        }

    }

}
