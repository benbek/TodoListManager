package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    protected static final String dbName = "todo_db";
    protected static final String tableName = "todo";
    
    protected static final String colId = "_id";
    protected static final String colTitle = "title";
    protected static final String colDue = "due";
    
    public DBHelper(Context context) {
        super(context, dbName, null, 1);
    }
    
    private void clearAllParseObjects() { 
        ParseQuery<ParseObject> query = 
            ParseQuery.getQuery(CommonActivities.PARSE_OBJECT_NAME);
        query.findInBackground(new FindCallback<ParseObject>() {
            
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : objects) {
                        parseObject.deleteInBackground();
                    }
                }
            }
        });
    }
    
    /***
     * Adds an item to the database. The object will then hold its new id
     * given from the database.
     * @param item The item to add.
     */
    void addTodoItem(TodoItem item) {
        long itemDueDate;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues taskValues = new ContentValues();
        
        itemDueDate = (item.dueDate != null) ? item.dueDate.getTime() : 
            CommonActivities.NULL_DUE_DATE_VALUE;
        
        taskValues.put(colTitle, item.title);
        taskValues.put(colDue, itemDueDate);
        
        item.id = (int)db.insert(tableName, null, taskValues);
        
        ParseObject parseItem = 
            new ParseObject(CommonActivities.PARSE_OBJECT_NAME);
        parseItem.put(CommonActivities.PARSE_ITEM_ID_KEY, item.id);
        parseItem.put(CommonActivities.PARSE_ITEM_TITLE_KEY, item.title);
        parseItem.put(CommonActivities.PARSE_ITEM_DUE_DATE_KEY, itemDueDate);
        parseItem.saveInBackground();
    }
    
    boolean deleteTodoItem(Integer itemId) {
        ParseQuery<ParseObject> query = 
            ParseQuery.getQuery(CommonActivities.PARSE_OBJECT_NAME);
        query.whereEqualTo(CommonActivities.PARSE_ITEM_ID_KEY, itemId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    object.deleteInBackground();
                }
            }
        });
        
        SQLiteDatabase db = this.getWritableDatabase();
        return 0 < db.delete(tableName, colId + " = ?", 
                new String[] { itemId.toString() });
    }
    
    List<TodoItem> getTodoItems() {
        long itemDueDate;
        ArrayList<TodoItem> items = new ArrayList<TodoItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[] { colId, colTitle,
                colDue }, null, null, null, null, "1 asc");
        
        if (cursor.moveToFirst()) {
            do {
                TodoItem item = new TodoItem();
                item.id = cursor.getInt(0);
                item.title = cursor.getString(1);
                itemDueDate = cursor.getLong(2);
                if (itemDueDate != CommonActivities.NULL_DUE_DATE_VALUE) {
                    item.dueDate = new Date(itemDueDate);
                } else {
                    item.dueDate = null;
                }
                
                items.add(item);
            } while (cursor.moveToNext());
        }
        
        return items;
    }
    
    /***
     * Creating our database. Also clears out any old Parse objects we may have.
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createCommandText = 
            "create table " + tableName + " ( " +
            colId + " integer primary key autoincrement, " +
            colTitle + " text, " +
            colDue + " long );";
        
        db.execSQL(createCommandText);
        
        clearAllParseObjects();
    }

    /***
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
