package sk.com.j2sky;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       //creating new thread just for demonstration of background tasks
        Thread t=new Thread() {
            public void run() {

                try {
                    //sleep thread for 10 seconds
                    sleep(10000);
                    //Call Main activity
                    Intent i=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    //destroying Splash activity
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //start thread
        t.start();
    }
}