package com.example.ahmed.subwayreservation;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button Bcities, bLoginActivity, bShowTickets;
    TextView tvAccount, tvNTickets;
    String result;
    boolean logged = false; // false
    String account;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAccount = (TextView)findViewById(R.id.tvAccount);
        tvNTickets = (TextView)findViewById(R.id.tvNTickets);
        Bcities = (Button)findViewById(R.id.Bcitites);
        bLoginActivity = (Button)findViewById(R.id.bLoginActivity);
        bShowTickets = (Button)findViewById(R.id.bShowTickets);

        Bcities.setOnClickListener(this);
        bLoginActivity.setOnClickListener(this);
        bShowTickets.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bLoginActivity){
            Intent login = new Intent(this, Login.class);
            startActivityForResult(login, 1);
        }
        else if(logged) {
            switch (view.getId()) {
                case R.id.Bcitites:
                    Intent reserve = new Intent(this, Reservation.class);
                    reserve.putExtra("account",account);
                    startActivityForResult(reserve, 1);
                    break;
                case R.id.bShowTickets:
                    Intent ticketsA = new Intent(this, Tickets.class);
                    ticketsA.putExtra("account",account);
                    startActivity(ticketsA);
                    break;
            }
        }
        else {
            Toast.makeText(this,"You should login first",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                logged = data.getBooleanExtra("logged", false);
                account = data.getStringExtra("account");
                tvAccount.setText(account);
                Toast.makeText(this, "Welcome, " + account, Toast.LENGTH_LONG).show();
                //loadData();
            }
        }
    }
}
