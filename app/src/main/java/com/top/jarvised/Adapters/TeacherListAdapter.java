//
//
// TOP Development
// TeacherListAdapter.java
//
//

package com.top.jarvised.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.R;

import java.util.List;

public class TeacherListAdapter extends ArrayAdapter<JarvisTeacher> {

    /*
     *
     * Member Variables
     *
     */

    private Context mContext;
    private List<JarvisTeacher> mTeacherList;
    private int mSelectedIndex = -1;



    /*
     *
     * Constructor
     *
     */

    public TeacherListAdapter(Context context, List<JarvisTeacher> teachers) {

        super(context, R.layout.selection_row_item, teachers);
        mContext = context;
        mTeacherList = teachers;

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
            convertView = inflater.inflate(R.layout.selection_row_item, parent, false);

        }

        if (mSelectedIndex == position) convertView.setSelected(true);

        ImageView iv = convertView.findViewById(R.id.imageview_user_avatar);
        iv.setImageResource(R.drawable.student_01);

        TextView tv = convertView.findViewById(R.id.text1);
        tv.setText(mTeacherList.get(position).getFullName());

        tv = convertView.findViewById(R.id.text2);
        tv.setText(mTeacherList.get(position).getContactEmail());

        return convertView;

    }

    public void setSelectedIndex(int index) {

        mSelectedIndex = index;

    }

}
