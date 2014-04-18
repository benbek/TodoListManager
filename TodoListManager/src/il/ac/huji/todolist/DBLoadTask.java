package il.ac.huji.todolist;

import java.util.List;
import android.os.AsyncTask;

public class DBLoadTask extends AsyncTask<Void, Void, List<TodoItem>> {
    
    private DBHelper dbHelper;
    private TodoListAdapter adapter;

    public DBLoadTask(DBHelper dbHelper, TodoListAdapter adapter) {
        this.dbHelper = dbHelper;
        this.adapter = adapter;
    }
    
    protected void onPreExecute() {
        // Initialization
    }
  
    protected void onProgressUpdate() {
        // Updating progress
    }
    
    protected void onPostExecute(List<TodoItem> result) {
        // Post-execute phase
        this.adapter.addAll(result);
    }

    @Override
    protected List<TodoItem> doInBackground(Void... params) {
        return dbHelper.getTodoItems();
    }

}
