package il.ac.huji.todolist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/***
 * An adapter that handles the list items in the Todo-List of the app.
 *
 */
public class TodoListAdapter extends ArrayAdapter<String> {
    
    private Context context;

    public TodoListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView baseView = (TextView)super.getView(
                position, convertView, parent);

        if (position % 2 == 0) {
            baseView.setTextColor(context.getResources().getColor(
                    R.drawable.alternate_color1));
        } else {
            baseView.setTextColor(context.getResources().getColor(
                    R.drawable.alternate_color2));
        }
        
        return baseView;
    }
    
}