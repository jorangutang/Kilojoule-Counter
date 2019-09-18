package com.example.myapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.reflect.TypeToken;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.gson.Gson;
import androidx.appcompat.widget.Toolbar;

import android.widget.EditText;
import android.widget.Button;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;
import android.text.TextWatcher;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;


public class Calculator extends AppCompatActivity {

    ArrayList<EntryObject> EntryList;
    EntryObject newEntry = new EntryObject();
    SharedPreferences sharedPreferences;
    Intent intent;
    int count = -1;

    EditText BreakfastIN, LunchIN, DinnerIN, WalkIN, ExerciseIN, OtherIN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.calculator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BreakfastIN = findViewById(R.id.editTextBreakfast);
        LunchIN = findViewById(R.id.editTextLunch);
        DinnerIN = findViewById(R.id.editTextDinner);

        WalkIN = findViewById(R.id.editTextWalk);
        ExerciseIN = findViewById(R.id.editTextExercise);
        OtherIN = findViewById(R.id.editTextOther);

        intent = this.getIntent();

        
        
        Button ExitBtn = findViewById(R.id.buttonCalcExit);
        ExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeGo = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(HomeGo);
                //clearAll();
            }
        });

        Button ClearBtn = findViewById(R.id.ClearBtn);
        ClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
            }
        });

        
        Button SaveBtn = findViewById(R.id.buttonCalcSave);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count != -1) EntryList.set(count, newEntry);
                else{
                    //EntryList.ensureCapacity(1);
                    EntryList.add(newEntry);
                    switch (count = EntryList.size() - 1) { // changed to switch
                    }
                }
                saveData();

                Intent diaryGO = new Intent(getApplicationContext(), DiaryEntries.class);
                diaryGO.putExtra("id", "calcultorKey");
                diaryGO.putExtra("index", count+""); //change happened
                startActivity(diaryGO);
                finish(); //might not be needed
            }
        });


        if(intent.getExtras().getString("id").equals("diaryKey")){
            switch (count = parseInt(intent.getExtras().getString("index"))) {
            }
            newEntry = EntryList.get(count);
            edit();
            updateTotals();
        }
        else{
            newEntry = new EntryObject();
        }



        TextWatcher watch = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateEntry();
                updateTotals();
            }
        };

        BreakfastIN.addTextChangedListener(watch);
        LunchIN.addTextChangedListener(watch);
        DinnerIN.addTextChangedListener(watch);
        WalkIN.addTextChangedListener(watch);
        ExerciseIN.addTextChangedListener(watch);
        OtherIN.addTextChangedListener(watch);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed(){
        if (intent.getExtras().getString("id").equals("diaryKey")){
            Intent diaryGO = new Intent(getApplicationContext(), DiaryEntries.class);
            startActivity(diaryGO);
        }
        else if (intent.getExtras().getString("id").equals("homeKey")){
            Intent homeGO = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(homeGO);
        }
    }

    public void edit(){
        BreakfastIN.setText(newEntry.getBreakfast());
        LunchIN.setText(newEntry.getLunch());
        DinnerIN.setText(newEntry.getDinner());
        WalkIN.setText(newEntry.getWalk());
        ExerciseIN.setText(newEntry.getExercise());
        OtherIN.setText(newEntry.getOther());
    }

    public void updateEntry(){
        EditText breakfastText = findViewById(R.id.editTextBreakfast);
        String breakfast;
        if (breakfastText.getText().length() != 0) breakfast = breakfastText.getText().toString();
        else breakfast = 0 + "";
        newEntry.setBreakfast(breakfast);

        EditText lunchText = findViewById(R.id.editTextLunch);
        String lunch;
        if (lunchText.getText().length() != 0) lunch = lunchText.getText().toString();
        else lunch = 0 + "";
        newEntry.setLunch(lunch);

        EditText dinnerText = findViewById(R.id.editTextDinner);
        String dinner;
        if (dinnerText.getText().length() != 0) dinner = dinnerText.getText().toString();
        else dinner = 0 + "";
        newEntry.setDinner(dinner);

        EditText WalkText = findViewById(R.id.editTextWalk);
        String Walk;
        if (WalkText.getText().length() != 0) Walk = WalkText.getText().toString();
        else Walk = 0 + "";
        newEntry.setWalk(Walk);

        EditText ExerciseText = findViewById(R.id.editTextExercise);
        String Exercise;
        if (ExerciseText.getText().length() != 0) Exercise = ExerciseText.getText().toString();
        else Exercise = 0 + "";
        newEntry.setExercise(Exercise);

        EditText otherText = findViewById(R.id.editTextOther);
        String other;
        if (otherText.getText().length() != 0) other = otherText.getText().toString();
        else other = 0 + "";
        newEntry.setOther(other);
    }

    public void clearAll(){
        EditText breakfastText = findViewById(R.id.editTextBreakfast);
        breakfastText.setText("");
        EditText lunchText = findViewById(R.id.editTextLunch);
        lunchText.setText("");
        EditText dinnerText = findViewById(R.id.editTextDinner);
        dinnerText.setText("");
        EditText walkText = findViewById(R.id.editTextWalk);
        walkText.setText("");
        EditText exerciseText = findViewById(R.id.editTextExercise);
        exerciseText.setText("");
        EditText otherText = findViewById(R.id.editTextOther);
        otherText.setText("");
    }

    public void updateTotals(){
        TextView FoodText = findViewById(R.id.textViewFoodTotal);
        FoodText.setText(newEntry.getFoodTotal());
        TextView ExerciseText = findViewById(R.id.textViewExerciseTotal);
        ExerciseText.setText(newEntry.getExerciseTotal());
        TextView NKIText = findViewById(R.id.textViewNKITotal);
        NKIText.setText(newEntry.getNKI());
    }

    private void saveData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor =  sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(EntryList);
                editor.putString("task list", json);
                editor.apply();
            }
        }).start();
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("task list", null);
                Type type = new TypeToken<ArrayList<EntryObject>>() {}.getType();
                EntryList = gson.fromJson(json, type);

                if (EntryList == null) {
                    EntryList = new ArrayList<>();
                }
            }
        }).start();
    }
}
