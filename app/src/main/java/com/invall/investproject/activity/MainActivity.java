package com.invall.investproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.invall.investproject.R;
import com.invall.investproject.model.ContentData;
import com.invall.investproject.viewadapter.AllContentDataRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements AllContentDataRecyclerViewAdapter.ItemClickListener{
    RecyclerView rv;
    AllContentDataRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        adapter = new AllContentDataRecyclerViewAdapter(this, SplashActivity.contentDataList);
        adapter.setClickListener(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(getGridLayoutManager());
    }

    @Override
    public void onItemClick(View view, int position, ContentData content) {
        openContentActivity(content.getContentKey());
    }

    private void openContentActivity(String contentKey){
        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("contentKey", contentKey);
        startActivity(intent);
    }

    private GridLayoutManager getGridLayoutManager (){
        return new GridLayoutManager(this,2);
    }
}