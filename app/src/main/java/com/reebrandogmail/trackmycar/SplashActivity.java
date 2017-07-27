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

import com.reebrandogmail.trackmycar.api.ApiUtils;
import com.reebrandogmail.trackmycar.api.UserAPI;
import com.reebrandogmail.trackmycar.model.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    // Time that the splashscreen will be visible
    private final int SPLASH_DISPLAY_LENGTH = 3500;
    private UserAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mService = ApiUtils.getUserAPI();

        // Loads data from the api
        syncData();
        // Executes the animation
        loadScreen();
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
        for (User user : users) {
            Toast.makeText(getApplicationContext(), user.getUser(), Toast.LENGTH_SHORT).show();
        }
    }



    private class SyncData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // Create a very simple REST adapter which points the GitHub API.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.mocky.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Create an instance of our GitHub API interface.
           // SimpleService.GitHub github = retrofit.create(SimpleService.GitHub.class);
            UserAPI userAPI = retrofit.create(UserAPI.class);

            // Create a call instance for looking up Retrofit contributors.
            //Call<List<SimpleService.Contributor>> call = github.contributors("square", "retrofit");
            Call<List<User>> call = (Call<List<User>>) userAPI.getUsers();

            // Fetch and print a list of the contributors to the library.
            List<User> users = null;
            try {
                users = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (User u : users) {
                Log.i("User", u.getUser());
                Log.i("Password", u.getPassword());
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}