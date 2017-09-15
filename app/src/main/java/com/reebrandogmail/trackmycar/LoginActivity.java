package com.reebrandogmail.trackmycar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.model.User;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int RC_SIGN_IN = 007;
    private PrefsManager prefManager;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    CallbackManager callbackManager;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    @BindView(R.id.chk_connected) CheckBox _chkConnected;
    @BindView(R.id.btn_sign_in) SignInButton btnSignIn;
    @BindView(R.id.login_fb_button) LoginButton loginButton;
    private DBHandler db;
    private String Name;
    private String FEmail;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        ButterKnife.bind(this);

        prefManager = new PrefsManager(this);

        db = new DBHandler(this);

        // Facebook Login
        //loginButton.setReadPermissions("email");
        //loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.setReadPermissions("public_profile email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), loginResult.getAccessToken().getUserId(),
                        null, HttpMethod.GET, new GraphRequest.Callback() {

                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.v("LoginActivity Response ", response.toString());

                        try {

                            Name = (String) response.getJSONObject().get("first_name");
                            FEmail = (String) response.getJSONObject().get("email");

                            Log.v("FACEBOOK", Name);
                            Log.v("FACEBOOK", FEmail);

                            User tmp = new User();
                            tmp.setUser(Name);
                            tmp.setMail(FEmail);
                            db.addOnceBySocial(tmp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("FACEBOOK", String.valueOf(response.getJSONObject()));
                    }

                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();

                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        // Google Login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());

        skipLogin();

        // Custom Login
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Starts sign up activity for email result
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        requestSmsPermission();
        requestSendSmsPermission();
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @OnClick(R.id.btn_sign_in)
    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.i(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.i(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);


        } else {
            // Signed out

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
            mProgressDialog.dismiss();
        }
    }

    public void skipLogin(){
        if (prefManager.isConnected()){
            // skip login
            startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        // Validates user entry
        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.authenticating));
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        String email = _emailText.getText().toString().trim();
                        String password = _passwordText.getText().toString().trim();

                        // Validates valid user
                        if (db.loginUser(email, password)){
                            onLoginSuccess();
                            // Puts keep connected preference in shared preferences
                            keepConnected();
                        } else {
                            onLoginFailed();
                        }

                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Facebook callback
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                _emailText.setText(data.getStringExtra("username"));
                // By default we just finish the Activity and log them in automatically
                //this.finish();
            }
        }

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.login_failed), Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public void keepConnected(){
        if (_chkConnected.isChecked()){
            prefManager.setKeepConnected(true);
        }
        else {
            prefManager.setKeepConnected(false);
        }
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError(getString(R.string.valid_address));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(getString(R.string.between_chars));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }

    }

    private void requestSendSmsPermission(){
        String permission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }
}
