package com.reebrandogmail.trackmycar.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.reebrandogmail.trackmycar.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by renan.brando on 27/07/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    
    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "trackMyCar";
    // Contacts table name
    private static final String TABLE_USERS = "users";
    // Users Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_MAIL + " TEXT,"
                + KEY_PHONE + " TEXT" + ")";
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
        values.put(KEY_PASSWORD, user.getPassword()); // User Password
        values.put(KEY_MAIL, user.getMail()); // User Email
        values.put(KEY_PHONE, user.getPhone()); // User Phone

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one user
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                        KEY_USERNAME, KEY_PASSWORD, KEY_MAIL, KEY_PHONE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        user.setMail(cursor.getString(3));
        user.setPhone(cursor.getString(4));

        db.close();
        cursor.close();

        // return user
        return user;
    }

    // Logging user
    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                        KEY_USERNAME, KEY_PASSWORD, KEY_MAIL, KEY_PHONE},
                KEY_MAIL + "=?" + " AND " + KEY_PASSWORD + "=?",
                new String[]{String.valueOf(username), String.valueOf(password)}, null, null, null, null);

        boolean i = cursor.getCount() > 0;

        db.close();
        cursor.close();

        return i;

    }

    // Getting All Users
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
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
                user.setMail(cursor.getString(3));
                user.setPhone(cursor.getString(4));
                // Adding contact to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

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
        values.put(KEY_MAIL, user.getMail());
        values.put(KEY_PHONE, user.getPhone());

        // updating row
        int i = db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});

        db.close();

        return i;
    }

    // Deleting a user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    // Adds only once
    public void addOnce(User user) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                        KEY_USERNAME, KEY_PASSWORD}, KEY_USERNAME + "=?",
                new String[]{String.valueOf(user.getUser())}, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            user.setId(Integer.parseInt(cursor.getString(0)));
            updateUser(user);
        } else {
            addUser(user);
        }

        cursor.close();
        db.close();
    }
}
