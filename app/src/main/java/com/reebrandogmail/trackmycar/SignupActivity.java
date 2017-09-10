package com.reebrandogmail.trackmycar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.Util.Mask;
import com.reebrandogmail.trackmycar.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_phone) EditText _phoneNumber;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    DBHandler db;

    String name;
    String password;
    String email;
    String phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        db = new DBHandler(getApplicationContext());

        _phoneNumber.addTextChangedListener(Mask.insert("(##)#####-####", _phoneNumber));

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.creating_account));
        progressDialog.show();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        User user = new User();
                        user.setUser(name);
                        user.setMail(email);
                        user.setPassword(password);
                        user.setPhone(phone);

                        db.addUser(user);
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        Intent intent = new Intent();
        intent.putExtra("username", _emailText.getText().toString());
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.login_failed), Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        name = _nameText.getText().toString();
        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        phone = Mask.unmask(_phoneNumber.getText().toString());

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError(getString(R.string.at_least));
            valid = false;
        } else {
            _nameText.setError(null);
        }

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

        if (phone.isEmpty() || phone.length() < 8 || phone.length() > 11) {
            _phoneNumber.setError(getString(R.string.between_chars));
            valid = false;
        } else {
            _phoneNumber.setError(null);
        }

        return valid;
    }
}
