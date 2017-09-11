package com.reebrandogmail.trackmycar.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.reebrandogmail.trackmycar.model.History;
import com.reebrandogmail.trackmycar.model.User;
import com.reebrandogmail.trackmycar.model.Vehicle;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by renan.brando on 27/07/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    
    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static final String DATABASE_NAME = "trackMyCar";
    // Users table name
    private static final String TABLE_USERS = "users";
    // Vehicles table name
    private static final String TABLE_VEHICLES = "vehicles";
    // History table name
    private static final String TABLE_HISTORY = "history";
    // Users table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";
    // Vehicles table columns names
    private static final String KEY_VHC_ID = "id";
    private static final String KEY_VHC_BRAND = "brand";
    private static final String KEY_VHC_MODEL = "model";
    private static final String KEY_VHC_PLATE = "plate";
    private static final String KEY_VHC_YEAR = "year";
    private static final String KEY_VHC_COLOR = "color";
    // History table columns names
    private static String KEY_HIST_ID = "id";
    private static String KEY_HIST_PLACE = "place";
    private static String KEY_HIST_LONG = "longitude";
    private static String KEY_HIST_LAT = "latitude";
    private static String KEY_HIST_TIME = "timestamp";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_MAIL + " TEXT,"
                + KEY_PHONE + " TEXT" + ")";

        String CREATE_VEHICLES_TABLE = "CREATE TABLE " + TABLE_VEHICLES + "("
                + KEY_VHC_ID + " INTEGER PRIMARY KEY,"
                + KEY_VHC_BRAND + " TEXT,"
                + KEY_VHC_MODEL + " TEXT,"
                + KEY_VHC_PLATE + " TEXT,"
                + KEY_VHC_YEAR + " TEXT,"
                + KEY_VHC_COLOR + " TEXT" + ")";

        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_HIST_ID + " INTEGER PRIMARY KEY,"
                + KEY_HIST_PLACE + " TEXT,"
                + KEY_HIST_LAT + " REAL,"
                + KEY_HIST_LONG + " REAL,"
                + KEY_HIST_TIME + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_VEHICLES_TABLE);
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
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

    // Adding new user
    public void addVehicle(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VHC_BRAND, vehicle.getBrand()); // Vehicle brand
        values.put(KEY_VHC_MODEL, vehicle.getModel()); // Vehicle model
        values.put(KEY_VHC_PLATE, vehicle.getLicensePlate()); // Vehicle license plate
        values.put(KEY_VHC_YEAR, vehicle.getYear()); // Vehicle year
        values.put(KEY_VHC_COLOR, vehicle.getColor()); // Vehicle color


        // Inserting Row
        db.insert(TABLE_VEHICLES, null, values);
        db.close(); // Closing database connection
    }

    // Adding new user
    public void addHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HIST_PLACE, history.getMapsURL()); // History place
        values.put(KEY_HIST_LAT, history.getLatitude()); // History latitude
        values.put(KEY_HIST_LONG, history.getLongitude()); // History longitude
        values.put(KEY_HIST_TIME, history.getTimestamp()); // History timestamp

        // Inserting Row
        db.insert(TABLE_HISTORY, null, values);
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

    // Getting one vehicle
    public Vehicle getVehicle(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VEHICLES, new String[]{KEY_VHC_ID,
                        KEY_VHC_BRAND, KEY_VHC_MODEL, KEY_VHC_PLATE, KEY_VHC_YEAR, KEY_VHC_COLOR}, KEY_VHC_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(Integer.parseInt(cursor.getString(0)));
        vehicle.setBrand(cursor.getString(1));
        vehicle.setModel(cursor.getString(2));
        vehicle.setLicensePlate(cursor.getString(3));
        vehicle.setYear(cursor.getString(4));
        vehicle.setColor(cursor.getString(5));

        db.close();
        cursor.close();

        // return vehicle
        return vehicle;
    }

    // Getting one history
    public History getHistory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HISTORY, new String[]{KEY_HIST_ID,
                        KEY_HIST_PLACE, KEY_HIST_LAT, KEY_HIST_LONG, KEY_HIST_TIME}, KEY_HIST_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        History history = new History();
        history.setId(Integer.parseInt(cursor.getString(0)));
        history.setMapsURL(cursor.getString(1));
        history.setLatitude(Float.parseFloat(cursor.getString(2)));
        history.setLongitude(Float.parseFloat(cursor.getString(3)));
        history.setTimestamp(cursor.getString(4));

        db.close();
        cursor.close();

        // return history
        return history;
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

        // return users list
        return userList;
    }

    // Getting All Vehicles
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehiclesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_VEHICLES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(Integer.parseInt(cursor.getString(0)));
                vehicle.setBrand(cursor.getString(1));
                vehicle.setModel(cursor.getString(2));
                vehicle.setLicensePlate(cursor.getString(3));
                vehicle.setYear(cursor.getString(4));
                vehicle.setColor(cursor.getString(5));
                // Adding contact to list
                vehiclesList.add(vehicle);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        // return vehicles list
        return vehiclesList;
    }

    // Getting All Vehicles
    public List<History> getAllHistories() {
        List<History> historiesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History history = new History();
                history.setId(Integer.parseInt(cursor.getString(0)));
                history.setMapsURL(cursor.getString(1));
                history.setLatitude(Float.parseFloat(cursor.getString(2)));
                history.setLongitude(Float.parseFloat(cursor.getString(3)));
                history.setTimestamp(cursor.getString(4));
                // Adding contact to list
                historiesList.add(history);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        // return histories list
        return historiesList;
    }

    // Getting Users Count
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

    // Getting Vehicles Count
    public int getVehiclesCount() {
        int count;
        String countQuery = "SELECT * FROM " + TABLE_VEHICLES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


    // Getting Vehicles Count
    public int getHistoriesCount() {
        int count;
        String countQuery = "SELECT * FROM " + TABLE_HISTORY;
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

    // Updating a vehicle
    public int updateVehicle(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VHC_BRAND, vehicle.getBrand()); // Vehicle brand
        values.put(KEY_VHC_MODEL, vehicle.getModel()); // Vehicle model
        values.put(KEY_VHC_PLATE, vehicle.getLicensePlate()); // Vehicle license plate
        values.put(KEY_VHC_YEAR, vehicle.getYear()); // Vehicle year
        values.put(KEY_VHC_COLOR, vehicle.getColor()); // Vehicle color

        // updating row
        int i = db.update(TABLE_VEHICLES, values, KEY_VHC_ID + " = ?",
                new String[]{String.valueOf(vehicle.getId())});

        db.close();

        return i;
    }

    // Updating a history
    public int updateHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HIST_PLACE, history.getMapsURL()); // History place
        values.put(KEY_HIST_LAT, history.getLatitude()); // History latitude
        values.put(KEY_HIST_LONG, history.getLongitude()); // History longitude
        values.put(KEY_HIST_TIME, history.getTimestamp()); // History timestamp

        // updating row
        int i = db.update(TABLE_HISTORY, values, KEY_HIST_ID + " = ?",
                new String[]{String.valueOf(history.getId())});

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

    // Deleting a vehicle
    public void deleteVehicle(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VEHICLES, KEY_VHC_ID + " = ?",
                new String[]{String.valueOf(vehicle.getId())});
        db.close();
    }

    // Deleting a history
    public void deleteHitory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VEHICLES, KEY_HIST_ID + " = ?",
                new String[]{String.valueOf(history.getId())});
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

    // Adds only once
    public void addOnceBySocial(User user) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                        KEY_USERNAME, KEY_PASSWORD}, KEY_MAIL+"=?",
                new String[]{String.valueOf(user.getMail())}, null, null, null, null);
        if (!(cursor.getCount() > 0)) {
            addUser(user);
        }

        cursor.close();
        db.close();
    }
}

