package sk.com.j2sky;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;

import static android.content.ContentValues.TAG;

public class ResetPasswordActivity extends Activity {

    Button register_button,cancel_button,otp_button;
    EditText editText_otp,editText_password,editText_re_password,editText_rest_email;
    String email="";
    ResetPasswordActivity resetPasswordActivity;
    Context context;
    private ForgotPasswordContinuation forgotPasswordContinuation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();
        resetPasswordActivity=this;
        setContentView(R.layout.activity_reset_password);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            email = bundle.getString("useremail");
        }

        Log.d("ResetPasswordActivity","email"+email);
        register_button = (Button) findViewById(R.id.button_reset_registeruser);
        cancel_button = (Button) findViewById(R.id.button_reset_cancel);
        editText_otp=(EditText) findViewById(R.id.editText_reset_otp);
        editText_password=(EditText) findViewById(R.id.editText_reset_password);
        editText_re_password=(EditText) findViewById(R.id.editText_reset_re_password);
        editText_rest_email=(EditText) findViewById(R.id.editText_rest_email);
        editText_rest_email.setText(email);
        editText_rest_email.setEnabled(false);
        otp_button = (Button) findViewById(R.id.button_reset_otp);
        otp_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Forgot Password: OTP button click");
                Cognito cognito=Cognito.getCognito(getApplicationContext());
                cognito.forgotPassword(email,forgotPasswordHandler);
            }
        });
        //registration
        register_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
               // Cognito cognito =Cognito.getCognito(getApplicationContext());
                forgotPasswordContinuation.setPassword(editText_password.getText().toString());
                forgotPasswordContinuation.setVerificationCode(editText_otp.getText().toString());
                forgotPasswordContinuation.continueTask();
                ShowAlert();

               // cognito.confirmResetPassword(editText_otp.getText().toString(),editText_password.getText().toString(),forgotPasswordHandler);
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void getForgotPasswordCode(ForgotPasswordContinuation forgotPasswordContinuation) {

        this.forgotPasswordContinuation = forgotPasswordContinuation;
    }
    ForgotPasswordHandler forgotPasswordHandler=new ForgotPasswordHandler() {
        @Override
        public void onSuccess() {

            Log.d(TAG, "Forgot Password: onSuccess");
        }

        @Override
        public void getResetCode(ForgotPasswordContinuation continuation) {
            Log.d(TAG, "Forgot Password: onSuccess");

            getForgotPasswordCode(continuation);
            //continuation.setPassword("");
            //continuation.setVerificationCode("");
        }

        @Override
        public void onFailure(Exception exception) {
            Log.d(TAG, "Forgot Password: onFailure"+exception);
            exception.printStackTrace();
            Log.d(TAG, "Forgot Password: onFailure"+exception.getMessage());
            if(exception.getMessage().contains("code expired"))
            {
                Log.d(TAG, "Forgot Password: onFailure"+exception);
            }
        }
    };

    private  void ShowAlert()
    {
        AlertDialog.Builder builder  = new AlertDialog.Builder(resetPasswordActivity);
        builder.setMessage("Password changed successfully.");
        builder.setTitle("Reset");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {
                Intent intent=new Intent(resetPasswordActivity,PasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("useremail",email);
                context.startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}