package com.example.obs.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.obs.OnBackpressed;
import com.example.obs.R;
import com.example.obs.activity.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

public class HaberGosterFragment extends Fragment implements OnBackpressed {
    private TextView textViewBaslik,textViewIcerik;
    private String Icerik;
    private String link;
    private Context mContext;
    private String baslik;
    private AVLoadingIndicatorView avi;
    private LinearLayout linear;
    private Context context;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.haber_goster_fragment,container,false);

        context=container.getContext();
        avi=root.findViewById(R.id.avi);
        linear=root.findViewById(R.id.linear);
        linear.setVisibility(View.INVISIBLE);
        mContext=container.getContext();
        textViewBaslik=root.findViewById(R.id.textViewBaslik);
        textViewIcerik=root.findViewById(R.id.textViewIcerik);


        Bundle bundle = this.getArguments();
        assert bundle != null;
        baslik=bundle.getString("baslik");
        link=bundle.getString("link");
        textViewBaslik.setText(baslik);


        Duyurularİcerik icerik= new Duyurularİcerik();
        icerik.execute();



        return root;
    }

    @Override
    public boolean onBackPressed() {

        Intent intent=new Intent(context,MainActivity.class);
        intent.putExtra("haber",true);
        startActivity(intent);
        return true;
    }




    public class Duyurularİcerik extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(link).get();
                final Elements elements=doc.getElementsByClass("text-content");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    textViewIcerik.setText(Html.fromHtml(elements.html(), Html.FROM_HTML_MODE_COMPACT));
                                } else {
                                    textViewIcerik.setText(Html.fromHtml(elements.html()));
                                }
                                avi.setVisibility(View.INVISIBLE);
                                linear.setVisibility(View.VISIBLE);
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
