//
//
// TOP Development
// DialogStudentListAdapter.java
//
//

package com.top.jarvised.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.top.jarvised.CustomObjects.JarvisStudent;
import com.top.jarvised.R;

import java.util.List;

public class DialogStudentListAdapter extends ArrayAdapter<JarvisStudent> {

    /*
     *
     * Member Variables
     *
     */

    private Context mContext;
    private List<JarvisStudent> mStudentList;



    /*
     *
     * Constructor
     *
     */

    public DialogStudentListAdapter(Context context, int resource, List<JarvisStudent> students) {

        super(context, resource, students);
        mContext = context;
        mStudentList = students;

    }



    /*
     *
     * Lifecycle Methods
     *
     */

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dialog_selection_layout, parent, false);

        }

        TextView tv = convertView.findViewById(R.id.text1);
        tv.setText(mStudentList.get(position).getFullName());

        return convertView;

    }

}
