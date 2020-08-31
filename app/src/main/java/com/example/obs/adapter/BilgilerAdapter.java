package com.example.obs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obs.R;
import com.example.obs.fragment.DanismanFragment;
import com.example.obs.fragment.DuyuruFragment;
import com.example.obs.fragment.NotlaimFragment;
import com.example.obs.fragment.TranskriptFragment;

import java.util.List;

public class BilgilerAdapter extends RecyclerView.Adapter<BilgilerAdapter.CartViewTutucu> {
    private Context mContext;
    private int[] bilgilerList;
    private String[] bilgilerListAd;
    private List<String> linkler;
    private String cookie;


    public BilgilerAdapter(Context mContext, int[] bilgilerList,String[] bilgilerListAd) {
        this.mContext = mContext;
        this.bilgilerList = bilgilerList;
        this.bilgilerListAd = bilgilerListAd;
        this.cookie=cookie;
    }

    public class CartViewTutucu extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private CardView bilgilerCardView;
        private TextView title;


        public CartViewTutucu(View view){
            super(view);
             imageView=view.findViewById(R.id.imageViewBilgiler);
             bilgilerCardView=view.findViewById(R.id.cardViewBilgiler);
             title=view.findViewById(R.id.title);
        }

    }

    @NonNull
    @Override
    public CartViewTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_frutorials,parent,false);
        return new CartViewTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewTutucu holder, final int position) {

        holder.imageView.setImageResource(bilgilerList[position]);
        holder.title.setText(bilgilerListAd[position]);
        holder.bilgilerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0){
                    AppCompatActivity activity= (AppCompatActivity) v.getContext();
                    Fragment fragment = new NotlaimFragment();

                    FragmentManager fm =activity.getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_tutucu, fragment);
                    transaction.commit();

                }else if (position==1){
                    AppCompatActivity activity= (AppCompatActivity) v.getContext();
                    Fragment fragment = new DanismanFragment();

                    FragmentManager fm =activity.getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_tutucu, fragment);
                    transaction.commit();

                }else {
                    AppCompatActivity activity= (AppCompatActivity) v.getContext();
                    Fragment fragment = new TranskriptFragment();

                    FragmentManager fm =activity.getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_tutucu, fragment);
                    transaction.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bilgilerList.length;
    }
}
