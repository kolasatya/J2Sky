package sk.com.j2sky;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    Button login_button;
    EditText email_ediEditText;
    static  Context context;
    static LoginActivity loginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity=this;
        context=getApplicationContext();
        login_button=(Button) findViewById(R.id.login_button);
        email_ediEditText=findViewById(R.id.email_editText);
        login_button.setOnClickListener(view -> {
            String email=email_ediEditText.getText().toString();
            //ShowAlert(getApplicationContext());
            if(email.equalsIgnoreCase("satyakola777@gmail.com")){
                Intent i=new Intent(LoginActivity.this, PasswordActivity.class);
                i.putExtra("useremail",email);
                startActivity(i);
            }else
            {
                Intent i=new Intent(LoginActivity.this, SignupActivity.class);
                i.putExtra("useremail",email);
                startActivity(i);
            }

        });
    }
    private static void ShowAlert(Context context)
    {
        AlertDialog.Builder builder  = new AlertDialog.Builder(loginActivity);
        builder.setMessage("You Have Successfully Registered.Please login.");
        builder.setTitle("SignUp");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {
                Intent intent=new Intent(loginActivity,PasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // intent.putExtra("useremail",email);
                context.startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}