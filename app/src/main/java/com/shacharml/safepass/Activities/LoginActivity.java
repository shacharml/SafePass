package com.shacharml.safepass.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shacharml.safepass.R;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    // Declare the UI elements for the phone number input, send button, and progress bar
    private EditText login_EDT_phone_number;
    private Button login_BTN_send;
    private ProgressBar login_PRB_progress_bar;
    // Declare a variable for the FirebaseAuth class
    private FirebaseAuth myAuth;

    // Declare the callback method that is called on the PhoneAuthProvider
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        /**
         * Method called when OTP is sent from Firebase.
         * This method will show a Toast message, move to the VerifyActivity, and finish this activity.
         *
         * @param s String containing the unique id for the OTP
         * @param forceResendingToken used to resend OTP
         */
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            login_BTN_send.setVisibility(View.VISIBLE);
            login_PRB_progress_bar.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, "OTP is successfully sent.", Toast.LENGTH_SHORT).show();
            // Move to the VerifyActivity and pass the phone number and unique id as extras
            Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
            intent.putExtra(getString(R.string.LBL_phone), login_EDT_phone_number.getText().toString().trim());
            intent.putExtra(getString(R.string.LBL_verification_Id), s);
            startActivity(intent);
            finish();
        }

        /**
         * Method called when the user receives the OTP from Firebase.
         * This method will hide the send button and show the progress bar.
         *
         * @param phoneAuthCredential contains the OTP that has been verified
         */
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            login_BTN_send.setVisibility(View.GONE);
            login_PRB_progress_bar.setVisibility(View.VISIBLE);
        }

        /**
         * Method called when Firebase does not send the OTP due to any error or issue.
         * This method will hide the progress bar and show the send button.
         *
         * @param e contains the exception that occurred
         */
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // Display error message with the Firebase exception
            login_PRB_progress_bar.setVisibility(View.GONE);
            login_BTN_send.setVisibility(View.VISIBLE);
            Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    };

    /**
     * This is the onCreate method of the LoginActivity class
     * It sets the content view to the activity_login layout and calls the findViews method
     * A listener is set to the login_BTN_send button which performs some validations and sends a
     * verification code to the phone number entered
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Finds the views from the activity_login layout
        findViews();

        // Sets a listener to the login_BTN_send button
        login_BTN_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validates the entered phone number
                if (TextUtils.isEmpty(login_EDT_phone_number.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                } else if (login_EDT_phone_number.getText().toString().length() != 9) {
                    Toast.makeText(LoginActivity.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    // Makes the login_BTN_send button invisible and shows the login_PRB_progress_bar
                    login_BTN_send.setVisibility(View.GONE);
                    login_PRB_progress_bar.setVisibility(View.VISIBLE);
                    // Sends the verification code to the entered phone number
                    sendVerificationCode(login_EDT_phone_number.getText().toString());
                }
            }
        });

    }

    /**
     * sendVerificationCode() method is used to send an OTP (one-time password)
     * to the user's phone number for verification purposes.
     *
     * @param number - the phone number to send the OTP to
     */
    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(myAuth)
                        .setPhoneNumber(getString(R.string.IL_num) + number)      // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)    // Timeout and unit
                        .setActivity(this)                          // Activity (for callback binding)
                        .setCallbacks(mCallBack)                  // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    /**
     * findViews() method initializes the view elements from the XML layout file
     * and sets a reference to them in the corresponding class variables.
     */
    private void findViews() {
        login_EDT_phone_number = findViewById(R.id.login_EDT_phone_number);
        login_BTN_send = findViewById(R.id.login_BTN_send);
        login_PRB_progress_bar = findViewById(R.id.login_PRB_progress_bar);
        myAuth = FirebaseAuth.getInstance();
    }

    /**
     * onStop() is a lifecycle method called when the activity is no longer visible to the user.
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * onBackPressed() is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * onResume() is a lifecycle method called when the activity comes into the foreground.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

}