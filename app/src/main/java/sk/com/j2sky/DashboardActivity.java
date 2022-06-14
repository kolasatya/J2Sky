package sk.com.j2sky;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class DashboardActivity extends Activity {

    Button changepwd_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        changepwd_button= findViewById(R.id.changepwd_button);
        changepwd_button.setOnClickListener(view -> {
            Intent i=new Intent(DashboardActivity.this, ChangePasswordActivity.class);
            startActivity(i);
        });

    }
}