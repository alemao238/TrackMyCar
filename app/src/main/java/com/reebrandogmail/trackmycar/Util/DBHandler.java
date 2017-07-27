package com.reebrandogmail.trackmycar.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.reebrandogmail.trackmycar.model.User;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Created by renan.brando on 27/07/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "trackMyCar";
    // Contacts table name
    private static final String TABLE_USERS = "users";
    // Users Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // Creating tables again
        onCreate(db);
    }

    // Adding new user
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUser()); // User Name
        values.put(KEY_PASSWORD, user.getPassword()); // User Phone Number

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one user
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                        KEY_USERNAME, KEY_PASSWORD}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User contact = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return user
        return contact;
    }

    // Getting All Users
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUser(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                // Adding contact to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return contact list
        return userList;
    }

    // Getting users Count
    public int getUsersCount() {
        int count;
        String countQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // Updating a user
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUser());
        values.put(KEY_PASSWORD, user.getPassword());

        // updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    // Deleting a user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
}
