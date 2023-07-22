//package com.example.appstock;
//
//import android.content.ContentValues;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.ParcelFileDescriptor;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileDescriptor;
//import java.io.IOException;
//
//public class UploadProduct extends AppCompatActivity {
//
//    private static final int GALLERY_REQUEST_CODE = 100;
//    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
//    private static final int PICK_IMAGE_REQUEST = 100;
//    private static final int PICK_IMAGE = 100;
//    DatabaseHelper databaseHelper;
//    SQLiteDatabase sqLiteDatabase;
//    EditText nameProduct, uploadDescProduct, uploadHargaProduct, uploadStockProduct;
//    ImageView avatar;
//    Button submit, display, edit, back;
//    private Bitmap imageToStore;
//    Uri uri;
//    int id=0;
//
//    public static final int CAMERA_REQUEST=100;
//    public static final int STORAGE_REQUEST=101;
//    String[]cameraPermission;
//    String[]storagePermission;
//    byte[] imageBytes;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload_product);
//        findid();
//        databaseHelper = new DatabaseHelper(this);
//
//        avatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickFromGallery();
//            }
//        });
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (imageBytes != null && nameProduct!=null) {
//                    boolean isInserted = databaseHelper.insertDataProduct(imageBytes, nameProduct.getText().toString(), Integer.valueOf(uploadHargaProduct.getText().toString().trim()), Integer.valueOf(uploadStockProduct.getText().toString()), uploadDescProduct.getText().toString());
//                    databaseHelper.close();
//                    if (isInserted) {
//                        Toast.makeText(UploadProduct.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
//                        finish();
//                    } else {
//                        Toast.makeText(UploadProduct.this, "Invalid, please try again", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
//        editDataProduct();
//        // for storing update data product
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (imageBytes==null){
//                    Bundle bundle = getIntent().getBundleExtra("barang");
//                    byte[] bytes = bundle.getByteArray("gambar");
//                    imageBytes = bytes;
//                }
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("gambar", imageBytes);
//                contentValues.put("name", nameProduct.getText().toString());
//                contentValues.put("harga_product", Integer.valueOf(uploadHargaProduct.getText().toString().trim()));
//                contentValues.put("stock_product", Integer.valueOf(uploadStockProduct.getText().toString().trim()));
//                contentValues.put("desc_product", uploadDescProduct.getText().toString());
//                sqLiteDatabase = databaseHelper.getWritableDatabase();
//                long updateData = sqLiteDatabase.update("barang", contentValues, "id="+id, null);
//                if (updateData != -1) {
//                    Toast.makeText(UploadProduct.this, "Update successfully", Toast.LENGTH_SHORT).show();
//                    edit.setVisibility(View.GONE);
//                    submit.setVisibility(View.VISIBLE);
////                    updateDataInFragmentProduct();
//                    finish();
//                }
//            }
//        });
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//    }
//
//    private void editDataProduct() {
//        if (getIntent().getBundleExtra("barang") != null) {
//            Bundle bundle = getIntent().getBundleExtra("barang");
//            id = bundle.getInt("id");
//            // for image product
//            byte[] bytes = bundle.getByteArray("gambar");
//            if (bytes != null) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                avatar.setImageBitmap(bitmap);
//            }
//            // for name product
//            nameProduct.setText(bundle.getString("name"));
//            // for harga product
//            uploadHargaProduct.setText(String.valueOf(bundle.getInt("harga_product")));
//            // for stock product
//            uploadStockProduct.setText(String.valueOf(bundle.getInt("stock_product")));
//            // for deskripsi product
//            uploadDescProduct.setText(bundle.getString("desc_product"));
//
//            // visible edit button (konfirmasi) and hide submit button (simpan data)
//            submit.setVisibility(View.GONE);
//            edit.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void findid() {
//        avatar = (ImageView) findViewById(R.id.avatar);
//        nameProduct = (EditText) findViewById(R.id.edit_name);
//        submit = (Button) findViewById(R.id.btn_submit);
//        uploadDescProduct = (EditText) findViewById(R.id.uploadDescProduct);
//        uploadHargaProduct = (EditText) findViewById(R.id.uploadHargaProduct);
//        uploadStockProduct = (EditText) findViewById(R.id.uploadStockProduct);
//        edit = (Button) findViewById(R.id.btn_edit);
//        back = (Button) findViewById(R.id.backUploadProduct);
//    }
//
//    private void pickFromGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, GALLERY_REQUEST_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            Uri selectedImageUri = data.getData();
//            avatar.setImageURI(selectedImageUri);
//            if (selectedImageUri != null) {
//                Bitmap bitmap = convertUriToBitmap(selectedImageUri);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
//                if (bitmap != null) {
//                    imageBytes = convertBitmapToByteArray(bitmap);
//                } // imageBytes
//            }
//        }
//    }
//    public Bitmap convertUriToBitmap(Uri uri) {
//        try {
//            ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
//            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
//            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
//            parcelFileDescriptor.close();
//            return bitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//        return outputStream.toByteArray();
//    }
//
//}