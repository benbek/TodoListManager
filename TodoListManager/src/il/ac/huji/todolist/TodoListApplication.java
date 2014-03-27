package il.ac.huji.todolist;

import com.parse.Parse;

import android.app.Application;

public class TodoListApplication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        Parse.initialize(getApplicationContext(), 
                "iLpEvdrWuye11ZDkRwvzKUS9i5iA5GTRQPzTLWY0", 
                "xq0IwMFnV0xAehWuktDphdFjAHMT4KAXGm5oa2M6");
    }

}
