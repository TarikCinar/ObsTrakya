package com.example.obs.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.obs.OnBackpressed;
import com.example.obs.R;
import com.example.obs.activity.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import uk.co.senab.photoview.PhotoViewAttacher;

public class TranskriptFragment extends Fragment implements OnBackpressed {
    private Context context;
    private android.os.Handler mHandler = new Handler(Looper.getMainLooper());
    private ImageView imageView;
    private AVLoadingIndicatorView avi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.transkript,container,false);

        context=container.getContext();

        imageView=root.findViewById(R.id.imageView);
        avi=root.findViewById(R.id.avi);

        imageView.setVisibility(View.INVISIBLE);
        avi.setVisibility(View.VISIBLE);

        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(imageView);
        pAttacher.update();

        Basic basic=new Basic();
        basic.execute();
        return root;
    }



    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getContext(), MainActivity.class));
        return false;
    }

    public class Basic extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                SharedPreferences sharedPreferences=context.getSharedPreferences("bilgiler",Context.MODE_PRIVATE);

                String login = sharedPreferences.getString("username","0") + ":" + sharedPreferences.getString("password","0");
                final String base64login = new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));

                final Connection.Response document = Jsoup
                        .connect("https://obs.trakya.edu.tr/api/ogrenci")
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .header("Authorization", "Basic " + base64login)
                        .execute();

                final OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request newRequest = chain.request().newBuilder()
                                        .addHeader("Cookie", "ASP.NET_SessionId=" + document.cookie("ASP.NET_SessionId"))
                                        .build();
                                return chain.proceed(newRequest);
                            }
                        })
                        .build();

                final Picasso picasso = new Picasso.Builder(context)
                        .downloader(new OkHttp3Downloader(client))
                        .build();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        picasso
                                .load("https://obs.trakya.edu.tr/trn01/Png/null.png")
                                .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                avi.setVisibility(View.INVISIBLE);
                                imageView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });


                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
