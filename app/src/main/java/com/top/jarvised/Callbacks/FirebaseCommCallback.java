//
//
// TOP Development
// FirebaseCommCallback.java
//
//

package com.top.jarvised.Callbacks;

import com.top.jarvised.Services.FirebaseCommService;

import java.util.ArrayList;

public interface FirebaseCommCallback {

    void successResponse(FirebaseCommService.FirebaseMethod method);
    void successResponseSingle(FirebaseCommService.FirebaseMethod method, Object object);
    void successResponseList(FirebaseCommService.FirebaseMethod method, ArrayList objectList);
    void errorResponse(FirebaseCommService.FirebaseMethod method);

}
