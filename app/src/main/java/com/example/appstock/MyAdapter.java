package com.example.appstock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    int singledata;
    ArrayList<Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public MyAdapter(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singledata_display_product,null);
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

            holder.flowmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context, holder.flowmenu);
                    popupMenu.inflate(R.menu.flow_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.edit_menu:
                                    // Edit operation
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("id", model.getId());
                                    bundle.putByteArray("gambar", model.getImageProduct());
                                    bundle.putString("name", model.getNameProduct());
                                    bundle.putInt("harga_product", model.getHargaProduct());
                                    bundle.putInt("harga_jual", model.getHargaJual());
                                    bundle.putInt("stock_product", model.getStockProduct());
                                    bundle.putString("desc_product", model.getDescProduct());

                                    UploadDataProductFragment fragment = new UploadDataProductFragment();
                                    fragment.setArguments(bundle);
                                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    break;
//                                    Intent intent = new Intent(context, DataProductFragment.class);
//                                    intent.putExtra("barang", bundle);
//                                    context.startActivity(intent);
//                                    break;
                                case R.id.delete_menu:
                                    // Delete operation
                                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                                    sqLiteDatabase = databaseHelper.getReadableDatabase();
                                    long requestDelete = sqLiteDatabase.delete("barang", "id = "+model.getId(), null);
                                    if (requestDelete!=-1){
                                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                        // remove position after deleted
                                        modelArrayList.remove(holder.getAdapterPosition());
                                        //update data
                                        notifyDataSetChanged();
                                    }
                                    break;
                                default:
                                    return false;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });

            holder.recCard.setOnClickListener(new View.OnClickListener() {
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

                    DetailProductFragment fragment = new DetailProductFragment();
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
        TextView txt_name_product, txt_harga_product, txt_harga_jual, txt_stock_product, txt_desc_product;
        ImageButton flowmenu;

        CardView recCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view_image_product = itemView.findViewById(R.id.view_image_product);
            txt_name_product = itemView.findViewById(R.id.txt_name_product);
            txt_harga_product = itemView.findViewById(R.id.txt_harga_product);
            txt_stock_product = itemView.findViewById(R.id.txt_stock_product);
            txt_desc_product = itemView.findViewById(R.id.txt_desc_product);
            txt_harga_jual = itemView.findViewById(R.id.txt_harga_jual);
            flowmenu = itemView.findViewById(R.id.flowmenu);
            recCard = itemView.findViewById(R.id.recCard);
        }
    }

}
