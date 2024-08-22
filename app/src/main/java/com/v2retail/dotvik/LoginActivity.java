package com.v2retail.dotvik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.v2retail.ApplicationController;
import com.v2retail.dotvik.dc.Process_Selection_Activity;
import com.v2retail.dotvik.ecomm.Ecomm_Process_Selection;
import com.v2retail.dotvik.store.Home_Activity;
import com.v2retail.util.AlertBox;
import com.v2retail.util.SharedPreferencesData;



import java.io.UnsupportedEncodingException;


/**
 * A login screen that offers login via userName/password.
 */
public class LoginActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    ProgressDialog dialog;
    private static final String TAG = LoginActivity.class.getName();
    Context con;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //  private UserLoginTask mAuthTask = null;

    // UI references.
    private RadioGroup locGrp;
    String loc;
    private EditText mUserName;
    private EditText mPasswordView;
    private TextView mResponseView;
    private View mProgressView;
    private View mLoginFormView;
    private String loc_name;
    AlertBox box;
    String URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(findViewById(R.id.ver)!=null) {
            ((TextView)findViewById(R.id.ver)).setText(BuildConfig.VERSION_NAME);
        }

        Log.d(TAG, TAG + " created");

        con = getApplicationContext();

        SharedPreferencesData data = new SharedPreferencesData(con);
        URL = data.read("URL");
       // String defLoc=data.read("LOC");
        Log.d(TAG,  "URL -> "+URL);

        box = new AlertBox(LoginActivity.this);
        dialog = new ProgressDialog(LoginActivity.this);

        // Set up the login form.

        locGrp=(RadioGroup)findViewById(R.id.loc);
        mUserName = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mResponseView = (TextView) findViewById(R.id.response);

        String username = data.read("USERNAME");

        if(username!=null && username.length()>0) {
            mUserName.setText(username);
        }

        String password = data.read("PASSWORD");
        if(password!=null && password.length()>0) {
            mPasswordView.setText(password);
        }

        mUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    mPasswordView.requestFocus();
                    return true;
                }
                return false;
            }
        });

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mLoginButton = (Button) findViewById(R.id.email_sign_in_button);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
                try {
                    attemptLogin();
                } catch (Exception e) {
                    box.getErrBox(e);
                }

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
       /* if(defLoc!=null  )
        switch (defLoc)
        {
            case "Store":
                 locGrp.check(R.id.store);
                break;
            case "DC":
            default:
                locGrp.check(R.id.dc);
                break;
        }*/
        mUserName.requestFocus();



    }




    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid userName, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
       /* if (mAuthTask != null) {
            return;
        }*/

        // Reset errors.
        mUserName.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mUserName.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid userName address.
        if (TextUtils.isEmpty(userName)) {
            mUserName.setError(getString(R.string.error_field_required));
            focusView = mUserName;
            cancel = true;
        } else if (!isUserNameValid(userName)) {
            mUserName.setError(getString(R.string.error_invalid_email));
            focusView = mUserName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            /*showProgress(true);
            mAuthTask = new UserLoginTask(userName, password);
            mAuthTask.execute((Void) null);*/
            try {

                networkCall();
            } catch (Exception e) {
                box.getErrBox(e);
            }
        }
    }

    private boolean isUserNameValid(String userName) {
        //TODO: Replace this with your own logic
        if (!userName.equals(""))
            return true;
        else
            return false;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        if (!password.equals(""))
            return true;
        else
            return false;
    }

    /**
     * Shows the progress UI and hides the login form.
     */



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    void clear() {

        mPasswordView.setText("");
        mUserName.setText("");
    }


    @Override
    protected void onPause() {
        super.onPause();
        clear();
    }

    public void onClear(View view) {
        clear();

    }

    private void networkCall() {
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    login();
                }catch (Exception e)
                {
                    dialog.dismiss();
                   box.getErrBox(e);
                }
            }
        }, 2000);
    }

    void login() {

        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
        String addrec = "scnrec#" + mUserName.getText().toString().trim() + "#" + mPasswordView.getText().toString().trim() + "#<eol>";
        Log.d(TAG, "payload-> " + addrec);

        try {
            sendAndRequestResponse(addrec.trim());
        }catch (Exception e)
        {   dialog.dismiss();
            box.getErrBox(e);
        }
        //clear();
    }

    private void sendAndRequestResponse(final String requestBody) {

        //RequestQueue initialized
        mRequestQueue = ApplicationController.getInstance().getRequestQueue();

        mStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                response = response.substring(9, response.length());
                dialog.dismiss();
                Log.d(TAG, "response :" + response);
                if (response == null) {

                    box.getBox("Error", "No response from Server");
                    mUserName.requestFocus();

                } else if (response.equals("")||response.equals("null")) {

                    Log.d(TAG, " Response is unknown :" + response);
                    box.getBox("Error", "Empty response from server /Unable to Connect To Server");
                    mUserName.requestFocus();
                    clear();

                } else if (response.charAt(0) == '1') {
                    Log.d(TAG, " Response : Logged in" + response);

                    moveTaskToBack(true);

                    String ar[] = response.split("#");
                    Log.d(TAG, " login : " + ar[0]);

                    int radioButtonID = locGrp.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton)locGrp.findViewById(radioButtonID);
                    String loc = (String) radioButton.getText();
                    SharedPreferencesData data = new SharedPreferencesData(con);
                    data.write("USER", mUserName.getText().toString().trim().toUpperCase());
                    if(ar.length>1) {
                        Log.d(TAG, " werks : " + ar[1]);
                        data.write("WERKS", ar[1]);
                    }

                    data.write("LOC",loc);

                    data.write("USERNAME", mUserName.getText().toString().trim());
                    data.write("PASSWORD", mPasswordView.getText().toString().trim());


                    if(isDC_user(ar[1])) {
                        Intent intent = null;
                        intent = new Intent(LoginActivity.this, Process_Selection_Activity.class);
                        startActivity(intent);
                    } else if(isEcomm_user(ar[1])) {
                        Intent intent = null;
                        intent = new Intent(LoginActivity.this, Ecomm_Process_Selection.class);
                        startActivity(intent);
                    } else {
                        Intent intent = null;
                        intent = new Intent(LoginActivity.this, Home_Activity.class);
                        startActivity(intent);
                    }
                    
                    clear();



                } else if (response.equals("0")) {
                    Log.d(TAG, " Response is 0 -> wrong user-pass :" + response);

                    box.getBox("Err", "Wrong User & Password");

                    clear();
                    mUserName.requestFocus();
                } else {

                    Log.d(TAG, " Response is unknown :" + response);

                    box.getBox("Response", response);

                    clear();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());

                String err = "";

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    err = "Communication Error! Unable to Connect Server";

                } else if (error instanceof AuthFailureError) {
                    err = "Authentication Error!";
                } else if (error instanceof ServerError) {
                    err = "Server Side Error!";
                } else if (error instanceof NetworkError) {
                    err = "Network Error!";
                } else if (error instanceof ParseError) {
                    err = "Parse Error!";
                } else err = error.toString();
                dialog.dismiss();
                box.getBox("ERR", err);


            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                Response<String> res = super.parseNetworkResponse(response);
                String data = res.result;
                Log.i(TAG, data);

                return res;
            }


        };
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(3*DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
       /* mStringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });*/
        mRequestQueue.add(mStringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }


    boolean isDC_user(String myWerks) {
        boolean retVal = false;
        if(myWerks!=null && (myWerks.equalsIgnoreCase("DH24") || myWerks.equalsIgnoreCase("DH26"))) {
            return true;
        }
        return retVal;
    }

    boolean isEcomm_user(String myWerks) {
        if(myWerks!=null && myWerks.equalsIgnoreCase("DH25")) {
            return true;
        }
        return false;
    }
}

