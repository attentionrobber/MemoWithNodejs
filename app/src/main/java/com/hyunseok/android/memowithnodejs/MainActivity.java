package com.hyunseok.android.memowithnodejs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hyunseok.android.memowithnodejs.domain.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getData() {
        // 1. 레트로핏을 생성한다.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8070/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 2. 사용할 인터페이스를 설정한다.
        LocalhostInterface service = retrofit.create(LocalhostInterface.class);

        // 3. 데이터를 가져온다
        Call<Data> result = service.getData();

        // 4. 데이터를 가져오는 부분은 네트워크를 통해서 오기때문에 비동기처리된다.
        result.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data>    call, Response<Data> response) {

                // 값이 정상적으로 리턴됐을 경우
                if(response.isSuccessful()) {
                    Data data = response.body(); // 원래 반환값인 jsonString 이 Data Class 로 변환되어 리턴된다. -> JSONOjbect 로 변환할 필요가 없다.

                    // 데이터 저장소에 원격 서버에서 데이터를 저장해둔다.
                    DataStore dataStore = DataStore.getInstance();
                    dataStore.setDatas(data.getData());

                    // 데이터를 가져온 후에 ListActivity를 호출한다.
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(intent);

                    // 로딩화면인 현재 Activity는 종료한다.
                    finish();
                } else {
                    Log.e("Retrofit", response.message());
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }
}
