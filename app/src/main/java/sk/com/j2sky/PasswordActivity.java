package sk.com.j2sky;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PasswordActivity extends Activity {

    Button login_button;
    EditText password_ediEditText;
    static String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            email = bundle.getString("useremail");
        }

        login_button=(Button) findViewById(R.id.login_button2);
        password_ediEditText=findViewById(R.id.password_editText);
        login_button.setOnClickListener(view -> {
            String pwd=password_ediEditText.getText().toString();
            Cognito cognito=new Cognito(getApplicationContext());
            cognito.userLogin(email,pwd);

        });
    }
public static void successCallback(Context context) {
    Intent i = new Intent(context, DashboardActivity.class);
    i.putExtra("useremail", email);
    context.startActivity(i);
}
}