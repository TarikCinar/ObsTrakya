package com.example.obs.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obs.R;
import com.example.obs.fragment.DuyuruFragment;
import com.example.obs.fragment.DuyuruGosterFragment;
import com.example.obs.fragment.HaberGosterFragment;
import com.example.obs.fragment.NotlaimFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HaberlerAdapter extends RecyclerView.Adapter<HaberlerAdapter.CartViewTutucu> {
    private Context mContext;
    private List<String> baslik;
    private List<String> altBaslik;
    private List<String> link;
    private List<String> resim;

    public HaberlerAdapter(Context mContext, List<String> baslik, List<String> altBaslik,List<String> link,List<String> resim) {
        this.mContext = mContext;
        this.baslik = baslik;
        this.altBaslik = altBaslik;
        this.link=link;
        this.resim=resim;
    }

    public class CartViewTutucu extends RecyclerView.ViewHolder{
        private TextView haberBaslik;
        private TextView HaberAltBaslik;
        private ImageView haberResim;
        private LinearLayout haber;

        public CartViewTutucu(View view){
            super(view);
            haberBaslik=view.findViewById(R.id.haberBaslik);
            HaberAltBaslik=view.findViewById(R.id.haberAltBaslik);
            haberResim=view.findViewById(R.id.haberResim);
            haber=view.findViewById(R.id.haber);

        }

    }

    @NonNull
    @Override
    public CartViewTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.haberler_card_desing,parent,false);
        return new CartViewTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewTutucu holder, final int position) {
        final String title=baslik.get(position);
        final String downTitle=altBaslik.get(position);
        holder.haberBaslik.setText(title);
        holder.HaberAltBaslik.setText(downTitle);
        System.out.println(resim.get(0));
        Picasso.get().load(resim.get(position)).centerCrop(Gravity.CENTER).resize(360,245).into(holder.haberResim);
        holder.haber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity= (AppCompatActivity) v.getContext();
                Fragment fragment = new HaberGosterFragment();
                Bundle bundle = new Bundle();
                bundle.putString("baslik",baslik.get(position));
                bundle.putString("link",link.get(position));
                fragment.setArguments(bundle);
                FragmentManager fm =activity.getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_tutucu, fragment);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return baslik.size();
    }
}
