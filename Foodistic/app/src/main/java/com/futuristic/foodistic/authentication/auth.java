
package com.futuristic.foodistic.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.futuristic.foodistic.R;
import com.futuristic.foodistic.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class auth extends AppCompatActivity {
private FirebaseUser currentUser;
private TextView textView,textView2;
    private EditText editTextMobile;
    private Button btn;
    private FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        editTextMobile = findViewById(R.id.editTextMobile);
        mauth = FirebaseAuth.getInstance();
        currentUser=mauth.getCurrentUser();
        btn=(Button) findViewById(R.id.buttonContinue);
        textView=(TextView)findViewById(R.id.textview);
        textView2=(TextView)findViewById(R.id.textview3);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/PunjabiVirsa.ttf");
        textView.setTypeface(customFont);
        textView2.setTypeface(customFont);


        if(currentUser !=null){
            Intent intent = new Intent(auth.this, MainActivity.class);
            startActivity(intent);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(auth.this, PhoneNumberVerification.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });
    }
}
