package com.example.appstock;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class TransaksiBarangMasukKeluarFragment extends Fragment {

    TextView txtNamaProduct, txtHargaProduct, txtHargaJual, txtStockProduct, txtDescProduct, quantitynumber, txtIncome, txtOutcome, txtStockUpdate;
    ImageView imageViewProduct;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Button btn_konfirmasi ;
    ImageButton plusquantity, minusquantity;
    CheckBox checkboxBarangMasuk, checkboxBarangKeluar;
    int id = 0, quantity;
    byte[] imageBytes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaksi_barang_masuk_keluar, container, false);
        findid(view);
        databaseHelper = new DatabaseHelper(getContext());
        detailDataProduct();

        minusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity == 0) {
                    Toast.makeText(getContext(), "Cant decrease quantity < 0", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    displayQuantity();
                }
            }
        });
        plusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                displayQuantity();
            }
        });
        btn_konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle arguments = getArguments();
                if (arguments != null) {
                    Bundle bundle = arguments;
                    if (bundle != null){
                        String name = bundle.getString("name");
                        if (name != null) {
                            txtNamaProduct.setText(name);
                        }
                        int stockProduct = bundle.getInt("stock_product");
                        int hargaProduct = bundle.getInt("harga_product");
                        int hargaJual = bundle.getInt("harga_jual");
                        String quantityStr = quantitynumber.getText().toString().trim();
                        if (!quantityStr.isEmpty() && quantityStr.matches("\\d+")) {
                            try {
                                int quantityTransaksi = Integer.parseInt(quantityStr);
                                // Transaksi barang masuk
                                if (checkboxBarangMasuk.isChecked()){
                                    int stockUpdate = stockProduct + quantityTransaksi;
                                    txtStockUpdate.setText(String.valueOf(stockUpdate));
                                    int income = hargaProduct * quantityTransaksi;
                                    txtIncome.setText(String.valueOf(income));
                                    if (quantitynumber !=null && checkboxBarangMasuk !=null){
                                        boolean isInserted = databaseHelper.insertTransaksiMasuk(txtNamaProduct.getText().toString(), hargaProduct, quantityTransaksi, income);
                                        if (isInserted) {
                                            Toast.makeText(getContext(), "Update stock successfully", Toast.LENGTH_SHORT).show();
                                            // update stock to database
                                            if (txtStockUpdate != null){
                                                ContentValues contentValues = new ContentValues();
                                                contentValues.put("stock_product", Integer.valueOf(txtStockUpdate.getText().toString().trim()));
                                                sqLiteDatabase = databaseHelper.getWritableDatabase();
                                                sqLiteDatabase.update("barang", contentValues, "id=" + id, null);
                                                // pindah fragment detail transaksi
                                                DetailTransaksiFragment detailTransaksiFragment = new DetailTransaksiFragment();
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.frame_layout, detailTransaksiFragment);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();
                                            } else {
                                                System.out.println("Invalid, error");
                                            }
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Invalid, please try again", Toast.LENGTH_SHORT).show();
                                    }
                                    // Transaksi barang keluar
                                } else if (checkboxBarangKeluar.isChecked()) {
                                    int stockUpdate = stockProduct - quantityTransaksi;
                                    txtStockUpdate.setText(String.valueOf(stockUpdate));
                                    int outcome = hargaJual * quantityTransaksi;
                                    txtOutcome.setText(String.valueOf(outcome));
                                    if (quantitynumber !=null && checkboxBarangKeluar !=null){
                                        boolean isInserted = databaseHelper.insertTransaksiKeluar(txtNamaProduct.getText().toString(), hargaJual, quantityTransaksi, outcome);
                                        if (isInserted) {
                                            Toast.makeText(getContext(), "Update stock successfully", Toast.LENGTH_SHORT).show();
                                            // update stock to database
                                            if (txtStockUpdate != null){
                                                ContentValues contentValues = new ContentValues();
                                                contentValues.put("stock_product", Integer.valueOf(txtStockUpdate.getText().toString().trim()));
                                                sqLiteDatabase = databaseHelper.getWritableDatabase();
                                                sqLiteDatabase.update("barang", contentValues, "id=" + id, null);
                                                // pindah fragment detail transaksi
                                                DetailTransaksiFragment detailTransaksiFragment = new DetailTransaksiFragment();
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.frame_layout, detailTransaksiFragment);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();
                                            } else {
                                                Toast.makeText(getContext(), "Invalid, please try again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                Toast.makeText(getContext(), "Invalid quantity", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        return view;
    }

    public void findid(View view){
        imageViewProduct = view.findViewById(R.id.imageViewProduct);
        txtNamaProduct = view.findViewById(R.id.txtNamaProduct);
        txtHargaProduct = view.findViewById(R.id.txtHargaProduct);
        txtHargaJual = view.findViewById(R.id.txtHargaJual);
        txtStockProduct = view.findViewById(R.id.txtStockProduct);
        txtDescProduct = view.findViewById(R.id.txtDescProduct);
        txtIncome = view.findViewById(R.id.txtIncome);
        txtOutcome = view.findViewById(R.id.txtOutcome);
        txtStockUpdate = view.findViewById(R.id.txtStockUpdate);
        quantitynumber = view.findViewById(R.id.quantitynumber);
        btn_konfirmasi = view.findViewById(R.id.btn_konfirmasi);
        plusquantity = view.findViewById(R.id.plusquantity);
        minusquantity = view.findViewById(R.id.minusquantity);
        checkboxBarangMasuk = view.findViewById(R.id.checkboxBarangMasuk);
        checkboxBarangKeluar = view.findViewById(R.id.checkboxBarangKeluar);
    }
    private void detailDataProduct() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            Bundle bundle = arguments;
            if (bundle != null) {
                id = bundle.getInt("id");
                byte[] bytes = bundle.getByteArray("gambar");
                if (bytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageViewProduct.setImageBitmap(bitmap);
                }
                String name = bundle.getString("name");
                if (name != null) {
                    txtNamaProduct.setText(name);
                }
                int hargaProduct = bundle.getInt("harga_product");
                txtHargaProduct.setText(String.valueOf(hargaProduct));
                int hargaJual = bundle.getInt("harga_jual");
                txtHargaJual.setText(String.valueOf(hargaJual));
                int stockProduct = bundle.getInt("stock_product");
                txtStockProduct.setText(String.valueOf(stockProduct));
                String descProduct = bundle.getString("desc_product");
                if (descProduct != null) {
                    txtDescProduct.setText(descProduct);
                }
            }
        }
    }

    private void displayQuantity() {
        quantitynumber.setText(String.valueOf(quantity));
    }
}