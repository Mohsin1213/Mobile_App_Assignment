package com.nausif.temperatureconverter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = (Spinner) findViewById(R.id.tempspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.temperature_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText txtValue = (EditText) findViewById(R.id.txtValue);
        Button b = (Button) findViewById(R.id.btnConvert);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    long convertvalue;
                    String   toastvalue;
                    if (spinner.getSelectedItemPosition() == 0) {
                        convertvalue = Long.valueOf(txtValue.getText().toString()) * 9 / 5 + 32;
                        toastvalue = "Converted Value is: " + convertvalue + "°F";

                    } else {
                        convertvalue = (Long.valueOf(txtValue.getText().toString()) - 32) * 5 / 9;
                        toastvalue = "Converted Value is: " + convertvalue + "°C";
                    }

                    Toast.makeText(MainActivity.this, toastvalue,
                            Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {}
            }
        });

    }
}
