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

public class AdapterTransaksiPengeluaran extends RecyclerView.Adapter<AdapterTransaksiPengeluaran.ViewHolder> {
    Context context;
    int singledata;
    ArrayList<Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public AdapterTransaksiPengeluaran(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public AdapterTransaksiPengeluaran.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_data_pengeluaran,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (modelArrayList != null && position < modelArrayList.size()) {
            final Model model = modelArrayList.get(position);
            if (model != null) {
                String name = model.getNameProduct();
                if (name != null) {
                    holder.tvNamaProductKeluar.setText(name);
                }
                int hargaProduct = model.getHargaProduct();
                if (hargaProduct != 0){
                    holder.tvHargaJual.setText(String.valueOf(hargaProduct));
                }
                int qtyBarangMasuk = model.getQtyBarangMasuk();
                if (qtyBarangMasuk != 0){
                    holder.tvQtyBarangKeluar.setText(String.valueOf(qtyBarangMasuk));
                }
                int income = model.getIncome();
                if (income != 0){
                    holder.tvOutcome.setText(String.valueOf(income));
                }
            }

            holder.btnDeletePengeluaran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    sqLiteDatabase = databaseHelper.getReadableDatabase();
                    long requestDelete = sqLiteDatabase.delete("transaksi_barang_keluar", "id = "+model.getId(), null);
                    if (requestDelete!=-1){
                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        // remove position after deleted
                        modelArrayList.remove(holder.getAdapterPosition());
                        //update data
                        notifyDataSetChanged();

                        DetailTransaksiFragment detailTransaksiFragment = new DetailTransaksiFragment();
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, detailTransaksiFragment);
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
        ImageButton btnDeletePengeluaran;
        TextView tvNamaProductKeluar, tvHargaJual, tvQtyBarangKeluar, tvOutcome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDeletePengeluaran = itemView.findViewById(R.id.btnDeletePengeluaran);
            tvNamaProductKeluar = itemView.findViewById(R.id.tvNamaProductKeluar);
            tvHargaJual = itemView.findViewById(R.id.tvHargaJual);
            tvQtyBarangKeluar = itemView.findViewById(R.id.tvQtyBarangKeluar);
            tvOutcome = itemView.findViewById(R.id.tvOutcome);
        }
    }

}
