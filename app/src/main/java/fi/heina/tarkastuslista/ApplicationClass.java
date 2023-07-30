package fi.heina.tarkastuslista;

import android.app.Application;

public class ApplicationClass extends Application {
    private static DatabaseHelper databaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseHelper = new DatabaseHelper(this);
    }

    public static DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }
}
