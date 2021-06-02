//
//
// TOP Development
// LoginCallback.java
//
//

package com.top.jarvised.Callbacks;

import com.top.jarvised.CustomObjects.JarvisUser;

public interface LoginCallback extends MACallback {

    void setCurrentUser(JarvisUser user);

}
