package com.example.appstock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTambahSuplier extends RecyclerView.Adapter<AdapterTambahSuplier.ViewHolder> {
    Context context;
    int singledata;
    ArrayList<Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public AdapterTambahSuplier(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public AdapterTambahSuplier.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_data_suplier,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (modelArrayList != null && position < modelArrayList.size()) {
            final Model model = modelArrayList.get(position);
            if (model != null) {
                String name = model.getNamaSuplier();
                if (name != null) {
                    holder.tvNamaSuplier.setText(name);
                }
                String noTlpSuplier = model.getNotlpSuplier();
                if (noTlpSuplier != null){
                    holder.tvNotlpSuplier.setText(noTlpSuplier);
                }
                String emailSuplier = model.getEmailSuplier();
                if (noTlpSuplier != null){
                    holder.tvEmailSuplier.setText(emailSuplier);
                }
                String alamatSuplier = model.getEmailSuplier();
                if (noTlpSuplier != null){
                    holder.tvAlamatSuplier.setText(alamatSuplier);
                }

            }

            holder.btnDeleteSuplier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    sqLiteDatabase = databaseHelper.getReadableDatabase();
                    long requestDelete = sqLiteDatabase.delete("suplier", "id = "+model.getId(), null);
                    if (requestDelete!=-1){
                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        // remove position after deleted
                        modelArrayList.remove(holder.getAdapterPosition());
                        //update data
                        notifyDataSetChanged();

                        SuplierFragment suplierFragment = new SuplierFragment();
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, suplierFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (modelArrayList != null) {
            return modelArrayList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnDeleteSuplier;
        TextView tvNamaSuplier, tvNotlpSuplier, tvEmailSuplier, tvAlamatSuplier;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDeleteSuplier = itemView.findViewById(R.id.btnDeleteSuplier);
            tvNamaSuplier = itemView.findViewById(R.id.tvNamaSuplier);
            tvNotlpSuplier = itemView.findViewById(R.id.tvNotlpSuplier);
            tvEmailSuplier = itemView.findViewById(R.id.tvEmailSuplier);
            tvAlamatSuplier= itemView.findViewById(R.id.tvAlamatSuplier);
        }
    }

}
