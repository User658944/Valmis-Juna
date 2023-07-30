package fi.heina.tarkastuslista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckActivity extends AppCompatActivity {

    private CheckBox checkboxVirroitin;
    private TextView eventTimeVirroitin;
    private CheckBox checkboxJunapuhelin;
    private TextView eventTimeJunapuhelin;
    private CheckBox checkboxJarrut;
    private TextView eventTimeJarrut;
    private CheckBox checkboxJkv;
    private TextView eventTimeJkv;
    private CheckBox checkboxMobiilipuhelin;
    private TextView eventTimeMobiilipuhelin;
    private CheckBox checkboxLahtolupa;
    private TextView eventTimeLahtolupa;
    private CheckBox checkboxSvp;
    private TextView eventTimeSvp;
    private View virroitinView;
    private View junapuhelinView;
    private View jarrutView;
    private View jkvView;
    private View mobiilipuhelinView;
    private View lahtolupaView;
    private View svpView;
    private TextView junanroHeadline;
    private TextView pvmHeadline;
    private Button readyButton;
    private EventsModel eventsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        DatabaseHelper databaseHelper = ApplicationClass.getDatabaseHelper();

        // piilotetaan ylävalikko (action bar)
        getSupportActionBar().hide();

        checkboxVirroitin = findViewById(R.id.checkboxVirroitin);
        eventTimeVirroitin = findViewById(R.id.eventTimeVirroitin);
        checkboxJunapuhelin = findViewById(R.id.checkboxJunapuhelin);
        eventTimeJunapuhelin = findViewById(R.id.eventTimeJunapuhelin);
        checkboxJarrut = findViewById(R.id.checkboxJarrut);
        eventTimeJarrut = findViewById(R.id.eventTimeJarrut);
        checkboxJkv = findViewById(R.id.checkboxJkv);
        eventTimeJkv = findViewById(R.id.eventTimeJkv);
        checkboxMobiilipuhelin = findViewById(R.id.checkboxMobiilipuhelin);
        eventTimeMobiilipuhelin = findViewById(R.id.eventTimeMobiilipuhelin);
        checkboxLahtolupa = findViewById(R.id.checkboxLahtolupa);
        eventTimeLahtolupa = findViewById(R.id.eventTimeLahtolupa);
        checkboxSvp = findViewById(R.id.checkboxSvp);
        eventTimeSvp = findViewById(R.id.eventTimeSvp);
        readyButton = findViewById(R.id.ready);
        virroitinView = findViewById(R.id.virroitinView);
        junapuhelinView = findViewById(R.id.junapuhelinView);
        jarrutView = findViewById(R.id.jarrutView);
        jkvView = findViewById(R.id.jkvView);
        mobiilipuhelinView = findViewById(R.id.mobiilipuhelinView);
        lahtolupaView = findViewById(R.id.lahtolupaView);
        svpView = findViewById(R.id.svpView);
        junanroHeadline = findViewById(R.id.junanroHeadline);
        pvmHeadline = findViewById(R.id.pvmHeadline);


        // Haetaan eventsModel Intentin mukana
        Intent intent = getIntent();
        eventsModel = intent.getParcelableExtra("eventsModel");

        // Asetetaan junanumero ja päivämäärä ruudun yläreunaan
        junanroHeadline.setText(Integer.toString(eventsModel.getTrain_number()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = dateFormat.format(eventsModel.getDeparture_date());
        pvmHeadline.setText(formattedDate);

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseHelper.insertEvent(eventsModel);

                // Siirrytään CheckedActivityyn
                Intent intent = new Intent(CheckActivity.this, CheckedActivity.class);
                intent.putExtra("eventsModel", eventsModel); // Siirretään eventsModel Intentin mukana CheckedActivityyn
                startActivity(intent);
                finish();
            }
        });

        checkboxVirroitin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String currentDateAndTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    eventTimeVirroitin.setText(currentDateAndTime);
                    eventsModel.setVirroitin(new Date());
                    virroitinView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                } else {
                    eventTimeVirroitin.setText("");
                    eventsModel.setVirroitin(null);
                    virroitinView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                }
                checkCheckBoxes();
            }
        });

        checkboxJunapuhelin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String currentDateAndTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    eventTimeJunapuhelin.setText(currentDateAndTime);
                    eventsModel.setTrain_phone(new Date());
                    junapuhelinView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                } else {
                    eventTimeJunapuhelin.setText("");
                    eventsModel.setTrain_phone(null);
                    junapuhelinView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                }
                checkCheckBoxes();
            }
        });

        checkboxJarrut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String currentDateAndTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    eventTimeJarrut.setText(currentDateAndTime);
                    eventsModel.setBrakes(new Date());
                    jarrutView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                } else {
                    eventTimeJarrut.setText("");
                    eventsModel.setBrakes(null);
                    jarrutView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                }
                checkCheckBoxes();
            }
        });

        checkboxJkv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String currentDateAndTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    eventTimeJkv.setText(currentDateAndTime);
                    eventsModel.setJkv(new Date());
                    jkvView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                } else {
                    eventTimeJkv.setText("");
                    eventsModel.setJkv(null);
                    jkvView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                }
                checkCheckBoxes();
            }
        });

        checkboxMobiilipuhelin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String currentDateAndTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    eventTimeMobiilipuhelin.setText(currentDateAndTime);
                    eventsModel.setPhone_app(new Date());
                    mobiilipuhelinView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                } else {
                    eventTimeMobiilipuhelin.setText("");
                    eventsModel.setPhone_app(null);
                    mobiilipuhelinView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                }
                checkCheckBoxes();
            }
        });

        checkboxLahtolupa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String currentDateAndTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    eventTimeLahtolupa.setText(currentDateAndTime);
                    eventsModel.setLahtolupa(new Date());
                    lahtolupaView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                } else {
                    eventTimeLahtolupa.setText("");
                    eventsModel.setLahtolupa(null);
                    lahtolupaView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                }
                checkCheckBoxes();
            }
        });

        checkboxSvp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String currentDateAndTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    eventTimeSvp.setText(currentDateAndTime);
                    eventsModel.setSuuntavalotpeilit(new Date());
                    svpView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_700));
                } else {
                    eventTimeSvp.setText("");
                    eventsModel.setSuuntavalotpeilit(null);
                    svpView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                }
                checkCheckBoxes();
            }
        });

    }

    public void checkCheckBoxes() {
        if (checkboxVirroitin.isChecked() && checkboxJunapuhelin.isChecked() && checkboxJarrut.isChecked() && checkboxJkv.isChecked() && checkboxMobiilipuhelin.isChecked() && checkboxLahtolupa.isChecked() && checkboxSvp.isChecked()) {
            readyButton.setVisibility(View.VISIBLE);
        } else {
            readyButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish(); // Sulkee CheckActivityn ja palaa MainActivityyn kun painetaan laitteen takaisin toimintoa
    }
}