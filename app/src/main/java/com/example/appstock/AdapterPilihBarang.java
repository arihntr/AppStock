package com.example.appstock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPilihBarang extends RecyclerView.Adapter<AdapterPilihBarang.ViewHolder> {
    Context context;
    int singledata;
    ArrayList<Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public AdapterPilihBarang(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public AdapterPilihBarang.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singledata_display_pilih_barang,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (modelArrayList != null && position < modelArrayList.size()) {
            final Model model = modelArrayList.get(position);
            if (model != null) {
                byte[] image = model.getImageProduct();
                if (image != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    holder.view_image_product.setImageBitmap(bitmap);
                }
                String name = model.getNameProduct();
                if (name != null) {
                    holder.txt_name_product.setText(name);
                }
                int hargaProduct = model.getHargaProduct();
                if (hargaProduct != 0){
                    holder.txt_harga_product.setText(String.valueOf(hargaProduct));
                }
                int hargaJual = model.getHargaJual();
                if (hargaJual != 0){
                    holder.txt_harga_jual.setText(String.valueOf(hargaJual));
                }
                int stockProduct = model.getStockProduct();
                if (stockProduct != 0){
                    holder.txt_stock_product.setText(String.valueOf(stockProduct));
                }
                String descProduct = model.getDescProduct();
                if (descProduct != null) {
                    holder.txt_desc_product.setText(descProduct);
                }
            }

            holder.btn_atur_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", model.getId());
                    bundle.putByteArray("gambar", model.getImageProduct());
                    bundle.putString("name", model.getNameProduct());
                    bundle.putInt("harga_product", model.getHargaProduct());
                    bundle.putInt("harga_jual", model.getHargaJual());
                    bundle.putInt("stock_product", model.getStockProduct());
                    bundle.putString("desc_product", model.getDescProduct());

                    TransaksiBarangMasukKeluarFragment fragment = new TransaksiBarangMasukKeluarFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
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
        ImageView view_image_product;
        TextView txt_name_product, txt_stock_product, txt_harga_product, txt_harga_jual, txt_desc_product;
        Button btn_atur_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view_image_product = itemView.findViewById(R.id.view_image_product);
            txt_name_product = itemView.findViewById(R.id.txt_name_product);
            txt_stock_product = itemView.findViewById(R.id.txt_stock_product);
            txt_harga_product = itemView.findViewById(R.id.txt_harga_product);
            txt_harga_jual = itemView.findViewById(R.id.txt_harga_jual);
            txt_desc_product = itemView.findViewById(R.id.txt_desc_product);
            btn_atur_product = itemView.findViewById(R.id.btn_atur_stock);
        }
    }

}
