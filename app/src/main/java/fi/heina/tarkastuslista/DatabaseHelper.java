package fi.heina.tarkastuslista;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "checkregister";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "checkevents";
    private static final String ID = "id";
    private static final String TRAIN_NUMBER = "train_number";
    private static final String DEPARTURE_DATE = "departure_date";
    private static final String VIRROITIN = "virroitin";
    private static final String TRAIN_PHONE = "train_phone";
    private static final String BRAKES = "brakes";
    private static final String JKV = "jkv";
    private static final String PHONE_APP = "phone_app";
    private static final String SUUNTAVALOTPEILIT = "suuntavalotpeilit";
    private static final String LAHTOLUPA = "lahtolupa";

    private static final String USER_TABLE_NAME = "user";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "username";

    private static final String USER_EMAIL = "useremail";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTaskTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TRAIN_NUMBER + " INTEGER,"
                + DEPARTURE_DATE + " DATE,"
                + VIRROITIN + " DATETIME,"
                + TRAIN_PHONE + " DATETIME,"
                + BRAKES + " DATETIME,"
                + JKV + " DATETIME,"
                + PHONE_APP + " DATETIME,"
                + SUUNTAVALOTPEILIT + " DATETIME,"
                + LAHTOLUPA + " DATETIME"
                + ")";
        db.execSQL(createTaskTableQuery);

        String createUserTableQuery = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_NAME + " INTEGER,"
                + USER_EMAIL + " TEXT"
                + ")";
        db.execSQL(createUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @SuppressLint("Range")
    public String getUserName() {
        String username = null;
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT " + USER_NAME + " FROM " + USER_TABLE_NAME + " WHERE " + USER_ID + " = 1";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(USER_NAME));
        }

        cursor.close();
        db.close();

        return username;
    }

    @SuppressLint("Range")
    public String getUserEmail() {
        String useremail = null;
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT " + USER_EMAIL + " FROM " + USER_TABLE_NAME + " WHERE " + USER_ID + " = 1";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            useremail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
        }

        cursor.close();
        db.close();

        return useremail;
    }

    public void setUserData(String username, String useremail) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, username);
        values.put(USER_EMAIL, useremail);

        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    public void insertEvent(EventsModel event) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TRAIN_NUMBER, event.getTrain_number());
        values.put(DEPARTURE_DATE, event.getDeparture_date().getTime());
        values.put(VIRROITIN, event.getVirroitin().getTime());
        values.put(TRAIN_PHONE, event.getTrain_phone().getTime());
        values.put(BRAKES, event.getBrakes().getTime());
        values.put(JKV, event.getJkv().getTime());
        values.put(PHONE_APP, event.getPhone_app().getTime());
        values.put(SUUNTAVALOTPEILIT, event.getSuuntavalotpeilit().getTime());
        values.put(LAHTOLUPA, event.getLahtolupa().getTime());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<EventsModel> getAllEvents() {
        List<EventsModel> eventsList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(ID));
                @SuppressLint("Range") int trainNumber = cursor.getInt(cursor.getColumnIndex(TRAIN_NUMBER));
                @SuppressLint("Range") long departureDateMillis = cursor.getLong(cursor.getColumnIndex(DEPARTURE_DATE));
                @SuppressLint("Range") long virroitinMillis = cursor.getLong(cursor.getColumnIndex(VIRROITIN));
                @SuppressLint("Range") long trainPhoneMillis = cursor.getLong(cursor.getColumnIndex(TRAIN_PHONE));
                @SuppressLint("Range") long brakesMillis = cursor.getLong(cursor.getColumnIndex(BRAKES));
                @SuppressLint("Range") long jkvMillis = cursor.getLong(cursor.getColumnIndex(JKV));
                @SuppressLint("Range") long phoneAppMillis = cursor.getLong(cursor.getColumnIndex(PHONE_APP));
                @SuppressLint("Range") long suuntavalotpeilitMillis = cursor.getLong(cursor.getColumnIndex(SUUNTAVALOTPEILIT));
                @SuppressLint("Range") long lahtolupaMillis = cursor.getLong(cursor.getColumnIndex(LAHTOLUPA));

                // Muunna ajan tiedot Date-objekteiksi
                Date departureDate = new Date(departureDateMillis);
                Date virroitin = new Date(virroitinMillis);
                Date trainPhone = new Date(trainPhoneMillis);
                Date brakes = new Date(brakesMillis);
                Date jkv = new Date(jkvMillis);
                Date phoneApp = new Date(phoneAppMillis);
                Date suuntavalotpeilit = new Date(suuntavalotpeilitMillis);
                Date lahtolupa = new Date(lahtolupaMillis);

                // Luo EventsModel-objekti ja lisää se listaan
                EventsModel event = new EventsModel(id, trainNumber, departureDate, virroitin, trainPhone, brakes, jkv, phoneApp, suuntavalotpeilit, lahtolupa);
                eventsList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return eventsList;
    }


}
