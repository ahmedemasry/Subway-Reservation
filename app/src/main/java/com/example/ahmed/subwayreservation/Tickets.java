package com.example.ahmed.subwayreservation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ahmed on 6/2/2015.
 */
public class Tickets extends Activity implements View.OnClickListener {

    TextView tvAccountName, tvDfrom, tvDto, tvTfrom, tvTto, tvDegreeV, tvDateV, tvCurrentTicket;
    Button bPrevious, bNext;
    LinearLayout TicketLayout;
    String ticketsFile = "TicketsData";
    SharedPreferences data;
    String account;//delete ahmed
    String[][] tickets;
    int NoTickets = 0, currentTicket = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets);
        tickets  = new String[][]{{"0", "1", "2", "3", "4", "5"}};
        NoTickets = 1;

        Intent intent = getIntent();
        account = intent.getStringExtra("account");

        initialize();
        tickets = readTicketFile();
        if (NoTickets != 0) {
            showTicket(0);
        }
        else {
            TicketLayout.setVisibility(View.INVISIBLE);
            Toast.makeText(this, account + ", You have no tickets reserved !!", Toast.LENGTH_LONG).show();
        }

        bNext.setOnClickListener(this);
        bPrevious.setOnClickListener(this);

    }

    private void showTicket(int i) {
        tvAccountName.setText(account);
        tvDfrom.setText(tickets[i][0]);
        tvDto.setText(tickets[i][1]);
        tvTfrom.setText(tickets[i][2]);
        tvTto.setText(tickets[i][3]);
        tvDegreeV.setText(tickets[i][4]);
        tvDateV.setText(tickets[i][5]);
        tvCurrentTicket.setText(currentTicket+1 + " / " + NoTickets);

    }

    private void initialize() {
        tvAccountName = (TextView) findViewById(R.id.tvAccountName);
        tvDfrom = (TextView) findViewById(R.id.tvDfrom);
        tvDto = (TextView) findViewById(R.id.tvDto);
        tvTfrom = (TextView) findViewById(R.id.tvTfrom);
        tvTto = (TextView) findViewById(R.id.tvTto);
        tvDegreeV = (TextView) findViewById(R.id.tvDegreeV);
        tvDateV = (TextView) findViewById(R.id.tvDateV);
        tvCurrentTicket = (TextView) findViewById(R.id.tvCurrentTicket);
        bPrevious = (Button) findViewById(R.id.bPrevious);
        bNext = (Button) findViewById(R.id.bNext);

        TicketLayout = (LinearLayout)findViewById(R.id.TicketLayout);

    }


    public String[][] readTicketFile() {
        String[][] ticketsArray = new String[20][6];
        data = getSharedPreferences(ticketsFile, 0);
        String tickets = data.getString(account, "non");
//        String tickets = "A,B,C,D,E,F,n,";

        if(!tickets.contentEquals("non")) {
            int m = 0, n = 0, i = 0, j = 0;
            String reader = "";
            ticketsArray[0] = new String[6];
            while (reader != "n" && n < tickets.length()) {
                reader = "";
                while (tickets.charAt(n) != ',' && n < tickets.length()){
                    reader += tickets.charAt(n);
                    n++;
                }
                ticketsArray[i][j++] = reader;
                reader = "";
                if (j == 6) {
                    j = 0;
                    i++;
                    ticketsArray[i] = new String[6];
                }
                n++;
            }
            NoTickets = i;
            return ticketsArray;
        }
        NoTickets = 0;
        return ticketsArray;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bNext:
                currentTicket = currentTicket++ % (NoTickets+1);
                if(NoTickets != 0) showTicket(currentTicket);
                break;
            case R.id.bPrevious:
                currentTicket--;
                if(currentTicket < 0){
                    currentTicket = NoTickets-1;
                }
                if(NoTickets != 0) showTicket(currentTicket);
                break;
        }
    }


}