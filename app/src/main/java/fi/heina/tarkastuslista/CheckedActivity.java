package fi.heina.tarkastuslista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CheckedActivity extends AppCompatActivity {

    private EventsModel eventsModel;
    private TextView virroitinTime;
    private TextView junapuhelinTime;
    private TextView jarrutTime;
    private TextView jkvTime;
    private TextView mobiilipuhelinTime;
    private TextView lahtolupaTime;
    private TextView svpTime;
    private TextView junaTitle;
    private TextView lahtopaivaTitle;
    private SimpleDateFormat timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked);

        // Haetaan eventsModel Intentin mukana
        Intent intent = getIntent();
        eventsModel = intent.getParcelableExtra("eventsModel");

        virroitinTime = findViewById(R.id.virroitinTime);
        junapuhelinTime = findViewById(R.id.junapuhelinTime);
        jarrutTime = findViewById(R.id.jarrutTime);
        jkvTime = findViewById(R.id.jkvTime);
        mobiilipuhelinTime = findViewById(R.id.mobiilipuhelinTime);
        lahtolupaTime = findViewById(R.id.lahtolupaTime);
        svpTime = findViewById(R.id.svpTime);
        junaTitle = findViewById(R.id.junaTitle);
        lahtopaivaTitle = findViewById(R.id.lahtopaivaTitle);

        // Asetetaan junanumero ja päivämäärä ruudun yläreunaan
        junaTitle.setText(Integer.toString(eventsModel.getTrain_number()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = dateFormat.format(eventsModel.getDeparture_date());
        lahtopaivaTitle.setText(formattedDate);

        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // Asetetaan tarkastusajat Textview kenttiin
        virroitinTime.setText(timeFormat.format(eventsModel.getVirroitin()));
        junapuhelinTime.setText(timeFormat.format(eventsModel.getTrain_phone()));
        jarrutTime.setText(timeFormat.format(eventsModel.getBrakes()));
        jkvTime.setText(timeFormat.format(eventsModel.getJkv()));
        mobiilipuhelinTime.setText(timeFormat.format(eventsModel.getPhone_app()));
        lahtolupaTime.setText(timeFormat.format(eventsModel.getLahtolupa()));
        svpTime.setText(timeFormat.format(eventsModel.getSuuntavalotpeilit()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            // Siirry takaisin MainViewActivityyn
            Intent intent = new Intent(CheckedActivity.this, MainViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            // Tyhjennä eventsModel-olion arvot
            eventsModel.setTrain_number(0);
            eventsModel.setDeparture_date(null);
            eventsModel.setVirroitin(null);
            eventsModel.setTrain_phone(null);
            eventsModel.setBrakes(null);
            eventsModel.setJkv(null);
            eventsModel.setPhone_app(null);
            eventsModel.setLahtolupa(null);
            eventsModel.setSuuntavalotpeilit(null);
            finish();
            return true;
        } else if (id == R.id.action_exit) {
            moveTaskToBack(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CheckedActivity.this, MainViewActivity.class);
        startActivity(intent);
        finish(); // Sulkee nykyisen aktiviteetin, jotta et voi palata siihen takaisin
    }

}