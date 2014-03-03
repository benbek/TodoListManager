package il.ac.huji.todolist;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

/***
 * The main Todo List activity.
 *
 */
public class TodoListManagerActivity extends Activity {
    
    // An array adapter to bind the ArrayList to the ListView
    private TodoListAdapter listAdapter = null;
    
    private String getResourceString(int id) {
        return this.getResources().getString(id);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        
        listAdapter = new TodoListAdapter(getApplicationContext(), 
                android.R.layout.simple_list_item_1);

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
            
            menu.setHeaderTitle(listAdapter.getItem(info.position));
            inflater.inflate(R.menu.todo_list_item_menu, menu);
      }
    }
    
    /** Called when the user touches the "delete" menu item */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = 
            (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        
        switch(item.getItemId()) {
            case R.id.menuItemDelete:
                listAdapter.remove(listAdapter.getItem(info.position));
            default:
                return super.onContextItemSelected(item);
          }
    }

    /** Called when the user touches the "add" menu item */
    public void addTask(MenuItem item) {
        EditText newItem = (EditText)findViewById(R.id.edtNewItem);
        
        // Check input
        if(newItem.getText().toString().isEmpty()) {
            newItem.setError(getResourceString(R.string.error_empty_task));
            return;
        }
        
        listAdapter.add(newItem.getText().toString());
        newItem.setText(R.string.edtNewItem_default_text);
    }
}
