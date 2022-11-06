package com.example.androidassignmentsone;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String ACTIVITY_NAME="ChatDatabaseHelper";
    public static final String DATABASE_NAME="ChatDatabase";
    public static int VERSION_NUM=1;
    public static final String TABLE_NAME="chat";
    public static final String KEY_ID="id";
    public static final String KEY_MESSAGE="message";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i(ACTIVITY_NAME, "Initiate onCreate function : ");
        db.execSQL("CREATE TABLE "+TABLE_NAME+"( "+KEY_ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_MESSAGE+" TEXT NOT NULL);");
        Log.i(ACTIVITY_NAME, "In onCreate Function, We created new Table : "+TABLE_NAME);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion "+oldVersion+", newVersion" +newVersion+"...");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        Log.i(ACTIVITY_NAME, "onUpgrade, Dropped Table-"+TABLE_NAME);
        onCreate(db);
    }
    public void addMessage(String newMessage){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_MESSAGE, newMessage);
        Log.i(ACTIVITY_NAME,"Added new message :");
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public ArrayList<String> getMessages(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor messagesCursor=db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        ArrayList<String> messages=new ArrayList<>();
        Log.i(ACTIVITY_NAME, "Cursor’s  column count :" + messagesCursor.getColumnCount());
        if (messagesCursor.moveToFirst()){
            do{
                int messageColumnIndex=messagesCursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
                int idColumnIndex=messagesCursor.getColumnIndex(ChatDatabaseHelper.KEY_ID);
                if (messageColumnIndex>=0 && idColumnIndex>=0){
                    Log.i(ACTIVITY_NAME, "SQL Information: ColumnName -"+KEY_ID+", Value-" +
                            messagesCursor.getString(idColumnIndex));
                    Log.i(ACTIVITY_NAME, "SQL Information: ColumnName -"+KEY_MESSAGE+", Value-" +
                            messagesCursor.getString(messageColumnIndex));

                    messages.add(messagesCursor.getString(messageColumnIndex));
                }
            }
            while(messagesCursor.moveToNext());
        }
        messagesCursor.close();
        return messages;
    }


}