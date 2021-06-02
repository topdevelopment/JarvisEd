//
//
// TOP Development
// SelectTeacherDialogFragment.java
//
//

package com.top.jarvised.DialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.top.jarvised.Adapters.DialogTeacherListAdapter;
import com.top.jarvised.Adapters.StudentListAdapter;
import com.top.jarvised.Adapters.TeacherListAdapter;
import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.MACallback;
import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.CustomObjects.JarvisUser;
import com.top.jarvised.R;

import java.util.List;

public class SelectTeacherDialogFragment extends DialogFragment {

    /*
     *
     * Member Variables
     *
     */

    private DialogTeacherListAdapter mTeacherAdapter;
    private DialogFragmentCallback mCallback;
    private List<JarvisTeacher> mTeachersList;
    private MACallback mMACallback;
    private JarvisTeacher mSelectedTeacher;



    /*
     *
     * Constructor
     *
     */

    public SelectTeacherDialogFragment(DialogFragmentCallback callback, List<JarvisTeacher> teachers) {

        mCallback = callback;
        mTeachersList = teachers;

    }



    /*
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof MACallback) {
            mMACallback = (MACallback) context;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.dialog_selection_layout, container, false);

        ListView lv = layoutView.findViewById(R.id.listview_selection_data);
        mTeacherAdapter = new DialogTeacherListAdapter(mMACallback.getActivityContext(), android.R.layout.simple_list_item_1, mTeachersList);
        lv.setAdapter(mTeacherAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mSelectedTeacher = mTeachersList.get(position);

            }
        });

        Button b = layoutView.findViewById(R.id.button_select);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSelectedTeacher != null) {

                    mCallback.stringDialogResponse(mSelectedTeacher.getFullName());
                    dismiss();

                } else {

                    mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_student_selected));

                }

            }
        });

        b = layoutView.findViewById(R.id.button_cancel);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        return layoutView;

    }

}
