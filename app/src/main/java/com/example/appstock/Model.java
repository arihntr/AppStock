package com.example.appstock;

public class Model {
    private int id;
    private byte[] imageProduct;
    private String nameProduct, descProduct, namaSuplier, notlpSuplier, emailSuplier, alamatSuplier;

    private int hargaProduct, hargaJual, stockProduct, qtyBarangMasuk, qtyBarangKeluar, totalHarga, income, outcome, totalHargaBarangMasuk, totalHargaBarangKeluar;
    public Model(){
        this.income = getIncome();
    }

    // Constructor detail product
    public Model(int id, byte[] imageProduct, String nameProduct, int hargaProduct, int hargaJual, int stockProduct, String descProduct) {
        this.id = id;
        this.imageProduct = imageProduct;
        this.nameProduct = nameProduct;
        this.hargaProduct = hargaProduct;
        this.hargaJual = hargaJual;
        this.stockProduct = stockProduct;
        this.descProduct = descProduct;
    }


    // Constructor Suplier
    public Model(int id, String namaSuplier, String notlpSuplier, String emailSuplier, String alamatSuplier){
        this.id = id;
        this.namaSuplier = namaSuplier;
        this.notlpSuplier = notlpSuplier;
        this.emailSuplier = emailSuplier;
        this.alamatSuplier = alamatSuplier;
    }


    // Constructor transaksi barang Masuk
    public Model(int id, String nameProduct, int hargaProductDanJual, int qtyBarangMasukDanKeluar, int incomeOutcome) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.hargaProduct = hargaProductDanJual;
        this.hargaJual = hargaProductDanJual;
        this.qtyBarangMasuk = qtyBarangMasukDanKeluar;
        this.qtyBarangKeluar = qtyBarangMasukDanKeluar;
        this.income = incomeOutcome;
        this.outcome= incomeOutcome;
        this.totalHargaBarangMasuk += incomeOutcome;
        this.totalHargaBarangKeluar += incomeOutcome;
    }





    // setter and getter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(byte[] imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescProduct(){
        return descProduct;
    }
    public void setDescProduct(String descProduct){
        this.descProduct = descProduct;
    }

    public int getHargaProduct(){
        return hargaProduct;
    }

    public void setHargaProduct(int hargaProduct){this.hargaProduct = hargaProduct;
    }

    public int getHargaJual(){
        return hargaJual;
    }

    public void setHargaJual(int hargaJual){this.hargaJual = hargaJual;
    }

    public int getStockProduct(){
        return stockProduct;
    }

    public void setStockProduct(int stockProduct){
        this.stockProduct = stockProduct;
    }

    public int getQtyBarangMasuk() {
        return qtyBarangMasuk;
    }

    public void setQtyBarangMasuk(int qtyBarangMasuk) {
        this.qtyBarangMasuk = qtyBarangMasuk;
    }

    public int getQtyBarangKeluar() {
        return qtyBarangKeluar;
    }

    public void setQtyBarangKeluar(int qtyBarangKeluar) {
        this.qtyBarangKeluar = qtyBarangKeluar;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getOutcome() {
        return outcome;
    }

    public void setOutcome(int outcome) {
        this.outcome = outcome;
    }

    public int getTotalHargaBarangMasuk() {
        return totalHargaBarangMasuk;
    }

    public void setTotalHargaBarangMasuk(int totalHargaProduct, int income) {
        this.totalHargaBarangMasuk += totalHargaProduct*income;
    }

    public int getTotalHargaBarangKeluar() {
        return totalHargaBarangKeluar;
    }

    public void setTotalHargaBarangKeluar(int totalHargaBarangKeluar) {
        this.totalHargaBarangKeluar = totalHargaBarangKeluar;
    }

    public String getNamaSuplier() {
        return namaSuplier;
    }

    public void setNamaSuplier(String namaSuplier) {
        this.namaSuplier = namaSuplier;
    }

    public String getNotlpSuplier() {
        return notlpSuplier;
    }

    public void setNotlpSuplier(String notlpSuplier) {
        this.notlpSuplier = notlpSuplier;
    }

    public String getEmailSuplier() {
        return emailSuplier;
    }

    public void setEmailSuplier(String emailSuplier) {
        this.emailSuplier = emailSuplier;
    }

    public String getAlamatSuplier() {
        return alamatSuplier;
    }

    public void setAlamatSuplier(String alamatSuplier) {
        this.alamatSuplier = alamatSuplier;
    }
}
