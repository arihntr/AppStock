package com.example.appstock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PemasukanFragment extends Fragment {

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    AdapterTransaksiPemasukan adapterTransaksiPemasukan;
    TextView tvTotalPemasukan;
    Model model;
    Transaksi transaksi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pemasukan, container, false);
        recyclerView = view.findViewById(R.id.rvListDataPemasukan);
        tvTotalPemasukan = view.findViewById(R.id.tvTotalPemasukan);
        apperFooter();
        databaseHelper = new DatabaseHelper(getContext());


        tvTotalPemasukan.setText(String.valueOf(getTotalIncome()));
        displayData();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        return view;
    }

    private void displayData() {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM transaksi_barang_masuk", null);
        ArrayList<Model> models = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nameProduct = cursor.getString(1);
            int hargaProduct = cursor.getInt(2);
            int qtyBarangMasuk = cursor.getInt(3);
            int income= cursor.getInt(4);
            models.add(new Model(id, nameProduct, hargaProduct, qtyBarangMasuk, income));
        }
        cursor.close();
        databaseHelper.close();

        adapterTransaksiPemasukan = new AdapterTransaksiPemasukan(getContext(), R.layout.list_item_data_pemasukan, models,sqLiteDatabase);
        recyclerView.setAdapter(adapterTransaksiPemasukan);
    }

    public int getTotalIncome() {
        int totalIncome = 0;
        String query = "SELECT SUM(income) FROM transaksi_barang_masuk";
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            totalIncome = cursor.getInt(0);
        }
        cursor.close();
        return totalIncome;
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
}