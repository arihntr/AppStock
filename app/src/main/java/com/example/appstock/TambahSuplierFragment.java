package com.example.appstock;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class TambahSuplierFragment extends Fragment {


    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    EditText namaSuplier, notlpSuplier, emailSuplier, alamatSuplier;
    Button btn_submit_suplier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tambah_suplier, container, false);
        findid(view);

        databaseHelper = new DatabaseHelper(getContext());

        btn_submit_suplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (namaSuplier != null && notlpSuplier != null){
                    boolean isInserted = databaseHelper.insertDataSuplier(namaSuplier.getText().toString(), notlpSuplier.getText().toString(), emailSuplier.getText().toString().trim(), alamatSuplier.getText().toString());
                    databaseHelper.close();
                    if (isInserted){
                        Toast.makeText(getContext(), "Data suplier berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getContext(), "Invalid, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    public void findid(View view){
        namaSuplier = view.findViewById(R.id.namaSuplier);
        notlpSuplier = view.findViewById(R.id.notlpSuplier);
        emailSuplier = view.findViewById(R.id.emailSuplier);
        alamatSuplier = view.findViewById(R.id.alamatSuplier);
        btn_submit_suplier = view.findViewById(R.id.btn_submit_suplier);
    }
}