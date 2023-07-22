package com.example.appstock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TransaksiPilihBarang extends Fragment {

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerViewTransaksiBarangMasuk;
    AdapterPilihBarang myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaksi_pilih_barang, container, false);
        recyclerViewTransaksiBarangMasuk = view.findViewById(R.id.recyclerViewTransaksiBarangMasuk);

        databaseHelper = new DatabaseHelper(getContext());
        displayData();
        recyclerViewTransaksiBarangMasuk.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        return view;

    }
    private void displayData() {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM barang", null);
        ArrayList<Model> models = new ArrayList<>();
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
        myAdapter = new AdapterPilihBarang(getContext(), R.layout.singledata_display_pilih_barang, models,sqLiteDatabase);
        recyclerViewTransaksiBarangMasuk.setAdapter(myAdapter);
    }

    // Apper
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
}