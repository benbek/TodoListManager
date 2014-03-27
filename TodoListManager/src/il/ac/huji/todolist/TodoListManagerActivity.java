package il.ac.huji.todolist;

import java.util.Date;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/***
 * The main Todo List activity.
 *
 */
public class TodoListManagerActivity extends Activity {
    static final String INTENT_DIAL_PREFIX = "tel:";
    
    // An array adapter to bind the ArrayList to the ListView
    private TodoListAdapter listAdapter = null;
    
    // A database and object manager
    private DBHelper dbHelper;
       
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        
        dbHelper = new DBHelper(getApplicationContext());
        
        listAdapter = new TodoListAdapter(getApplicationContext(), 
                R.layout.todo_list_item_layout, R.id.txtTodoTitle);
        listAdapter.addAll(dbHelper.getTodoItems());
        
        // Bind the array adapter to the ListView
        final ListView todoListView = (ListView)findViewById(R.id.lstTodoItems);
        todoListView.setAdapter(listAdapter);
        registerForContextMenu(todoListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is
        // present.
        getMenuInflater().inflate(R.menu.todo_list_manager, menu);
        return true;
    }
    
    /** Called when the user long-clicks an item in the list */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, 
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lstTodoItems) {
            // Inflate the context menu
            MenuInflater inflater = getMenuInflater();
            
            AdapterView.AdapterContextMenuInfo info = 
                (AdapterView.AdapterContextMenuInfo)menuInfo;
            
            TodoItem item = listAdapter.getItem(info.position);
            
            menu.setHeaderTitle(item.title);
            inflater.inflate(R.menu.todo_list_item_menu, menu);
            
            if (getDialString(item) != null) {
                menu.findItem(R.id.menuItemCall).setTitle(item.title);
                menu.findItem(R.id.menuItemCall).setVisible(true);
            } else {
                menu.findItem(R.id.menuItemCall).setVisible(false);
            }
      }
    }
    
    /** Called when the user touches the "delete" menu item */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = 
            (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        
        TodoItem selection = listAdapter.getItem(info.position);
        
        switch(item.getItemId()) {
            case R.id.menuItemDelete:
                dbHelper.deleteTodoItem(selection.id);
                listAdapter.remove(selection);
                return true;
            case R.id.menuItemCall:
                Intent dial = new Intent(Intent.ACTION_DIAL,
                    Uri.parse(getDialString(selection)));
                startActivity(dial);
                return true;
          }
        
        return super.onContextItemSelected(item);
    }

    /** Called when the user touches the "add" menu item */
    public void addTaskMenuItem(MenuItem item) {
        Intent intent = new Intent(this, AddNewTodoItemActivity.class);
        startActivityForResult(intent, CommonActivities.ADD_TODO_CODE);
    }

    protected void addTask(String title, Date dueDate) {
        TodoItem item = new TodoItem();
        
        item.title = title;
        item.dueDate = dueDate;
        
        addTask(item);
    }
    
    protected void addTask(TodoItem item) {
        dbHelper.addTodoItem(item);        
        listAdapter.add(item);
    }
    
    protected String getDialString(TodoItem item) {
        if (item.title.startsWith(this.getResources().getString(
                R.string.item_prefix_call))) {
            return INTENT_DIAL_PREFIX.concat(item.title.substring(
                    this.getResources().getString(R.string.item_prefix_call).
                    length()));
        } else {
            return null;
        }
    }
    
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        switch (reqCode) {
        case CommonActivities.ADD_TODO_CODE:
            if (resCode == RESULT_OK) {
                String itemTitle = data.getStringExtra(
                        CommonActivities.EXTRA_DATA_TITLE);
                Date itemDueDate = (Date)data.getSerializableExtra(
                        CommonActivities.EXTRA_DATA_DUE_DATE);
                
                addTask(itemTitle, itemDueDate);
            }
        }
    }
}
