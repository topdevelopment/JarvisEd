//
//
// TOP Development
// SelectStudentDialogFragment.java
//
//

package com.top.jarvised.DialogFragments;

import android.app.Dialog;
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

import com.top.jarvised.Adapters.DialogStudentListAdapter;
import com.top.jarvised.Adapters.JarvisUserListAdapter;
import com.top.jarvised.Adapters.StudentListAdapter;
import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.MACallback;
import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.CustomObjects.JarvisUser;
import com.top.jarvised.R;

import java.util.List;

public class SelectStudentDialogFragment extends DialogFragment {

    /*
     *
     * Member Variables
     *
     */

    private DialogStudentListAdapter mStudentAdapter;
    private DialogFragmentCallback mCallback;
    private List<JarvisStudent> mStudentsList;
    private MACallback mMACallback;
    private JarvisUser mSelectedUser;



    /*
     *
     * Constructor
     *
     */

    public SelectStudentDialogFragment(DialogFragmentCallback callback, List<JarvisStudent> students) {

        mCallback = callback;
        mStudentsList = students;

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
        mStudentAdapter = new DialogStudentListAdapter(mMACallback.getActivityContext(), android.R.layout.simple_list_item_1, mStudentsList);
        lv.setAdapter(mStudentAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mSelectedUser = mStudentsList.get(position);

            }
        });

        Button b = layoutView.findViewById(R.id.button_select);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSelectedUser != null) {

                    mCallback.stringDialogResponse(mSelectedUser.getFullName());
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
