package com.example.obs.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.obs.OnBackpressed;
import com.example.obs.R;
import com.example.obs.activity.MainActivity;

public class DanismanFragment extends Fragment implements OnBackpressed {

    private TextView danismanAd,danismanKurum,danismanMail;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.danisman,container,false);
        context=container.getContext();

        danismanAd=root.findViewById(R.id.danismanAd);
        danismanKurum=root.findViewById(R.id.danismanKurum);
        danismanMail=root.findViewById(R.id.danismanMail);

        final SharedPreferences sp=context.getSharedPreferences("bilgiler", Context.MODE_PRIVATE);

        danismanAd.append(sp.getString("danismanAd",""));
        danismanKurum.append(sp.getString("danismanKurum",""));
        danismanMail.append(sp.getString("danismanMail",""));


        danismanMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[] {sp.getString("danismanMail","") });

                startActivity(Intent.createChooser(intent, "Mail uygulamasını seçin"));
            }
        });


        return root;
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getContext(), MainActivity.class));
        return false;
    }
}
