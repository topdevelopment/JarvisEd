//
//
// TOP Development
// SignUpCallback.java
//
//

package com.top.jarvised.Callbacks;

import com.top.jarvised.Enums.ScreensID;

public interface SignUpCallback extends MACallback {

    void signupAnswer(ScreensID screenID, String answer);
    void cancelSignup();
    String getSignupAnswer(ScreensID screenID);

}
