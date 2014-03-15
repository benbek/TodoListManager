package il.ac.huji.todolist;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/***
 * An adapter that handles the list items in the Todo-List of the app.
 *
 */
public class TodoListAdapter extends ArrayAdapter<TodoItem> {
    
    private Context context;

    public TodoListAdapter(Context context, int resource, 
            int textViewResourceId) {
        super(context, resource, textViewResourceId);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {        
        View baseView = super.getView(position, convertView, parent);

        TextView txtTodoTitle = (TextView)baseView.findViewById(
                R.id.txtTodoTitle);
        TextView txtTodoDueDate = (TextView)baseView.findViewById(
                R.id.txtTodoDueDate);
        
        TodoItem item = this.getItem(position);
        String formatTemplate = context.getString(R.string.date_format);
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatTemplate, 
                Locale.getDefault());
        
        if (item.isOverdue()) {
            txtTodoTitle.setTextColor(context.getResources().getColor(
                    R.drawable.warning_color));
            txtTodoDueDate.setTextColor(context.getResources().getColor(
                    R.drawable.warning_color));
        }
        else if (position % 2 == 0) {
            txtTodoTitle.setTextColor(context.getResources().getColor(
                    R.drawable.alternate_color1));
            txtTodoDueDate.setTextColor(context.getResources().getColor(
                    R.drawable.alternate_color1));
        } else {
            txtTodoTitle.setTextColor(context.getResources().getColor(
                    R.drawable.alternate_color2));
            txtTodoDueDate.setTextColor(context.getResources().getColor(
                    R.drawable.alternate_color2));
        }
        
        txtTodoTitle.setText(item.title);
        txtTodoDueDate.setText(item.dueDate == null ? 
                context.getResources().getString(R.string.item_no_due_date) : 
                    dateFormat.format(item.dueDate));
        
        return baseView;
    }
    
}