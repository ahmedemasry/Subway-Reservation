package com.example.ahmed.subwayreservation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Ahmed on 5/18/2015.
 */
public class SignUp extends Activity implements View.OnClickListener {
    EditText etAccount, etPassword;
    Button bSubmit;
    SharedPreferences data;
    String accountsFile = "AccountsData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        initialize();
        bSubmit.setOnClickListener(this);
        data = getSharedPreferences(accountsFile, 0);
    }

    private void initialize() {
        etAccount = (EditText)findViewById(R.id.etAccount);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bSubmit = (Button)findViewById(R.id.bSubmit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSubmit :
                if(etAccount.getText().length() < 4){
                    etAccount.setHint("Be sure Account name more than 4 chars");
                    return;
                }
                if(etPassword.getText().length() < 4){
                    etPassword.setHint("Be sure Password more than 4 chars");
                    return;
                }
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                SharedPreferences.Editor editor = data.edit();
                editor.putString(account, password);
                editor.commit();
                finish();
                break;
        }

    }
}
