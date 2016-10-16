package com.tutorials.hp.sqliteswipetorefresh.mRecycler;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutorials.hp.sqliteswipetorefresh.R;
import com.tutorials.hp.sqliteswipetorefresh.mDataBase.DBAdapter;
import com.tutorials.hp.sqliteswipetorefresh.mDataObject.SpaceCraft;

import java.util.ArrayList;

/**
 * Created by Oclemmy on 5/2/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<SpaceCraft> spaceCrafts;
    SwipeRefreshLayout swipeRefreshLayout;

    public MyAdapter(Context c, ArrayList<SpaceCraft> spaceCrafts, SwipeRefreshLayout swipeRefreshLayout) {
        this.c = c;
        this.spaceCrafts = spaceCrafts;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.nameTxt.setText(spaceCrafts.get(position).getName());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUpdates();
            }
        });

    }

    @Override
    public int getItemCount() {
        return spaceCrafts.size();
    }

    private void getUpdates()
    {
        spaceCrafts.clear();

        DBAdapter db=new DBAdapter(c);
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

        swipeRefreshLayout.setRefreshing(false);
        this.notifyDataSetChanged();
    }
}
