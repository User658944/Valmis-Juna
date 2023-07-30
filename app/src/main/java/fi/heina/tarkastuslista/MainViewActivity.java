package fi.heina.tarkastuslista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class MainViewActivity extends AppCompatActivity {
    private String userName;
    private String userEmail;
    private TextView userNameDisplay;
    private CardView departTrain;

    private User userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        // piilotetaan ylävalikko (action bar)
        getSupportActionBar().hide();

        userNameDisplay = findViewById(R.id.username);

        // Vastaanota userModel-objekti intentistä
        userModel = (User) getIntent().getSerializableExtra("UserModel");


        DatabaseHelper databaseHelper = ApplicationClass.getDatabaseHelper();
        userName = databaseHelper.getUserName();
        userEmail = databaseHelper.getUserEmail();

        userModel = new User(userName, userEmail);

        if (TextUtils.isEmpty(userName)) {
            Intent intent = new Intent(MainViewActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            userNameDisplay.setText(userName);
        }

        departTrain = findViewById(R.id.departTrain);
        departTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameDisplay.getText().toString();
                Intent intent = new Intent(MainViewActivity.this, DepartActivity.class);
                intent.putExtra("userName", username);
                startActivity(intent);
            }
        });





    }
}