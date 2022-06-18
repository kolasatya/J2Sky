package sk.com.j2sky;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

import static android.content.ContentValues.TAG;

public class DashboardActivity extends Activity {

    Button changepwd_button,logout_button;
    DashboardActivity dashboardActivity;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardActivity=this;
        context=getApplicationContext();

        changepwd_button= findViewById(R.id.changepwd_button);
        changepwd_button.setOnClickListener(view -> {
            Intent i=new Intent(DashboardActivity.this, ChangePasswordActivity.class);
            startActivity(i);
        });
        logout_button= findViewById(R.id.logout_btn);
        logout_button.setOnClickListener(view -> {
           Cognito cognito=Cognito.getCognito(getApplicationContext());
           cognito.cognitoUser.globalSignOutInBackground(genericHandler);
        });

    }
    GenericHandler genericHandler=new GenericHandler() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "Logout user: onSuccess");
            ShowAlert();
        }

        @Override
        public void onFailure(Exception exception) {
            exception.printStackTrace();
            Log.d(TAG, "Logout user: exception"+exception.getMessage());
        }
    };
    private  void ShowAlert()
    {
        AlertDialog.Builder builder  = new AlertDialog.Builder(dashboardActivity);
        builder.setMessage("Logout Successful");
        builder.setTitle("Logout");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {
                Intent intent=new Intent(dashboardActivity,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}