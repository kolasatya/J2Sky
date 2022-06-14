package sk.com.j2sky;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends Activity {
    static Context context;
    static SignupActivity signupActivity;
    Button register_button,cancel_button,otp_button;
    EditText editText_otp,editText_password,editText_re_password,editText_surname,editText_dob,editText_place,editText_name,editText_email,editText_phonenumber,editText_gender;
    static String email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context=getApplicationContext();
        signupActivity=this;
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            email = bundle.getString("useremail");
        }

        Log.d("SignupActivity","email"+email);
        register_button = (Button) findViewById(R.id.button_registeruser);
        cancel_button = (Button) findViewById(R.id.button_cancel);

        editText_dob=(EditText) findViewById(R.id.editText_dob);
        editText_email=(EditText) findViewById(R.id.editText_email);
        editText_email.setText(email);
        editText_email.setEnabled(false);
        editText_gender=(EditText) findViewById(R.id.editText_gender);
        editText_name=(EditText) findViewById(R.id.editText_Name);
        editText_phonenumber=(EditText) findViewById(R.id.editText_phone);

        editText_otp=(EditText) findViewById(R.id.editText_otp);





        editText_password=(EditText) findViewById(R.id.editText_password);
        editText_re_password=(EditText) findViewById(R.id.editText_re_password);
        //generate otp
        otp_button = (Button) findViewById(R.id.button_otp);
        otp_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                Cognito cognito =Cognito.getCognito(getApplicationContext());
                cognito.addAttribute("birthdate",editText_dob.getText().toString());//birthdate
                cognito.addAttribute("email",email);//email
                cognito.addAttribute("gender",editText_gender.getText().toString());//gender
                cognito.addAttribute("name",editText_name.getText().toString());//name
                cognito.addAttribute("phone_number",editText_phonenumber.getText().toString());//phonenumber

               // cognito.addAttribute("place",editText_place.getText().toString());//place
               // cognito.addAttribute("family_name",editText_surname.getText().toString());//familyname
               // cognito.addAttribute("gender",editText_gender.getText().toString());//gender
               // cognito.addAttribute("picture","");//picture
                editText_password.setError(null);
                editText_re_password.setError(null);
                if(validateSignup(editText_password.getText().toString(),editText_re_password.getText().toString()))
                {
                    cognito.signUpInBackground(editText_email.getText().toString(),editText_password.getText().toString());
                    otp_button.setVisibility(View.INVISIBLE);
                    register_button.setVisibility(View.VISIBLE);
                    editText_otp.setVisibility(View.VISIBLE);
                }else
                {
                    editText_password.setError(context.getText(R.string.password_retype_notmatched));
                    editText_re_password.setError(context.getText(R.string.password_retype_notmatched));
                }

            }
        });

        //registration
        register_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Cognito cognito =Cognito.getCognito(getApplicationContext());
                cognito.confirmUser(editText_email.getText().toString(),editText_otp.getText().toString());
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
    public static boolean validateSignup(String password, String retypepassword)
    {
        return (!password.isEmpty() || !retypepassword.isEmpty() || password.equals(retypepassword));

    }

    public static void SuccessCallback(Context appContext)
    {
        ShowAlert();

    }
    private static void ShowAlert()
    {
        AlertDialog.Builder builder  = new AlertDialog.Builder(signupActivity);
        builder.setMessage("You Have Successfully Registered.Please login.");
        builder.setTitle("SignUp");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int which)
                {
                    Intent intent=new Intent(signupActivity,PasswordActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("useremail",email);
                    context.startActivity(intent);
                }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}