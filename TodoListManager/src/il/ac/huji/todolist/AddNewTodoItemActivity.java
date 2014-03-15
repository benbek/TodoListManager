package il.ac.huji.todolist;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class AddNewTodoItemActivity extends Activity {

    private String getResourceString(int id) {
        return this.getResources().getString(id);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item);
        // Show the Up button in the action bar.
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is
        // present.
        getMenuInflater().inflate(R.menu.add_new_todo_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            // This ID represents the Home or Up button. In the case of
            // this
            // activity, the Up button is shown. Use NavUtils to allow
            // users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when the user touches the button */
    public void btnCancelClick(View view) {
        finish();
    }
    
    /** Called when the user touches the button */
    public void btnOKClick(View view) {
        EditText newItemTitle = (EditText)findViewById(R.id.edtNewItem);
  
        // Check input
        if(newItemTitle.getText().toString().isEmpty()) {
            newItemTitle.setError(getResourceString(R.string.error_empty_task));
            return;
        }
        
        DatePicker newItemDate = (DatePicker)findViewById(R.id.datePicker);
  
        Intent result = new Intent();
        result.putExtra(CommonActivities.EXTRA_DATA_TITLE, 
                newItemTitle.getText().toString());
        result.putExtra(CommonActivities.EXTRA_DATA_DUE_DATE, 
                new Date(newItemDate.getCalendarView().getDate()));
        setResult(RESULT_OK, result);       
        
        finish();
    }
}
