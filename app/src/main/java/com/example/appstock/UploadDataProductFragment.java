package com.example.appstock;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
public class UploadDataProductFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 100;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    EditText nameProduct, uploadDescProduct, uploadHargaProduct, uploadHargaJual, uploadStockProduct;
    ImageView avatar;
    Button submit, display, edit, btn_back;
    int id = 0;
    byte[] imageBytes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_data_product, container, false);
        findid(view);
        hideFooter();
        hideToolbar();
        databaseHelper = new DatabaseHelper(getContext());
        // action upload gambar
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
            }
        });
        // action add new product
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageBytes != null && nameProduct != null) {
                    boolean isInserted = databaseHelper.insertDataProduct(imageBytes, nameProduct.getText().toString(), Integer.valueOf(uploadHargaProduct.getText().toString().trim()), Integer.valueOf(uploadHargaJual.getText().toString().trim()), Integer.valueOf(uploadStockProduct.getText().toString()), uploadDescProduct.getText().toString());
                    databaseHelper.close();
                    if (isInserted) {
                        Toast.makeText(getContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        // Refresh fragment or perform any other action
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getContext(), "Invalid, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        editDataProduct();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageBytes == null) {
                    // Get image bytes from the existing bundle
                    Bundle arguments = getArguments();
                    if (arguments != null){
                        Bundle bundle = arguments;
                        byte[] bytes = bundle.getByteArray("gambar");
                        imageBytes = bytes;
                    }
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("gambar", imageBytes);
                contentValues.put("name", nameProduct.getText().toString());
                contentValues.put("harga_product", Integer.valueOf(uploadHargaProduct.getText().toString().trim()));
                contentValues.put("harga_jual", Integer.valueOf(uploadHargaJual.getText().toString().trim()));
                contentValues.put("stock_product", Integer.valueOf(uploadStockProduct.getText().toString().trim()));
                contentValues.put("desc_product", uploadDescProduct.getText().toString());
                sqLiteDatabase = databaseHelper.getWritableDatabase();
                long updateData = sqLiteDatabase.update("barang", contentValues, "id=" + id, null);
                if (updateData != -1) {
                    Toast.makeText(getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                    // Refresh fragment or perform any other action
                    appearBtnSubmit();
                    getActivity().onBackPressed();
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.popBackStack();
            }
        });
        return view;
    }
    public void hideToolbar(){
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }
    private void editDataProduct() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            Bundle bundle = arguments;
            if (bundle != null) {
                id = bundle.getInt("id");
                byte[] bytes = bundle.getByteArray("gambar");
                if (bytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    avatar.setImageBitmap(bitmap);
                }
                String name = bundle.getString("name");
                if (name != null) {
                    nameProduct.setText(name);
                }
                int hargaProduct = bundle.getInt("harga_product");
                uploadHargaProduct.setText(String.valueOf(hargaProduct));
                int hargaJual = bundle.getInt("harga_jual");
                uploadHargaJual.setText(String.valueOf(hargaJual));
                int stockProduct = bundle.getInt("stock_product");
                uploadStockProduct.setText(String.valueOf(stockProduct));
                String descProduct = bundle.getString("desc_product");
                if (descProduct != null) {
                    uploadDescProduct.setText(descProduct);
                    // visible edit button (konfirmasi) and hide submit button (simpan data)
                    appearBtnEdit();
                }
            }
        }
    }
    public void appearBtnSubmit(){
        edit.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
    }
    public void appearBtnEdit(){
        submit.setVisibility(View.GONE);
        edit.setVisibility(View.VISIBLE);
    }
    public  void hideFooter(){
        // Ambil objek BottomNavigationView dari layout
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        // Sembunyikan bottom navigation bar
        bottomNavigationView.setVisibility(View.GONE);
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);
        bottomAppBar.setVisibility(View.GONE);
        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab);
        floatingActionButton.setVisibility(View.GONE);
    }
    private void findid(View view) {
        avatar = view.findViewById(R.id.avatar);
        nameProduct = view.findViewById(R.id.edit_name);
        submit = view.findViewById(R.id.btn_submit);
        uploadDescProduct = view.findViewById(R.id.uploadDescProduct);
        uploadHargaProduct = view.findViewById(R.id.uploadHargaProduct);
        uploadStockProduct = view.findViewById(R.id.uploadStockProduct);
        uploadHargaJual = view.findViewById(R.id.uploadHargaJual);
        edit = view.findViewById(R.id.btn_edit);
        btn_back = view.findViewById(R.id.btn_back);

    }
    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            avatar.setImageURI(selectedImageUri);
            if (selectedImageUri != null) {
                Bitmap bitmap = convertUriToBitmap(selectedImageUri);
                if (bitmap != null) {
                    imageBytes = convertBitmapToByteArray(bitmap);
                }
            }
        }
    }
    public Bitmap convertUriToBitmap(Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
