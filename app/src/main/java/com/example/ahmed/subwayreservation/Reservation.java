package com.example.ahmed.subwayreservation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.Duration;

/**
 * Created by Ahmed on 5/18/2015.
 */
public class Reservation extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {

    ListView lvTimes;
    Button bFrom, bTo, bReserveTicket, bDateSet;
    TextView tvFrom, tvTo, tvTime, tvDegree, tvDate, tvStatus;
    int Dfrom = -2, Dto = -2;
    String[] sTimesList = new String[3];
    RadioGroup rgDegree;
    int[][] Times= new int[3][2]; // from[i][0]  and   to[i][1]
    String account;
    String[] ticket = new String[6];
    String ticketsFile = "TicketsData";
    SharedPreferences data;
    DateFormat dateFormat = DateFormat.getDateInstance();
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        initialize();
        Intent intent = getIntent();

        account = intent.getStringExtra("account");
        Toast.makeText(this, "Reserve your ticket, " + account, Toast.LENGTH_LONG).show();

        bFrom.setOnClickListener(this);
        bTo.setOnClickListener(this);
        bDateSet.setOnClickListener(this);
        bReserveTicket.setOnClickListener(this);
        lvTimes.setOnItemClickListener(this);
        rgDegree.setOnCheckedChangeListener(this);

    }


    private void initialize() {
        for(int i = 0; i < 6; i++){
            ticket[i] = "";
        }

        bFrom = (Button)findViewById(R.id.bFrom);
        bTo = (Button)findViewById(R.id.bTo);
        bDateSet = (Button)findViewById(R.id.bDateSet);
        bReserveTicket = (Button)findViewById(R.id.bReserveTicket);
        tvFrom = (TextView)findViewById(R.id.tvFrom);
        tvTo = (TextView)findViewById(R.id.tvTo);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvTime = (TextView)findViewById(R.id.tvTime);
        tvDegree = (TextView)findViewById(R.id.tvDegree);
        tvDate = (TextView)findViewById(R.id.tvDate);
        lvTimes = (ListView)findViewById(R.id.lvTimes);
        rgDegree = (RadioGroup)findViewById(R.id.rgDegree);
        data = getSharedPreferences(ticketsFile, 0);

    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            i2++;
            tvDate.setText(i3 + "-" + i2 + "-" + i);
            ticket[5] = (i3 + "-" + i2 + "-" + i).toString();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bFrom:
                Intent i1 = new Intent(this, Cities.class);
                startActivityForResult(i1, 1);
//                showTimesList();
                break;
            case  R.id.bTo:
                Intent i2 = new Intent(this, Cities.class);
                startActivityForResult(i2, 2);
//                showTimesList();
                break;
            case R.id.bDateSet:
                new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.bReserveTicket:

//                Object[][] o = data.getObject(account, "non");
//                if(o != "non"){
//                    while(find null object at i){
//
//                    }
//                    //found
//                    o[i] = ticket;
//                    SharedPreferences.Editor editor = data.edit();
//                    editor.putObject(account, o);
//                    editor.commit();
//                }else{
//                    Object[][] o = new  Object[5][6];
//                    o[1] = ticket;
//                    SharedPreferences.Editor editor = data.edit();
//                    editor.putObject(account, o);
//                    editor.commit();
//                }
                //if ok
                updateTicketsFile();
                Toast.makeText(this, "Ticket reserved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("account", account);
                intent.putExtra("logged", true);
                setResult(RESULT_OK,intent);
                finish();

//                finish();
                //if not ok (missing information
//                Toast.makeText(this, "Missing information", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void showTimesList() {
        if(Dfrom == -1 || Dto == -1 || Dfrom == Dto){
            Toast.makeText(this, "Not Valid Destination", Toast.LENGTH_LONG).show();
            lvTimes.setVisibility(View.INVISIBLE);
        }
        else if(tvFrom.getText() != "" && tvTo.getText() != ""){
            setTimesList();
            lvTimes.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sTimesList));
            lvTimes.setVisibility(View.VISIBLE);
        }
    }

    private void setTimesList() {
        if(Dfrom < Dto){
            int[] toSouth = getResources().getIntArray(R.array.ToSouth);
            for(int i = 0; i < 3; i++){
                Times[i][0] = toSouth[i] + Dfrom;
                Times[i][1] = toSouth[i] + Dto;
                sTimesList[i] = (timeForm(Times[i][0]) + " : " + timeForm(Times[i][1]));
            }
        }
        else { //if(Dfrom < Dto){
            int[] toNorth = getResources().getIntArray(R.array.ToNorth);
            for(int i = 0; i < 3; i++){
                Times[i][0] = toNorth[i] + (8 - Dfrom);
                Times[i][1] = toNorth[i] + (8 - Dto);
                sTimesList[i] = (timeForm(Times[i][0]) + " : " + timeForm(Times[i][1]));
            }
        }

    }

    private String timeForm(int i) {
        String TimeForm = "";
        int hour = i % 12;
        int PMorAM = i / 12;
        if(hour == 0){
            TimeForm += "12";
        }
        else {
            TimeForm += hour;
        }
        if(PMorAM % 2 == 0){
            TimeForm += "AM";
        }else {
            TimeForm += "PM";
        }
        return TimeForm;

    }


    private int getDestinationId(int cityId) {
        if(cityId == 0){
            return getResources().getInteger(R.integer.Alexandria);
        }
        if(cityId == 1){
            return getResources().getInteger(R.integer.Damanhour);
        }
        if(cityId == 2){
            return getResources().getInteger(R.integer.Tanta);
        }
        if(cityId == 3){
            return getResources().getInteger(R.integer.Cairo);
        }
        if(cityId == 4){
            return getResources().getInteger(R.integer.Luxor);
        }
        if(cityId == 5){
            return getResources().getInteger(R.integer.Aswan);
        }

        return -1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                tvFrom.setText(data.getStringExtra("result"));
                Dfrom = getDestinationId(data.getIntExtra("cityId",-1));
                ticket[0] = (data.getStringExtra("result"));
                showTimesList();
            }
        }
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                tvTo.setText(data.getStringExtra("result"));
                Dto = getDestinationId(data.getIntExtra("cityId",-1));
                ticket[1] = (data.getStringExtra("result"));
                showTimesList();
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ticket[2] = timeForm(Times[i][0]);
        ticket[3] = timeForm(Times[i][1]);
        tvTime.setText(timeForm(Times[i][0]) + " : " + timeForm(Times[i][1]));
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rbFirst:
                ticket[4] = "First";
                break;
            case R.id.rbSecond:
                ticket[4] = "Second";
                break;
            case R.id.rbThird:
                ticket[4] = "Third";
                break;
        }
        tvDegree.setText(ticket[4].toString());
    }
    public void updateTicketsFile(){
//        Toast.makeText(this, ticket[0] + " | " + ticket[1] + " | " + ticket[2] + " | " + ticket[3] + " | " + ticket[4] + " | " + ticket[5] ,Toast.LENGTH_LONG).show();
        data = getSharedPreferences(ticketsFile, 0);
        String tickets = data.getString(account, "non");

        if(tickets.contentEquals("non")){
            tickets = "";
            for(int i = 0; i < ticket.length; i++){
                tickets += ticket[i] + ",";
            }
            tickets += "n,";
        }
        else {
//            int m = 0, n = 0;
//            String reader = "";
//            while (reader != "n"){
//                reader = "";
//                while (tickets.charAt(n) != ','){
//                    reader += tickets.charAt(n);
//                    n++;
//                }
////                reader = tickets.substring(m,n-1);
////                m = n+1;
//                n++;
//            }
            tickets = tickets.substring(0,tickets.length()-2);
            for(int i = 0; i < ticket.length; i++){
                tickets += ticket[i] + ",";
            }
            tickets += "n,";
        }
        SharedPreferences.Editor editor = data.edit();
        editor.putString(account, tickets);
        editor.commit();
    }
    public String[][] readTicketFile(){
        String[][] ticketsArray = new String[20][6];
        data = getSharedPreferences(ticketsFile, 0);
        String tickets = data.getString(account, "non");

        int m = 0, n = 0, i = 0, j = 0;
        String reader = "";
        while (reader != "n"){
            while (tickets.charAt(n) != ',') n++;
            reader = tickets.substring(m,n-1);
            ticketsArray[i][j++] = reader;
            if(j == 6){
                j = 0;i++;
            }
            m = n+1;
            n++;
        }
        return ticketsArray;
    }

}

