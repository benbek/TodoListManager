package il.ac.huji.todolist;

/***
 * Holds common functions and constants all the activities in this app
 * should share.
 */
public class CommonActivities {

    static final int ADD_TODO_CODE = 12;
    
    static final long NULL_DUE_DATE_VALUE = -1;
    
    static final String EXTRA_DATA_TITLE = "title";
    static final String EXTRA_DATA_DUE_DATE = "dueDate";
    
    static final String PARSE_OBJECT_NAME = "TodoItem";
    static final String PARSE_ITEM_ID_KEY = "rowId";
    static final String PARSE_ITEM_TITLE_KEY = EXTRA_DATA_TITLE;
    static final String PARSE_ITEM_DUE_DATE_KEY = EXTRA_DATA_DUE_DATE;
}
