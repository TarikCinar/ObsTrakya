package com.example.obs.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.obs.R;
import com.example.obs.activity.MainActivity;
import com.example.obs.sqlite.Bilgiler;
import com.example.obs.sqlite.BilgilerDao;
import com.example.obs.sqlite.BilgilerYardimci;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;



public class ProfilFragment extends Fragment {

    private Context context;
    private TextView profilIsim;
    private Button duzenle;
    public static final int PICK_IMAGE = 1;
    private CircleImageView profilImage;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private BilgilerYardimci yardimci;
    private String numara;
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.profil,container,false);
        context=container.getContext();
        profilImage=root.findViewById(R.id.profilImage);
        SharedPreferences sp=context.getSharedPreferences("bilgiler",Context.MODE_PRIVATE);

        String login = sp.getString("username","0") + ":" + sp.getString("password","0");
        final String base64login = new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));




        yardimci=new BilgilerYardimci(context);
         sp=context.getSharedPreferences("bilgiler",Context.MODE_PRIVATE);
        numara=sp.getString("username","null");

        duzenle=root.findViewById(R.id.duzenle);

         final ArrayList<Bilgiler> bilgilers=new BilgilerDao().bilgiAl(yardimci,sp.getString("username",""));



        String resim ="";
        for (Bilgiler bil:bilgilers){
            resim=bil.getResim();
        }

        if (resim.equals("null")){

            final OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Request newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", "Basic " + base64login)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            Picasso picasso = new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(client))
                    .build();
            picasso.load("https://obs.trakya.edu.tr/api/ogrenciresim").into(profilImage);
        }else{
            SharedPreferences myPrefrence = getActivity().getSharedPreferences(sp.getString("username",""),context.MODE_PRIVATE);
            String imageS = myPrefrence.getString("imagePreferance", "");
            Bitmap imageB;
            if(!imageS.equals("")) {
                imageB = decodeToBase64(imageS);
                profilImage.setImageBitmap(imageB);
            }
        }


        duzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int permissionCheck = ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
                int readCheck=ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    startGallery();
                }else if(readCheck==PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1500);
                }
                else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
            }
            @SuppressLint("IntentReset")
            private void startGallery() {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });

        profilIsim=root.findViewById(R.id.profilIsim);
        String isimSoyisim=sp.getString("isimSoyisim","");
        profilIsim.setText(isimSoyisim);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                InputStream stream;
                try {
                    SharedPreferences sp=context.getSharedPreferences("bilgiler",Context.MODE_PRIVATE);

                    stream = getActivity().getContentResolver().openInputStream(data.getData());
                    Bitmap realImage = BitmapFactory.decodeStream(stream);
                    profilImage.setImageBitmap(realImage);
                    SharedPreferences myPrefrence = getActivity().getSharedPreferences(sp.getString("username",""),context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = myPrefrence.edit();
                    editor.putString("imagePreferance", encodeToBase64(realImage));

                    editor.commit();

                    String rawQuery=String.format("UPDATE bilgiler SET resim='%s' WHERE okulNo='%s'","true",sp.getString("username",""));
                    new BilgilerDao().update(yardimci,rawQuery);

                    startActivity(new Intent(context,MainActivity.class));
                }
                catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


}
