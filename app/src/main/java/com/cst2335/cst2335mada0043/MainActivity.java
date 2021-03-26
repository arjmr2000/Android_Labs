package com.cst2335.cst2335mada0043;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;




public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
System.out.println("Text");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        EditText emailField = (EditText) findViewById(R.id.editText);
        //  EditText passField=(EditText) findViewById(R.id.editView3);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedString = preferences.getString("email", "");
        emailField.setText(savedString);

        //getting the login button with ClickListner function
        Button loginBtn = (Button) findViewById(R.id.button1);
        loginBtn.setOnClickListener( c -> {

            Intent profilePage = new Intent(MainActivity.this, activity_profile.class);
            profilePage.putExtra("EMAIL", emailField.getText().toString().trim());
            startActivityForResult( profilePage, 345);
        });
    }

    @Override
   protected void onPause() {
        super.onPause();
        EditText emailField = (EditText) findViewById(R.id.editText);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String textEmailAddress=emailField.getText().toString().trim();
        editor.putString("email", textEmailAddress);
        editor.commit();
        editor.apply();

    }
}