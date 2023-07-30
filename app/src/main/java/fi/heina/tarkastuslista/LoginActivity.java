package fi.heina.tarkastuslista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private User userModel;
    private EditText kuljettajaTunnus;

    private EditText kuljettajaEmail;
    private Button kirjauduButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseHelper databaseHelper = ApplicationClass.getDatabaseHelper();

        kuljettajaTunnus = findViewById(R.id.kuljettajatunnus);
        kuljettajaEmail = findViewById(R.id.kuljettajaemail);
        kirjauduButton = findViewById(R.id.kirjaudu);

        kirjauduButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kuljettaja = kuljettajaTunnus.getText().toString();
                String email = kuljettajaEmail.getText().toString();

                if (kuljettaja.isEmpty() || email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Täytä tunnus ja sähköposti", Toast.LENGTH_SHORT).show();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Ovatko käyttäjätunnus ja sähköposti oikein?\n\n" + kuljettaja + "\n" + email)
                            .setPositiveButton("Kyllä", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    // lisätään kuljettaja olioon
                                    userModel = new User(kuljettaja, email);

                                    // lisätään kuljettaja tietokantaan
                                    databaseHelper.setUserData(kuljettaja, email);

                                    // Siirrytään CheckedActivityyn
                                    Intent intent = new Intent(LoginActivity.this, MainViewActivity.class);
                                    intent.putExtra("UserModel", userModel);

                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("Ei", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Ei-painiketta painettiin, voit suorittaa tarvittaessa toisen toiminnon
                                }
                            });

                    // Luo AlertDialog-objekti ja näytä se
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
    }
}