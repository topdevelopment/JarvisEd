//
//
// TOP Development
// JarvisUserListAdapter.java
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

import com.top.jarvised.CustomObjects.JarvisTeacher;
import com.top.jarvised.CustomObjects.JarvisUser;
import com.top.jarvised.R;

import java.util.List;

public class JarvisUserListAdapter extends ArrayAdapter<JarvisUser> {

    /*
     *
     * Member Variables
     *
     */

    private Context mContext;
    private List<JarvisUser> mUserList;



    /*
     *
     * Constructor
     *
     */

    public JarvisUserListAdapter(Context context, int resource, List<JarvisUser> users) {

        super(context, resource, users);
        mContext = context;
        mUserList = users;

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

        TextView tv = convertView.findViewById(R.id.text1);
        tv.setText(mUserList.get(position).getFullName());

        return convertView;

    }

}
