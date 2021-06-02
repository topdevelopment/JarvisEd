//
//
// TOP Development
// DrawerListContent.java
//
//

package com.top.jarvised.CustomObjects;

import android.content.Context;

import androidx.annotation.NonNull;

import com.top.jarvised.Enums.ScreensID;
import com.top.jarvised.R;

import java.util.ArrayList;

public class DrawerListContent {

    /*
     *
     * Member Variables
     *
     */

    private ArrayList<DrawerItem> mItems;



    /*
     *
     * Initializer
     *
     */

    public DrawerListContent(Context context, boolean dashboard, boolean students, boolean teachers) {
        mItems = new ArrayList<>();
        int count = 0;
        mItems.add(new DrawerItem(String.valueOf(count), "Dashboard", R.drawable.dashboard_black, ScreensID.Dashboard));
        count++;
        mItems.add(new DrawerItem(String.valueOf(count), "Teacher Directory", R.drawable.teachers_black, ScreensID.TeacherConfig));
        count++;
        mItems.add(new DrawerItem(String.valueOf(count), "Student Directory", R.drawable.students_black, ScreensID.StudentConfig));
        count++;
        mItems.add(new DrawerItem(String.valueOf(count), "Classes Catalogue", R.drawable.classes_black, ScreensID.ClassConfig));
    }



    /*
     *
     * Class Methods
     *
     */

    public ArrayList<DrawerItem> getItems() {
        return mItems;
    }



    /*
     *
     * Inner Class:
     *
     */

    public class DrawerItem {

        /* Member Variables */

        public String mId;
        public String mContent;
        public int mIconId;
        private ScreensID mScreenId;



        /* Initializer */

        public DrawerItem(String id, String content, int icon_id, ScreensID screenId) {
            mId = id;
            mContent = content;
            mIconId = icon_id;
            mScreenId = screenId;
        }



        /* Class Methods */

        @NonNull
        @Override
        public String toString() {
            return mContent;
        }

        public ScreensID getScreenId() {
            return mScreenId;
        }

    }

}
