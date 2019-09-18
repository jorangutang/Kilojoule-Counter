package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import android.os.Bundle;
import com.google.gson.reflect.TypeToken;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import java.lang.reflect.Type;
import android.widget.TextView;
import android.widget.Button;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class DiaryEntries extends AppCompatActivity {

    EntryObject newEntry;
    ArrayList<EntryObject> entries;    //storing objects

    Intent Action;
    SharedPreferences SPstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.entry_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Action = this.getIntent();

        if(Action.getExtras().getString("id").equals("calcultorKey")
                || Action.getExtras().getString("id").equals("homeKey")
                || Action.getExtras().getString("id").equals("diaryKey")){
            newEntry = entries.get(Integer.parseInt(Action.getExtras().getString("index")));
        }
        displayData();

        
        if(Action.getExtras().getString("index").equals("0")){
            Button PreviousBtn =  findViewById(R.id.buttonPrevious);
            PreviousBtn.setVisibility(View.INVISIBLE);
        }

        else if(Action.getExtras().getString("index").equals((entries.size()-1)+"")){
            Button nextButton =  findViewById(R.id.buttonNext);
            nextButton.setVisibility(View.INVISIBLE);
        }

        
        
        Button Donebtn1 = findViewById(R.id.Donebtn);       
        Donebtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent done = new Intent(getApplicationContext(), MainActivity.class);
                done.putExtra("id", "diaryKey");
                startActivity(done);
                finish();
            }
        });

        
        
        Button EditBtn = findViewById(R.id.buttonEdit);
        EditBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent edit = new Intent(getApplicationContext(), Calculator.class);
                edit.putExtra("id", "diaryKey");
                String index = entries.indexOf(newEntry)+"";
                edit.putExtra("index", index);
                startActivity(edit);
                finish();
            }
        });

        Button NextBtn = findViewById(R.id.buttonNext);
        NextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent next = new Intent(getApplicationContext(), DiaryEntries.class);
                next.putExtra("id", "diaryKey");
                String index = (entries.indexOf(newEntry)+1)+"";
                next.putExtra("index", index);
                startActivity(next);
                finish();
            }
        });

        Button PreviousBtn = findViewById(R.id.buttonPrevious);
        PreviousBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent back = new Intent(getApplicationContext(), DiaryEntries.class);
                back.putExtra("id", "diaryKey");
                String index = (entries.indexOf(newEntry)-1)+"";
                back.putExtra("index", index);
                startActivity(back);
                finish();
            }
        });
    }



    private void displayData() {
        //Date
        SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy HH:mm");
        ((TextView) findViewById(R.id.textViewDate)).setText(formatter.format(newEntry.getDate()));
        ((TextView) findViewById(R.id.textViewBreakfastSummary)).setText(newEntry.getBreakfast());
        ((TextView) findViewById(R.id.textViewLunchSummary)).setText(newEntry.getLunch());
        ((TextView) findViewById(R.id.textViewDinnerSummary)).setText(newEntry.getDinner());
        ((TextView) findViewById(R.id.textViewFoodTotal)).setText(newEntry.getFoodTotal());
        ((TextView) findViewById(R.id.textViewWalkSummary)).setText(newEntry.getWalk());
        ((TextView) findViewById(R.id.textViewExerciseSummary)).setText(newEntry.getExercise());
        ((TextView) findViewById(R.id.textViewOtherSummary)).setText(newEntry.getOther());
        ((TextView) findViewById(R.id.textViewExerciseTotal)).setText(newEntry.getExerciseTotal());
        ((TextView) findViewById(R.id.textViewNKITotal)).setText(newEntry.getNKI());
    }
    @Override
    public void onBackPressed(){
        if (Action.getExtras().getString("id").equals("homeKey")){
            Intent home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(home);
            finish();
        }
        else if (Action.getExtras().getString("id").equals("calcultorKey")){
            Intent edit = new Intent(getApplicationContext(), Calculator.class);
            edit.putExtra("id", "diaryKey");
            String index = entries.indexOf(newEntry)+"";
            edit.putExtra("index", index);
            startActivity(edit);
            finish();
        }
    }

    private void loadData() { // stores entries as tokens of type entry using shared preferences and 
        new Thread(new Runnable() {

            @Override
            public void run() {
                SPstore = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = SPstore.getString("task list", null);
                Type type = new TypeToken<ArrayList<EntryObject>>() {}.getType();
                entries = gson.fromJson(json, type);
                if (entries == null) {
                    entries = new ArrayList<>();
                }
            }
        }).start();

    }
}
