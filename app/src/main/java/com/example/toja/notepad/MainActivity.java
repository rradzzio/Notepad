package com.example.toja.notepad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private DatabaseHelper databaseHelper;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private SQLiteDatabase mDatabase;

    public MainActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRecyclerView();

      /*  databaseHelper = new DatabaseHelper(this);
        mDatabase = databaseHelper.getReadableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).marginResId(R.dimen.activity_margin).build());
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, getAllItems());
        recyclerView.setAdapter(mRecyclerViewAdapter); */

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWriteFragment();
            }
        });
    }

    private void setRecyclerView() {
        databaseHelper = new DatabaseHelper(this);
        mDatabase = databaseHelper.getReadableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).marginResId(R.dimen.activity_margin).build());
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, getAllItems());
        recyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void showWriteFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy, h:mm a");
        String formattedDate = simpleDateFormat.format(date);
        showToast(formattedDate);
        WriteFragment writeFragment = WriteFragment.newInstance(formattedDate);
        writeFragment.show(fragmentManager,"");
    }

    private void showToast(String formatted) {
        Toast.makeText(this, formatted, Toast.LENGTH_SHORT).show();
    }

    public void swap() {
        setRecyclerView();
        mRecyclerViewAdapter.swapCursor(getAllItems());      //recycler view is empty
    }

    public Cursor getAllItems() {
        return mDatabase.query(Note.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Note.COL_ID);
    }
}
