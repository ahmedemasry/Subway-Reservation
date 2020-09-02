package com.example.ahmed.subwayreservation;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;

/**
 * Created by Ahmed on 5/18/2015.
 */
public class Cities extends Activity {

    ListView LVcities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citieslist);

        LVcities = (ListView)findViewById(R.id.LVcities);
        final String[] cities = getResources().getStringArray(R.array.cities);
        LVcities.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities));
        LVcities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String choice = cities[i];
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",choice);
                returnIntent.putExtra("cityId",i);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }




}
