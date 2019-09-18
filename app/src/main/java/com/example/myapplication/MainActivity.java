package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mainRecyclerView;
    private EntryObjectAdapter Adapter;
    private RecyclerView.LayoutManager mainLayoutManager;
    ArrayList<EntryObject> entries;
    SharedPreferences SPstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.home_recycler);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buildRecyclerView();
        CalculateAverage();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newEntry = new Intent(getApplicationContext(), Calculator.class);
                newEntry.putExtra("id", "homeKey");
                startActivity(newEntry);
            }
        });
    }

    private void buildRecyclerView(){
        mainRecyclerView = findViewById(R.id.recyclerView);
        mainRecyclerView.setHasFixedSize(true);
        mainLayoutManager = new LinearLayoutManager(this);
        Adapter = new EntryObjectAdapter(entries);
        mainRecyclerView.setLayoutManager(mainLayoutManager);
        mainRecyclerView.setAdapter(Adapter);



        Adapter.setOnItemClickListener(new EntryObjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent view = new Intent(getApplicationContext(), DiaryEntries.class);
                view.putExtra("id", "homeKey");
                view.putExtra("index", (pos)+"");

                startActivity(view);
                finish();
            }
        });
    }

    private void loadData() {
                SPstore = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = SPstore.getString("task list", null);
                Type type = new TypeToken<ArrayList<EntryObject>>() {
                }.getType();
                entries = gson.fromJson(json, type);

                if (entries == null) {
                    entries = new ArrayList<>();
                }
    }

    public void CalculateAverage(){
        final TextView AVG = findViewById(R.id.textViewNKIValue);

        new Thread(new Runnable() {
            public void run() {
                AVG.post(new Runnable() {
                    @SuppressLint("DefaultLocale")
                    public void run() {
                        double TotalNKI = 0;
                        for (int j = 0; j < entries.size(); j++) {
                            TotalNKI += Double.parseDouble(entries.get(j).getNKI());
                        }
                        AVG.setText(String.format("%.2f kJ",  TotalNKI / entries.size()));
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() { // Starting functions go here
        super.onStart();
        CalculateAverage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
//RecyclerView code from "Code in flow"