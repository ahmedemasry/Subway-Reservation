package com.example.ahmed.subwayreservation;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ahmed on 5/20/2015.
 */
public class Login extends Activity implements View.OnClickListener {

    EditText etAccountReader, etPasswordReader;
    Button bLogin, bSingupActivity;
    SharedPreferences data;
    String accountsFile = "AccountsData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initialize();
        bLogin.setOnClickListener(this);
        bSingupActivity.setOnClickListener(this);
    }

    private void initialize() {
        etAccountReader = (EditText)findViewById(R.id.etAccountReader);
        etPasswordReader = (EditText)findViewById(R.id.etPasswordReader);
        bLogin = (Button)findViewById(R.id.bLogin);
        bSingupActivity = (Button)findViewById(R.id.bSingupActivity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bLogin:
                String enteredAccount = etAccountReader.getText().toString();
                String enteredPassword = etPasswordReader.getText().toString();
                data = getSharedPreferences(accountsFile, 0);
                String rightPassword = data.getString(enteredAccount, "non");
                if(rightPassword == "non"){
                    Toast.makeText(this, "Account is not right , Try to retype it or sign up", Toast.LENGTH_LONG).show();
                }else if(rightPassword.contentEquals(enteredPassword)){
                    Intent intent = new Intent();
                    intent.putExtra("account", enteredAccount);
                    intent.putExtra("logged", true);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(this, "Password is not right !! ", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bSingupActivity:
                Intent signup = new Intent(this, SignUp.class);
                startActivity(signup);
                break;
        }
    }

}
