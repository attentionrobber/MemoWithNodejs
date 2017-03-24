package com.hyunseok.android.memowithnodejs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyunseok.android.memowithnodejs.domain.Data;
import com.hyunseok.android.memowithnodejs.domain.Qna;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WriteActivity extends AppCompatActivity {

    EditText et_title, et_content, et_author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        et_author = (EditText) findViewById(R.id.et_author);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 서버로 전송한다.
                AsyncTask<String, Void, String> networkTask = new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        String title = params[0];
                        String name = params[1];
                        String content = params[2];

                        Qna qna = new Qna();
                        qna.setTitle(title);
                        qna.setName(name);
                        qna.setContent(content);

                        Gson gson = new Gson();
                        String jsonString = gson.toJson(qna); // Object를 Json 형태의 String 으로 바꿔준다.

                        String result = Remote.postJSON(MainActivity.SITE_URL + "bbs", jsonString);

                        if("SUCCESS".equals(result)) { // server_beta1.js에 createData 함수에 있는 SUCCESS
                            // 성공적으로 등로가면 내가 쓴 글을 목록에 더해준다.
                            DataStore dataStore = DataStore.getInstance();
                            dataStore.addtData(qna);
                        }

                        return result;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        //Snackbar.make(view, result, Snackbar.LENGTH_LONG);
                        Toast.makeText(WriteActivity.this, result, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                };
                networkTask.execute(et_title.getText().toString()
                        , et_author.getText().toString()
                        , et_content.getText().toString());
            }
        });
    }
}
