package com.example.appstock;

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

import androidx.fragment.app.Fragment;

public class DetailProductFragment extends Fragment {

    TextView detailNameProduct, detailHargaProduct, detailHargaJual, detailStockProduct, detailDescProduct;
    ImageView detailImageProduct;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Button btn_back_detail;
    int id = 0;
    byte[] imageBytes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        findid(view);
        databaseHelper = new DatabaseHelper(getContext());
        detailDataProduct();
        btn_back_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  getActivity().onBackPressed();
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.popBackStack();
            }
        });
        return view;
    }

    public void findid(View view){
        detailImageProduct = view.findViewById(R.id.detailImageProduct);
        detailNameProduct = view.findViewById(R.id.detailNamaProduct);
        detailHargaProduct = view.findViewById(R.id.detailHargaProduct);
        detailHargaJual = view.findViewById(R.id.detailHargaJual);
        detailStockProduct = view.findViewById(R.id.detailStockProduct);
        detailDescProduct = view.findViewById(R.id.detailDescProduct);
        btn_back_detail = view.findViewById(R.id.btn_back_detail);
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
                    detailImageProduct.setImageBitmap(bitmap);
                }
                String name = bundle.getString("name");
                if (name != null) {
                    detailNameProduct.setText(name);
                }
                int hargaProduct = bundle.getInt("harga_product");
                detailHargaProduct.setText(String.valueOf(hargaProduct));
                int hargaJual = bundle.getInt("harga_jual");
                detailHargaJual.setText(String.valueOf(hargaJual));
                int stockProduct = bundle.getInt("stock_product");
                detailStockProduct.setText(String.valueOf(stockProduct));
                String descProduct = bundle.getString("desc_product");
                if (descProduct != null) {
                    detailDescProduct.setText(descProduct);
                }
            }
        }
    }
}