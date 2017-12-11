package com.nausif.numberguessinggame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int randomNumber;
    SQLiteDatabase db;

    public void displayResult(String result){
        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
    }


    int attempt = 0;
    public void guess(View view){
        EditText numberEditText = (EditText) findViewById(R.id.numberEditText);
        if(attempt < 10) {
            int guessNumber = Integer.parseInt(numberEditText.getText().toString());
            if (guessNumber > randomNumber) {
                displayResult("Lower!");
                showDialog();
                attempt++;
            } else if (guessNumber < randomNumber) {
                displayResult("Higher");attempt++;
            } else {
                displayResult("That's right. Try again!");
                Random rand = new Random();
                randomNumber = rand.nextInt(1000);
            }
        }
        else
        {
            displayResult("You used your 10 attempts try again!!");
            Random rand = new Random();
            randomNumber = rand.nextInt(1000);
            attempt = 0;
        }
    }
    final Context context = this;
    public void showDialog()
    {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.custom, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

//        final EditText userName = (EditText) promptsView
//                .findViewById(R.id.editTextDialogUserName);
//
//        final EditText userEmail = (EditText) promptsView
//                .findViewById(R.id.editTextDialogUserEmail);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                addScore();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    EditText editName;
    EditText editEmail;


    public void viewAllContacts(View view)
    {
        // Retrieving all records
        Cursor c=db.rawQuery("SELECT * FROM highscore", null);
        // Checking if no records found
        if(c.getCount()==0)
        {
            showMessage("Error", "No records found");
            return;
        }
        // Appending records to a string buffer
        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext())
        {
            //0 is Name
            //1 is eMail
            //2 is mobile

            buffer.append("score: "+c.getString(2)+" | ");
            buffer.append("Name: "+c.getString(0)+" | ");
            buffer.append("eMail: "+c.getString(1)+"\n");
        }
        // Displaying all records
        showMessage("HighScore Details", buffer.toString());
    }

    public void addScore(){


        if(editName.getText().toString().trim().length()==0||
                editEmail.getText().toString().trim().length()==0
                )
        {
            Toast.makeText(getApplicationContext(), "No Empty Data Allowed", Toast.LENGTH_SHORT).show();
            return;
        }
        //Logic for Add Contact
        db.execSQL("INSERT INTO highscore VALUES('"+editName.getText()+"','"+
                editEmail.getText()+"','"+String.valueOf(randomNumber)+"');");
        Toast.makeText(getApplicationContext(), "High Score Added", Toast.LENGTH_SHORT).show();



    }


    public void showMessage(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }

    Button btnViewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText)findViewById(R.id.editTextDialogUserName);
        editEmail = (EditText) findViewById(R.id.editTextDialogUserEmail);
        db = openOrCreateDatabase("HighScoreDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS highscore(name VARCHAR,email VARCHAR,score VARCHAR);");

        btnViewAll = (Button) findViewById(R.id.btnViewAll);

        Random rand = new Random();

        randomNumber = rand.nextInt(1000);
    }
}
