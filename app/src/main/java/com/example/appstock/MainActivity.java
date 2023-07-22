package com.example.appstock;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appstock.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.toolbar.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()){
                case R.id.toolbar_product:
                    Toast.makeText(this, "Create a new Product", Toast.LENGTH_SHORT).show();
                    replaceFragment(new ProductFragment());
                    break;
                case R.id.toolbar_analytics:
                    replaceFragment(new InsightFragment());
                    break;
            }
            return true;
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.shorts:
                    binding.toolbar.setVisibility(View.GONE);
                    replaceFragment(new ProductFragment());
                    break;
                case R.id.subscription:
                    replaceFragment(new InsightFragment());
                    break;
                case R.id.library:
                    replaceFragment(new AkunFragment());
                    break;
            }

            return true;

        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showButtonDialog();
            }
        });
    }

    //    // Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.toolbar_product){
//            Toast.makeText(this, "Create a new Product", Toast.LENGTH_SHORT).show();
//        }
//        if (id == R.id.toolbar_suplier){
//            Toast.makeText(this, "Create a new Suplier", Toast.LENGTH_SHORT).show();
//        }
//        if (id == R.id.toolbar_analytics){
//            Toast.makeText(this, "About Analytics", Toast.LENGTH_SHORT).show();
//        }
//        if (id == R.id.toolbar_settings){
//            Toast.makeText(this, "Go to Settings", Toast.LENGTH_SHORT).show();
//        }
//        if (id == R.id.toolbar_search){
//            Toast.makeText(this, "Go to Settings", Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }

    // End of Toolbar

    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showButtonDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        LinearLayout layout_barang_masuk = dialog.findViewById(R.id.layout_barang_masuk);
        LinearLayout layout_barang_keluar = dialog.findViewById(R.id.layout_barang_keluar);
        ImageView cancel_button = dialog.findViewById(R.id.cancel_button);
        ImageView remove = dialog.findViewById(R.id.remove);

        layout_barang_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new TransaksiPilihBarang());
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Data Masuk Barang", Toast.LENGTH_SHORT).show();
            }
        });

        layout_barang_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                replaceFragment(new TransaksiPilihBarang());
                Toast.makeText(MainActivity.this, "Data Keluar Barang", Toast.LENGTH_SHORT).show();
            }
        });


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}