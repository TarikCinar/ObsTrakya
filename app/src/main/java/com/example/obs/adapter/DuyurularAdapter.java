package com.example.obs.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obs.R;
import com.example.obs.fragment.DuyuruFragment;
import com.example.obs.fragment.DuyuruGosterFragment;
import com.example.obs.fragment.NotlaimFragment;

import java.util.List;

public class DuyurularAdapter extends RecyclerView.Adapter<DuyurularAdapter.CartViewTutucu> {
    private Context mContext;
    private List<String> duyurularList;
    private List<String> duyurularLink;
    private boolean referer=false;

    public DuyurularAdapter(Context mContext, List<String> duyurularList, List<String> duyurularLink,boolean referer) {
        this.mContext = mContext;
        this.duyurularList = duyurularList;
        this.duyurularLink = duyurularLink;
        this.referer=referer;
    }

    public class CartViewTutucu extends RecyclerView.ViewHolder{
        private TextView title;
        private LinearLayout duyuruGit;
        private ImageView duyuruImage;

        public CartViewTutucu(View view){
            super(view);
            title=view.findViewById(R.id.title);
            duyuruGit=view.findViewById(R.id.duyuruGit);
            duyuruImage=view.findViewById(R.id.duyuruImage);

        }

    }

    @NonNull
    @Override
    public CartViewTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_popular_courses,parent,false);
        return new CartViewTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewTutucu holder, final int position) {
        final String baslik=duyurularList.get(position);
        final String result;
        if(baslik.indexOf("YENİ")==0){
            result=baslik.substring(5);
            holder.title.setText(result);
            holder.duyuruImage.setImageResource(R.drawable.new_duyuru);
        }else {
            result=baslik;
            holder.title.setText(result);
            holder.duyuruImage.setImageResource(R.drawable.duyuru_icon);
        }
        holder.duyuruGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity= (AppCompatActivity) v.getContext();
                Fragment fragment = new DuyuruGosterFragment();
                Bundle bundle = new Bundle();
                bundle.putString("baslik",duyurularList.get(position));
                bundle.putString("link",duyurularLink.get(position));
                bundle.putBoolean("referer",referer);
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
        return duyurularList.size();
    }
}
