package il.ac.huji.todolist;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;

public class TodoListApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        //initializeParse();
    }
    
    // Parse is not currently needed; This method is left for a future endeavor
    @SuppressWarnings("unused")
    private void initializeParse() {
        Parse.initialize(getApplicationContext(), 
                "iLpEvdrWuye11ZDkRwvzKUS9i5iA5GTRQPzTLWY0", 
                "xq0IwMFnV0xAehWuktDphdFjAHMT4KAXGm5oa2M6");
        
        ParseUser.enableAutomaticUser();
        ParseUser.getCurrentUser().increment("RunCount");
        ParseUser.getCurrentUser().saveInBackground();
        
        // Providing a default ACL where only the current user is given access:
        ParseACL.setDefaultACL(new ParseACL(), true);
    }

}
