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

public class AdapterTransaksiPemasukan extends RecyclerView.Adapter<AdapterTransaksiPemasukan.ViewHolder> {
    Context context;
    int singledata;
    ArrayList<Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public AdapterTransaksiPemasukan(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public AdapterTransaksiPemasukan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_data_pemasukan,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (modelArrayList != null && position < modelArrayList.size()) {
            final Model model = modelArrayList.get(position);
            if (model != null) {
                String name = model.getNameProduct();
                if (name != null) {
                    holder.tvNamaProductPemasukan.setText(name);
                }
                int hargaProduct = model.getHargaProduct();
                if (hargaProduct != 0){
                    holder.tvHargaProduct.setText(String.valueOf(hargaProduct));
                }
                int qtyBarangMasuk = model.getQtyBarangMasuk();
                if (qtyBarangMasuk != 0){
                    holder.tvQtyBarangMasuk.setText(String.valueOf(qtyBarangMasuk));
                }
                int income = model.getIncome();
                if (income != 0){
                    holder.tvIncome.setText(String.valueOf(income));
                }
            }

            holder.btnDeletePemasukan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    sqLiteDatabase = databaseHelper.getReadableDatabase();
                    long requestDelete = sqLiteDatabase.delete("transaksi_barang_masuk", "id = "+model.getId(), null);
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
        ImageButton btnDeletePemasukan;
        TextView tvNamaProductPemasukan, tvHargaProduct, tvQtyBarangMasuk, tvIncome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDeletePemasukan = itemView.findViewById(R.id.btnDeletePemasukan);
            tvNamaProductPemasukan = itemView.findViewById(R.id.tvNamaProductPemasukan);
            tvHargaProduct = itemView.findViewById(R.id.tvHargaProduct);
            tvQtyBarangMasuk = itemView.findViewById(R.id.tvQtyBarangMasuk);
            tvIncome = itemView.findViewById(R.id.tvIncome);
        }
    }

}
