package com.example.obs.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obs.OnBackpressed;
import com.example.obs.R;
import com.example.obs.activity.MainActivity;
import com.example.obs.adapter.DuyurularAdapter;
import com.example.obs.sqlite.Duyurular;
import com.example.obs.sqlite.DuyurularDao;
import com.example.obs.sqlite.SqliteYardimci;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DuyuruFragment extends Fragment implements OnBackpressed {

    private ArrayList<String> duyurularList=new ArrayList<>();
    private ArrayList<String> duyurularLink=new ArrayList<>();
    private DuyurularAdapter duyurularAdapter;
    RecyclerView duyurularRv;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    AVLoadingIndicatorView avi;
    private SqliteYardimci sq;


    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.duyuru_fragment,container,false);

        context=container.getContext();

        sq=new SqliteYardimci(context);
        ArrayList<Duyurular> gelenKelimeler=new DuyurularDao().tumKelimeler(sq);

        for (Duyurular d:gelenKelimeler){
            duyurularList.add(d.getDuyuru_ad());
            duyurularLink.add(d.getDuyuru_link());
        }



        duyurularRv = root.findViewById(R.id.duyurularRv);
        duyurularRv.setHasFixedSize(true);
        duyurularRv.setLayoutManager(new LinearLayoutManager(getContext()));
        duyurularAdapter=new DuyurularAdapter(getContext(),duyurularList,duyurularLink,true);
        duyurularRv.setAdapter(duyurularAdapter);


        return root;
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getContext(), MainActivity.class));
        return false;
    }

}
