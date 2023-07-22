package com.example.appstock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    FloatingActionButton fabTambah;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shorts, container, false);
        fabTambah = view.findViewById(R.id.fabTambah);
        recyclerView = view.findViewById(R.id.recyclerView);
        hideToolbar();
        apperFooter();

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadDataProductFragment uploadDataProductFragment = new UploadDataProductFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, uploadDataProductFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Intent intent = new Intent(getActivity(), UploadProduct.class);
//                startActivity(intent);
//                hideBtnEditAndAppearBtnSubmit();
            }
        });
        databaseHelper = new DatabaseHelper(getContext());
        displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        return view;
    }

    private void displayData() {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM barang", null);
        ArrayList<Model>models = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            byte[]imageProduct=cursor.getBlob(1);
            String nameProduct = cursor.getString(2);
            int hargaProduct = cursor.getInt(3);
            int hargaJual = cursor.getInt(4);
            int stockProduct = cursor.getInt(5);
            String descProduct = cursor.getString(6);
            models.add(new Model(id, imageProduct, nameProduct, hargaProduct, hargaJual, stockProduct, descProduct));
        }
        cursor.close();
        databaseHelper.close();

        myAdapter = new MyAdapter(getContext(), R.layout.singledata_display_product, models,sqLiteDatabase);
        recyclerView.setAdapter(myAdapter);
    }

    // AppearFooter
    public void apperFooter(){
        // Ambil objek BottomNavigationView dari layout
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        // Sembunyikan bottom navigation bar
        bottomNavigationView.setVisibility(View.VISIBLE);
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);
        bottomAppBar.setVisibility(View.VISIBLE);
        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    public void hideToolbar(){
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }

}