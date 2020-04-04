package com.app.spinnerdemo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

public class AndroidActivity extends AppCompatActivity {
    private String[] countryArray;
    private AppCompatSpinner spinnerCountry;
    private RelativeLayout relSpinner;
    private TextView txtSelectedItem;
    private int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryArray = new String[getResources().getStringArray(R.array.country).length];
        countryArray = getResources().getStringArray(R.array.country);

        spinnerCountry = findViewById(R.id.spinnerCountry);
        relSpinner = findViewById(R.id.relSpinner);
        txtSelectedItem = findViewById(R.id.txtSelectedItem);

        //define custom width of spinner
        spinnerCountry.setDropDownWidth((int) getResources().getDimension(R.dimen._150dp));

        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(AndroidActivity.this
                , R.layout.spinner_custom_text_view
                , countryArray);

        spinnerCountry.setAdapter(myCustomAdapter);

        AdapterView.OnItemSelectedListener countrySelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {
                selectedPosition = position;
                txtSelectedItem.setText(countryArray[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        };
        spinnerCountry.setOnItemSelectedListener(countrySelectedListener);
        spinnerCountry.setSelection(selectedPosition);

        relSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerCountry.performClick();
            }
        });
    }

    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.spinner_custom_text_view, parent, false);
            RelativeLayout relMain=(RelativeLayout) row.findViewById(R.id.relMain);
            TextView label=(TextView)row.findViewById(R.id.spinnerText);
            ImageView imgSelected=(ImageView) row.findViewById(R.id.imgSelected);
            label.setText(countryArray[position]);

            if(countryArray.length == 1){ //only 1 item
                relMain.setBackground(getResources().getDrawable(R.drawable.all_corners_rounder));
            } else {
                if(0 == position){ //first item
                    relMain.setBackground(getResources().getDrawable(R.drawable.rounded_top));
                } else if((countryArray.length -1) == position){  //last item
                    relMain.setBackground(getResources().getDrawable(R.drawable.rounded_bottom));
                } else { //other item
                    relMain.setBackground(getResources().getDrawable(R.drawable.corners_not_rounded));
                }
            }

            if(selectedPosition == position){
                imgSelected.setVisibility(View.VISIBLE);
            } else {
                imgSelected.setVisibility(View.GONE);
            }

            return row;
        }
    }
}
