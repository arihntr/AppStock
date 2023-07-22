package com.example.appstock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    GridLayout topView;
    CardView cardViewProduct, cardViewCategory, cardViewLaporan, cardViewMonitoring, cardViewTransaksi, cardViewSuplier;
    BottomNavigationView bottomNavigationView;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    TextView tvPengeluaranHome, tvPemasukanHome, tvPendapatanBersih;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cardViewProduct = view.findViewById(R.id.cardViewProduct);
        tvPengeluaranHome = view.findViewById(R.id.tvPengeluaranHome);
        tvPemasukanHome = view.findViewById(R.id.tvPemasukanHome);
        tvPendapatanBersih = view.findViewById(R.id.tvPendapatanBersih);

        databaseHelper = new DatabaseHelper(getContext());
        tvPemasukanHome.setText(String.valueOf(getTotalIncome()));
        tvPengeluaranHome.setText(String.valueOf(getTotalOutcome()));

        String pengeluaranHomeStr = tvPengeluaranHome.getText().toString();
        String pemasukanHomeStr = tvPemasukanHome.getText().toString();

        if (TextUtils.isDigitsOnly(pengeluaranHomeStr) && TextUtils.isDigitsOnly(pemasukanHomeStr)) {
            int pengeluaranHome = Integer.parseInt(pengeluaranHomeStr);
            int pemasukanHome = Integer.parseInt(pemasukanHomeStr);

            int pendapatanBersih = pengeluaranHome - pemasukanHome;
            tvPendapatanBersih.setText(String.valueOf(pendapatanBersih));
        } else {
            return null;
        }
        appearToolbar();
        cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideToolbar();
                ProductFragment productFragment = new ProductFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, productFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        cardViewSuplier = view.findViewById(R.id.cardViewSuplier);
        cardViewSuplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideToolbar();
                SuplierFragment suplierFragment = new SuplierFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, suplierFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        cardViewTransaksi = view.findViewById(R.id.cardViewTransaksi);
        cardViewTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideToolbar();
                DetailTransaksiFragment detailTransaksiFragment = new DetailTransaksiFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, detailTransaksiFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        cardViewMonitoring = view.findViewById(R.id.cardViewMonitoring);
        cardViewMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideToolbar();
                InsightFragment insightFragment = new InsightFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, insightFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    // menampilkan jumlah total harga barang keluar
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
    public void appearToolbar(){
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    public void hideToolbar(){
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.toolbar_product){
//            Toast.makeText(this, "Tambah Product Baru", Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }
    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//        return true;
//    }
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

//     End of Toolbar

}