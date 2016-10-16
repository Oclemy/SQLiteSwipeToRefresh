package com.tutorials.hp.sqliteswipetorefresh;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.tutorials.hp.sqliteswipetorefresh.mDataBase.DBAdapter;
import com.tutorials.hp.sqliteswipetorefresh.mDataObject.SpaceCraft;
import com.tutorials.hp.sqliteswipetorefresh.mRecycler.MyAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText nameEditText;
    Button saveBtn,retrieveBtn;
    ArrayList<SpaceCraft> spaceCrafts=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swiper);

        adapter=new MyAdapter(this,spaceCrafts,swipeRefreshLayout);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayDialog();
            }
        });



    }

    private void displayDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save To Database");
        d.setContentView(R.layout.dialog_layout);

        nameEditText= (EditText) d.findViewById(R.id.nameEditTxt);
        saveBtn= (Button) d.findViewById(R.id.saveBtn);
        retrieveBtn= (Button) d.findViewById(R.id.retrieveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(nameEditText.getText().toString());

            }
        });
        retrieveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getSpaceCrafts();
            }
        });

        d.show();
    }

    private void save(String name)
    {
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        if(db.add(name))
        {
            nameEditText.setText("");
        }else {
            Toast.makeText(this,"Unable to save",Toast.LENGTH_SHORT).show();
        }

        db.closeDB();
    }

    private void getSpaceCrafts()
    {
        spaceCrafts.clear();

        DBAdapter db=new DBAdapter(this);
        db.openDB();
        Cursor c=db.retrieve();

        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String name=c.getString(1);

            SpaceCraft s=new SpaceCraft();
            s.setId(id);
            s.setName(name);

            spaceCrafts.add(s);
        }

        db.closeDB();

        rv.setAdapter(adapter);
    }


}
















