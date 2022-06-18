package sk.com.j2sky;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordActivity extends Activity {

    Button login_button;
    TextView forgot_pwd_bth;
    static EditText password_ediEditText;
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
        forgot_pwd_bth=(TextView) findViewById(R.id.button_forgotpwd);
        forgot_pwd_bth.setOnClickListener(view -> {
            Intent i = new Intent(PasswordActivity.this, ResetPasswordActivity.class);
            i.putExtra("useremail", email);
            startActivity(i);
        });
        password_ediEditText=findViewById(R.id.password_editText);
        login_button.setOnClickListener(view -> {
            String pwd=password_ediEditText.getText().toString();
            Cognito cognito=Cognito.getCognito(getApplicationContext());
            cognito.userLogin(email,pwd);

        });
    }
public static void successCallback(Context context) {
    Intent i = new Intent(context, DashboardActivity.class);
    i.putExtra("useremail", email);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);
}
public static void failureCallback()
{
    password_ediEditText.setError("Invalid user/password");
}
}