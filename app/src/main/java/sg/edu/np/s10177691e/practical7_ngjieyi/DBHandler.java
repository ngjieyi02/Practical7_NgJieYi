package sg.edu.np.s10177691e.practical7_ngjieyi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsDB.db";
    public static final String ACCOUNTS = "Accounts";
    public static final String COLUMN_USERNAME = "UserName";
    public static final String COLUMN_PASSWORD = "Password";

    public DBHandler(Context c,
                     String name,
                     SQLiteDatabase.CursorFactory factory,
                     int version)
    {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //codes to create db
        //CREATE TABLE Accounts (UserName, Text, Password)
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + ACCOUNTS +
                " (" + COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_ACCOUNTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //handles different database
        //drop every table and create the new one but the downside is that the data will be gone
        db.execSQL("DROP TABLE IF EXISTS "+ ACCOUNTS);
        onCreate(db);
    }

    public void addAccount (Account a)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, a.getPassword());
        values.put(COLUMN_USERNAME, a.getUsername());

        //connecting to the db
        SQLiteDatabase db = this.getWritableDatabase(); //similar to connecting mySQL by username or password
        db.insert(ACCOUNTS, null, values);
        db.close(); //must close everytime you open
    }

    public Account findAccount (String username, String password)
    {
        String query = "SELECT * FROM " + ACCOUNTS + " WHERE "
                + COLUMN_USERNAME + " =\"" + username + "\"" +
                " AND " + COLUMN_PASSWORD + " =\"" + password + "\"";

        Account a = new Account();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null); //it point to something that is out of the data, -1 position.

        if(cursor.moveToFirst()) { //moving the curson to the first row of the data. it will return true/false. true when there is data, and false when there is no data
            //when there is multiple row
            // while(cursor.moveToNext()){} however cursor.close should not be inside the method
            a.setUsername(cursor.getString(0)); //first column
            a.setPassword(cursor.getString(1)); //second column
            cursor.close();
        }
        else
        {
            a = null;
        }
        db.close();
        return a;
    }
}