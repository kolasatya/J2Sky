package sk.com.j2sky;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    Button login_button;
    EditText email_ediEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_button=(Button) findViewById(R.id.login_button);
        email_ediEditText=findViewById(R.id.email_editText);
        login_button.setOnClickListener(view -> {
            String email=email_ediEditText.getText().toString();
            if(email.equalsIgnoreCase("satyakola777@gmail.com")){
                Intent i=new Intent(LoginActivity.this, PasswordActivity.class);
                startActivity(i);
            }else
            {
                Intent i=new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }

        });
    }
}