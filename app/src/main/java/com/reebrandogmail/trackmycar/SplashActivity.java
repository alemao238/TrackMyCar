package com.reebrandogmail.trackmycar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.api.ApiUtils;
import com.reebrandogmail.trackmycar.api.UserAPI;
import com.reebrandogmail.trackmycar.model.User;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    // Time that the splashscreen will be visible
    private final int SPLASH_DISPLAY_LENGTH = 3500;
    private UserAPI mService;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       db = new DBHandler(this);

        SyncData syncData = new SyncData();
        syncData.execute("");
    }

    private void loadScreen() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animation_splash);
        Animation animText = AnimationUtils.loadAnimation(this, R.anim.animation_splash_text);
        anim.reset();
        animText.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        TextView tv = (TextView) findViewById(R.id.tvName);
        if (iv != null && tv != null) {
            iv.clearAnimation();
            tv.clearAnimation();
            iv.startAnimation(anim);
            tv.startAnimation(animText);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void syncData(){
        mService = ApiUtils.getUserAPI();

        mService.getUsers()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Deu ruim",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        Log.e("Deu ruim", e.getMessage());
                    }

                    @Override
                    public void onNext(List<User> users) {
                        showData(users);
                    }
                });
    }

    private void showData(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            Toast.makeText(getApplicationContext(), users.get(i).getUser() + "\n" + users.get(i).getPassword(), Toast.LENGTH_SHORT).show();
            db.addUser(new User(i, users.get(i).getUser(), users.get(i).getPassword()));
        }
    }



    private class SyncData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // Loads data from the api
            syncData();
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            // Executes the animation
            loadScreen();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}