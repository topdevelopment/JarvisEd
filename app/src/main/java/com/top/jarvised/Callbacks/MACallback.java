//
//
// TOP Development
// MACallback.java
//
//

package com.top.jarvised.Callbacks;

import android.content.Context;
import android.widget.EditText;

import androidx.viewpager.widget.ViewPager;

import com.top.jarvised.CustomObjects.JarvisReport;
import com.top.jarvised.CustomObjects.JarvisUser;
import com.top.jarvised.Enums.DialogFragmentType;
import com.top.jarvised.Enums.ScreensID;
import com.top.jarvised.Services.FirebaseCommService;

public interface MACallback {

    Context getActivityContext();
    void selectScreen(ScreensID screenId);
    void postToastMessage(String message);
    FirebaseCommService getFirebaseCommService(FirebaseCommCallback callback);
    void startDialogFragment(DialogFragmentType type, DialogFragmentCallback dialogCallback);
    JarvisUser getCurrentUser();
    void closeAllDialogs();
    void toggleNavigationDrawer();
    void showKeyboardForET(EditText et);
    void startReportDialog(JarvisReport.ReportType type, ReportCallback callback);
    void forceRunOnUIThread(Runnable runnable);

}
