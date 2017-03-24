package com.hyunseok.android.memowithnodejs;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hyunseok.android.memowithnodejs.domain.Data;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setList();
    }

    ListView listView;
    CustomAdapter adapter;
    List<Data.Qna> datas;
    private void setList() {
        listView = (ListView) findViewById(R.id.listView);
        DataStore dataStore = DataStore.getInstance();
        datas = dataStore.getDatas();
        adapter = new CustomAdapter(this, datas);
        listView.setAdapter(adapter);
    }
}

class CustomAdapter extends BaseAdapter {

    List<Data.Qna> datas;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(Context context, List<Data.Qna> datas) {
        this.context = context;
        this.datas = datas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        Data.Qna qna = datas.get(position);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);

        tv_name.setText(qna.getName());
        tv_title.setText(qna.getTitle());

        return convertView;
    }
}