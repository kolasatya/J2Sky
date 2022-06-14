package sk.com.j2sky;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

import static android.content.ContentValues.TAG;

public class ChangePasswordActivity extends AppCompatActivity {

    static Context context;
    static ChangePasswordActivity changePasswordActivity;
    Button changepassword_btn,changecalcel_btn;
    EditText editText_chg_password,editText_chg_re_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        context=getApplicationContext();
        changePasswordActivity=this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        changepassword_btn=  findViewById(R.id.button_change_pwd);
        changecalcel_btn=  findViewById(R.id.button_change_cancel);
        editText_chg_password=(EditText) findViewById(R.id.editText_change_password);
        editText_chg_re_password=(EditText) findViewById(R.id.editText_change_re_password);

        changepassword_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                Cognito cognito =Cognito.getCognito(getApplicationContext());
                cognito.changePassword(editText_chg_password.getText().toString(),editText_chg_re_password.getText().toString(),changepasswordCallback);
            }
        });

        changecalcel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
    }
    // Callback handler for confirmSignUp API
    public GenericHandler changepasswordCallback = new GenericHandler() {

        @Override
        public void onSuccess() {
            // User was successfully confirmed
            ShowAlert();
            Toast.makeText(getApplicationContext(),"User password changed", Toast.LENGTH_LONG).show();
            Log.d(TAG, "changepwd confirmed: ");

        }

        @Override
        public void onFailure(Exception exception) {
            // User confirmation failed. Check exception for the cause.
            exception.printStackTrace();
            Log.d(TAG, "changepwd failed: " + exception);
        }
    };
    private static void ShowAlert()
    {
        AlertDialog.Builder builder  = new AlertDialog.Builder(changePasswordActivity);
        builder.setMessage("You Have Successfully Changed Password.");
        builder.setTitle("Change password");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {
                Intent intent=new Intent(changePasswordActivity,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              //  intent.putExtra("useremail",email);
                context.startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}