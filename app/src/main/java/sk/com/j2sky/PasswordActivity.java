package sk.com.j2sky;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PasswordActivity extends Activity {

    Button login_button;
    EditText password_ediEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        login_button=(Button) findViewById(R.id.login_button2);
        password_ediEditText=findViewById(R.id.password_editText);
        login_button.setOnClickListener(view -> {
            String pwd=password_ediEditText.getText().toString();
                Intent i=new Intent(PasswordActivity.this, DashboardActivity.class);
                startActivity(i);
        });
    }
}