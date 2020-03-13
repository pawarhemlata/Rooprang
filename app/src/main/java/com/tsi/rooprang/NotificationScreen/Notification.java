package com.tsi.rooprang.NotificationScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tsi.rooprang.R;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {
    private List<NotificationDataModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotificationAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mAdapter = new NotificationAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
    }

    private void prepareMovieData() {
        NotificationDataModel movie = new NotificationDataModel("Hello Shivam","Tata Shivam","Ok Shivam");
        movieList.add(movie);

        movie = new NotificationDataModel("Cloud 1","Cloud 1 1","Cloud 1 2");
        movieList.add(movie);

        movie = new NotificationDataModel("Bye","Bye 1","Bye 2");
        movieList.add(movie);



        mAdapter.notifyDataSetChanged();
    }
}