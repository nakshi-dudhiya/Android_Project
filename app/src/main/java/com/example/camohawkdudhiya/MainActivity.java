package com.example.camohawkdudhiya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
// I, Nakshi Dudhiya, 000793267 certify that this material is my original work. No
//other person's work has been used without due acknowledgement.
//Video link: https://youtu.be/ViMVeR5NV08


//Contacts:Admissions - 844-767-6871
//         Mohawk College - 905-575-1212
//         Help Desk - 905-575-2199
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private static String tag="==MAIN ACTIVITY==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, "OnCreate");

        Button b= findViewById(R.id.searchButton);
        b.setOnClickListener(this::onClick);

        Spinner mySpinner = findViewById(R.id.spinner);
        // Set a selection before setting listener,
        // to prevent "ghost" selection on start
        mySpinner.setSelection(0, false);
        mySpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        Spinner mySpinner= findViewById(R.id.spinner);
        String courseSelected = mySpinner.getSelectedItem().toString();
        Log.d(tag, "Course Selected: "+ courseSelected);
        EditText semSelected = findViewById(R.id.semEditTextView);
        String semester= semSelected.getText().toString();
        Log.d(tag, "Sem Selected: "+ semester);
        TextView error= findViewById(R.id.errorTextView);
        EditText name = findViewById(R.id.nameEditTextView);
        String user= name.getText().toString();
        int sem = Integer.parseInt(semester);
        try {
            if (sem>= 1 && sem<=6 ){
                error.setText("");
                Intent switch2Activity2 = new Intent(MainActivity.this, MainActivity2.class);
                switch2Activity2.putExtra("course", courseSelected);
                switch2Activity2.putExtra("semester",semester);
                switch2Activity2.putExtra("user",user);
                DownloadAsyncTask dl = new DownloadAsyncTask();
                // Build call to Webservice
                String uri= "https://csunix.mohawkcollege.ca/~geczy/mohawkprograms.php";

                if (!courseSelected.equals("")) {
                    // Filter - numbers (Year) no quotes on value, strings have quotes on value
                    uri += "?filter=[{\"type\":\"number\",\"column\":\"program\",\"value\":" + courseSelected + "},{\"type\":\"number\",\"column\":\"semesterNum\",\"value\":"+ semester +"}]";
                }
                Log.d(tag, "startDownload " + uri);
                dl.execute(uri);
                startActivity(switch2Activity2);
            }
            if(sem<1 || sem>6 || TextUtils.isEmpty(semSelected.getText().toString()) ){
                error.setText("Error: Sem should be from 1 to 6.");
            }
        }
        catch (Exception ex){

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Load string array from resources
        String[] courseId = getResources().getStringArray(R.array.courses);
        // log the spinner's choice
        Log.d(tag, courseId[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // log the spinner's choice
        Log.d(tag, "Nothing Selected");
    }

}