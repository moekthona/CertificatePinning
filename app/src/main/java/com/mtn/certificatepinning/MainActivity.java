package com.mtn.certificatepinning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String hostname = "test.com";

        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(hostname,"sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
                .build();
        OkHttpClient client =  new OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build();

        final Request request = new Request.Builder()
                .url("https://"+hostname)
                .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("TAG", "onFailure: "+e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("TAG", "onResponse: "+response);
                }
            });

    }
}
