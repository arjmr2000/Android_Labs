package com.cst2335.cst2335mada0043;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
        import android.widget.Toast;

import com.cst2335.cst2335mada0043.R;
import com.google.android.material.snackbar.Snackbar;

        import static android.icu.text.DisplayContext.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    private Button button2;

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        EditText editText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        //  EditText passField=(EditText) findViewById(R.id.editView3);
        SharedPreferences prefs = getSharedPreferences("fileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("email", "");
        editText.setText(savedString);

        //getting the login button with ClickListner function
        Button loginBtn = (Button) findViewById(R.id.login);
        loginBtn.setOnClickListener( c -> {

            Intent goToProfile  = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile .putExtra("EMAIL", editText.getText().toString());
            startActivityForResult( goToProfile , 345);
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        EditText editText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        SharedPreferences prefs = getSharedPreferences("fileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String textEmailAddress=editText.getText().toString();
        editor.putString("email", textEmailAddress);
        editor.commit();
        editor.apply();
    }
}