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

public class PengeluaranFragment extends Fragment {
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    AdapterTransaksiPengeluaran adapterTransaksiPengeluaran;
    TextView tvTotalPengeluaran;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pengeluaran, container, false);
        recyclerView = view.findViewById(R.id.rvListDataPengeluaran);
        tvTotalPengeluaran = view.findViewById(R.id.tvTotalPengeluaran);
        apperFooter();
        databaseHelper = new DatabaseHelper(getContext());
        tvTotalPengeluaran.setText(String.valueOf(getTotalOutcome()));
        displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));

        return view;
    }

    private void displayData() {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM transaksi_barang_keluar", null);
        ArrayList<Model> models = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nameProduct = cursor.getString(1);
            int hargaJual = cursor.getInt(2);
            int qtyBarangKeluar = cursor.getInt(3);
            int outcome= cursor.getInt(4);
            models.add(new Model(id, nameProduct, hargaJual, qtyBarangKeluar, outcome));

        }
        cursor.close();
        databaseHelper.close();

        adapterTransaksiPengeluaran = new AdapterTransaksiPengeluaran(getContext(), R.layout.list_item_data_pengeluaran, models,sqLiteDatabase);
        recyclerView.setAdapter(adapterTransaksiPengeluaran);
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

    public int getTotalOutcome() {
        int totalOutcome = 0;
        String query = "SELECT SUM(outcome) FROM transaksi_barang_keluar";
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            totalOutcome = cursor.getInt(0);
        }
        cursor.close();
        return totalOutcome;
    }

}