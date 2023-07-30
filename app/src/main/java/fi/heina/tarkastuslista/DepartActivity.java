package fi.heina.tarkastuslista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class DepartActivity extends AppCompatActivity {

    private Button kulkupaiva, raportti;
    private EditText junanumeroInput;
    private EventsModel eventsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);

        DatabaseHelper databaseHelper = ApplicationClass.getDatabaseHelper();

        // piilotetaan ylävalikko (action bar)
        getSupportActionBar().hide();

        junanumeroInput = findViewById(R.id.junanumero);

        junanumeroInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                junanumeroInput.setRawInputType(Configuration.KEYBOARD_12KEY);
            }
        });

        // Asetetaan nykyinen päivämäärä Button-tekstiin
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());

        kulkupaiva = findViewById(R.id.kulkupaivaButton);
        kulkupaiva.setText(currentDate);

        kulkupaiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Button okButton = findViewById(R.id.ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOkButtonClicked(v);
            }
        });

        raportti = findViewById(R.id.raportti);
        raportti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Haetaan kaikki tietokannan rivit
                List<EventsModel> allEvents = databaseHelper.getAllEvents();

                // Luodaan StringBuilder tallentamaan rivit
                StringBuilder stringBuilder = new StringBuilder();

                // Käydään läpi kaikki rivit ja lisätään ne StringBuilderiin
                for (EventsModel event : allEvents) {
                    stringBuilder.append("Rivi: ").append(event.getId()).append("\n");
                    stringBuilder.append("Junanumero: ").append(event.getTrain_number()).append("\n");
                    stringBuilder.append("Lähtöpäivä: ").append(event.getDeparture_date()).append("\n");
                    stringBuilder.append("Virroitin: ").append(event.getVirroitin()).append("\n");
                    stringBuilder.append("Junapuhelin: ").append(event.getTrain_phone()).append("\n");
                    stringBuilder.append("Jarrut: ").append(event.getBrakes()).append("\n");
                    stringBuilder.append("JKV: ").append(event.getJkv()).append("\n");
                    stringBuilder.append("Kenttä: ").append(event.getPhone_app()).append("\n");
                    stringBuilder.append("Lähtölupa: ").append(event.getLahtolupa()).append("\n");
                    stringBuilder.append("SuuntaValotPeilit: ").append(event.getSuuntavalotpeilit()).append("\n");
                    stringBuilder.append("\n");
                }

                // Luodaan AlertDialog ponnahdusikkuna ja asetetaan sisältö
                AlertDialog.Builder builder = new AlertDialog.Builder(DepartActivity.this);
                builder.setTitle("Tietokannan rivit");
                builder.setMessage(stringBuilder.toString());
                builder.setPositiveButton("OK", null); // Aseta "OK"-painike
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                // Määritä tiedoston nimi ja polku
                String fileName = "tietokanta.pdf";
                File file = new File(getExternalFilesDir(null), fileName);

                // Luo uusi PDF-dokumentti
                Document document = new Document(PageSize.A4);

                try {
                    // Luo PDF-tiedostonkirjoittaja
                    PdfWriter.getInstance(document, new FileOutputStream(file));

                    // Avaa dokumentti
                    document.open();

                    // Luo fontti
                    Font font = FontFactory.getFont(FontFactory.COURIER, 12);

                    // Lisää tietokannan rivit dokumenttiin
                    for (EventsModel event : allEvents) {
                        document.add(new Paragraph("Rivi: " + event.getId(), font));
                        document.add(new Paragraph("Junanumero: " + event.getTrain_number(), font));
                        document.add(new Paragraph("Lähtöpäivä: " + event.getDeparture_date(), font));
                        document.add(new Paragraph("Virroitin: " + event.getVirroitin(), font));
                        document.add(new Paragraph("Junapuhelin: " + event.getTrain_phone(), font));
                        document.add(new Paragraph("Jarrut: " + event.getBrakes(), font));
                        document.add(new Paragraph("JKV: " + event.getJkv(), font));
                        document.add(new Paragraph("Kenttä: " + event.getPhone_app(), font));
                        document.add(new Paragraph("Lähtölupa: " + event.getLahtolupa(), font));
                        document.add(new Paragraph("SuuntaValotPeilit: " + event.getSuuntavalotpeilit(), font));
                        document.add(new Paragraph("\n", font));
                    }

                    // Sulje dokumentti
                    document.close();

                    // Tulosta tiedoston sijainti
                    Log.d("MainActivity", "Tiedosto tallennettu: " + file.getAbsolutePath());

                    // Näytä viesti käyttäjälle
                    Toast.makeText(DepartActivity.this, "Tietokanta tallennettu PDF-tiedostoon", Toast.LENGTH_SHORT).show();
                } catch (DocumentException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onOkButtonClicked(View view) {

        String junanumero = junanumeroInput.getText().toString().trim();
        if (junanumero.isEmpty()) {
            View parentLayout = findViewById(android.R.id.content);
            String message = "Täytä junan numero";
            int duration = Snackbar.LENGTH_SHORT;

            Snackbar snackbar = Snackbar.make(parentLayout, message, duration);
            snackbar.show();
        } else {
            // Haetaan junan numero EditText-kentästä
            int trainNumber = Integer.parseInt(junanumeroInput.getText().toString());

            // Haetaan lähtöpäivä Napin tekstistä
            String departureDateString = kulkupaiva.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date departureDate;
            try {
                departureDate = dateFormat.parse(departureDateString);
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }

            // Luodaan uusi EventsModel-olio junan numerolla ja lähtöpäivällä
            eventsModel = new EventsModel(0, trainNumber, departureDate, null, null, null, null, null, null, null);

            // Siirrytään CheckActivityyn
            Intent intent = new Intent(DepartActivity.this, CheckActivity.class);
            intent.putExtra("eventsModel", eventsModel); // Siirretään eventsModel Intentin mukana CheckActivityyn
            startActivity(intent);

            // Tyhjätään junanumerotekstikenttä, jotta se on tyhjä takaisin palattaessa
            junanumeroInput.setText("");
            finish();
        }

    }

    private void showDatePickerDialog() {
        // asetetaan kalenteri nykyiseen päivämäärään
        Calendar calendar = Calendar.getInstance();

        // luodaan CustomDatePickerDialog kuuntelijalla, joka asettaa valitun päivämäärän EditText-kenttään
        CustomDatePickerDialog datePickerDialog = new CustomDatePickerDialog(DepartActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                        String selectedDate = dateFormat.format(calendar.getTime());

                        kulkupaiva.setText(selectedDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DepartActivity.this, MainViewActivity.class);
        startActivity(intent);
        finish(); // Sulkee nykyisen aktiviteetin, jotta et voi palata siihen takaisin
    }
}