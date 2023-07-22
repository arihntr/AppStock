package com.example.appstock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SuplierFragment extends Fragment {

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    AdapterTambahSuplier adapterTambahSuplier;
    FloatingActionButton fabTambahSuplier;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suplier, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSuplier);
        fabTambahSuplier = view.findViewById(R.id.fabTambahSuplier);
        fabTambahSuplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahSuplierFragment tambahSuplierFragment = new TambahSuplierFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, tambahSuplierFragment);
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
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM suplier", null);
        ArrayList<Model> models = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String namaSuplier = cursor.getString(1);
            String noTlpSuplier = cursor.getString(2);
            String emailSuplier = cursor.getString(3);
            String alamatSuplier = cursor.getString(4);
            models.add(new Model(id, namaSuplier, noTlpSuplier, emailSuplier, alamatSuplier));
        }
        cursor.close();
        databaseHelper.close();

        adapterTambahSuplier = new AdapterTambahSuplier(getContext(), R.layout.single_item_data_suplier, models,sqLiteDatabase);
        recyclerView.setAdapter(adapterTambahSuplier);
    }
}