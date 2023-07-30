package fi.heina.tarkastuslista;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;

public class CustomDatePickerDialog extends DatePickerDialog {

    public CustomDatePickerDialog(Context context, OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
        fixFirstDayOfWeek();
    }

    private void fixFirstDayOfWeek() {
        DatePicker datePicker = getDatePicker();
        if (datePicker != null) {
            // Aseta viikon ensimmäinen päivä maanantaiksi
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            datePicker.setFirstDayOfWeek(calendar.getFirstDayOfWeek());
        }
    }
}
