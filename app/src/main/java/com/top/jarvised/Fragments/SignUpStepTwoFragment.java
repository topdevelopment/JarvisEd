//
//
// TOP Development
// SignUpStepTwoFragment.java
//
//

package com.top.jarvised.Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.top.jarvised.Callbacks.DialogFragmentCallback;
import com.top.jarvised.Callbacks.FirebaseCommCallback;
import com.top.jarvised.Adapters.SelectionListAdapter;
import com.top.jarvised.Enums.DialogFragmentType;
import com.top.jarvised.Enums.ScreensID;
import com.top.jarvised.MainActivity;
import com.top.jarvised.R;
import com.top.jarvised.Services.FirebaseCommService;

import java.util.ArrayList;

public class SignUpStepTwoFragment
        extends BaseFragment
        implements FirebaseCommCallback, DialogFragmentCallback {

    /*
     *
     * Member Variables
     *
     */

    private FirebaseCommService mFirebaseService = null;
    private SelectionListAdapter mSchoolsAdapter = null;
    private ArrayList<String> mSchoolList = new ArrayList<>();
    private String mSelectedSchool = "";



    /*
     *
     * Constructor
     *
     */

    public static SignUpStepTwoFragment newInstance() {

        Bundle args = new Bundle();

        SignUpStepTwoFragment fragment = new SignUpStepTwoFragment();
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

        if (mMACallback != null) {

            Log.i(MainActivity.LOG_TAG, "MACallback is not null");
            mFirebaseService = mMACallback.getFirebaseCommService(this);
            mFirebaseService.getAllSchools();

        } else {

            Log.i(MainActivity.LOG_TAG, "MACallback is null");

        }

        mMACallback.startDialogFragment(DialogFragmentType.LOADING, this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_selection, container, false);
        setHasOptionsMenu(true);

        Button b = layoutView.findViewById(R.id.button_select);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (mSelectedSchool.isEmpty()) {

                mMACallback.postToastMessage(getResources().getString(R.string.toast_message_select_school_before_continuing));

            } else {

                Log.i(MainActivity.LOG_TAG, "School Selected: " + mSelectedSchool);
                mSignupCallback.signupAnswer(ScreensID.SignupStepTwo, mSelectedSchool);

            }

            }
        });

        final EditText et = layoutView.findViewById(R.id.edittext_search_field);
        et.setHint(getResources().getString(R.string.edittext_hint_search_school));
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!et.getText().toString().trim().isEmpty()) {

                    mSchoolsAdapter.getFilter().filter(et.getText().toString().trim());

                } else {

                    mSchoolsAdapter.getFilter().filter("");

                }

            }
        });

        final ListView lv = layoutView.findViewById(R.id.listview_selection_data);
        mSchoolsAdapter = new SelectionListAdapter(mMACallback.getActivityContext(), R.layout.selection_row_item, mSchoolList);
        lv.setAdapter(mSchoolsAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mSchoolList.get(position).matches(mSelectedSchool)) {

                    Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
                    lv.setSelector(transparentDrawable);
                    mSelectedSchool = "";

                } else {

                    Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.listSelectorColor));
                    lv.setSelector(colorDrawable);
                    mSelectedSchool = mSchoolList.get(position);

                }

            }
        });

        return layoutView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_signupsteptwo, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle().toString().matches(getResources().getString(R.string.menu_item_add))) {

            mMACallback.startDialogFragment(DialogFragmentType.ADD_NEW_SCHOOL, this);

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

        Log.i(MainActivity.LOG_TAG, "Success from Method: " + method.toString() + " in Callback Method: successResponse");

        if (method == FirebaseCommService.FirebaseMethod.ADD_NEW_SCHOOL) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_school_created));

        } else {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

        }

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseSingle(FirebaseCommService.FirebaseMethod method, Object object) {

        Log.i(MainActivity.LOG_TAG, "Success from Method: " + method.toString() + " in Callback Method: successResponseSingle");
        mMACallback.closeAllDialogs();
        mMACallback.postToastMessage(getResources().getString(R.string.toast_message_something_went_wrong));

    }

    @Override // FirebaseCommCallback Interface Method
    public void successResponseList(FirebaseCommService.FirebaseMethod method, ArrayList objectList) {

        Log.i(MainActivity.LOG_TAG, "Success from Method: " + method.toString() + " in Callback Method: successResponseList");

        for (int i = 0; i < objectList.size(); i++) {

            Object o = objectList.get(i);
            if (o instanceof String) {

                Log.i(MainActivity.LOG_TAG, "Retrieved School: " + o);
                mSchoolList.add(String.valueOf(o));

            }

        }
        mSchoolsAdapter.notifyDataSetChanged();
        mMACallback.closeAllDialogs();

    }

    @Override // FirebaseCommCallback Interface Method
    public void errorResponse(FirebaseCommService.FirebaseMethod method) {

        Log.i(MainActivity.LOG_TAG, "Error from Method: " + method.toString() + " in Callback Method: errorResponse");

        mMACallback.closeAllDialogs();
        if (method == FirebaseCommService.FirebaseMethod.GET_ALL_SCHOOLS) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_error_get_schools));

        } else if (method == FirebaseCommService.FirebaseMethod.ADD_NEW_SCHOOL) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_error_add_school));

        }

    }

    @Override // DialogFragmentCallback Interface Method
    public void stringDialogResponse(String response) {

        if (response.trim().isEmpty()) {

            mMACallback.postToastMessage(getResources().getString(R.string.toast_message_no_school_name));

        } else {

            mMACallback.closeAllDialogs();
            mFirebaseService.addNewSchool(response);

        }

    }

}
