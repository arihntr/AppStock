package com.example.appstock;

public class Transaksi {

    private int id;
    private byte[] imageProduct;
    private String nameProduct, descProduct;

    private int hargaProduct, hargaJual, stockProduct, qtyBarangMasuk, qtyBarangKeluar, totalHarga, income, outcome, totalHargaBarangMasuk, totalHargaBarangKeluar;

    public Transaksi(int id, String nameProduct, int hargaProductDanJual, int qtyBarangMasukDanKeluar, int incomeOutcome) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.hargaProduct = hargaProductDanJual;
        this.hargaJual = hargaProductDanJual;
        this.qtyBarangMasuk = qtyBarangMasukDanKeluar;
        this.qtyBarangKeluar = qtyBarangMasukDanKeluar;
        this.income = incomeOutcome;
        this.outcome= incomeOutcome;
    }

    public void setTotalHargaBarangMasuk(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getHargaProduct() {
        return hargaProduct;
    }

    public void setHargaProduct(int hargaProduct) {
        this.hargaProduct = hargaProduct;
    }

    public int getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(int hargaJual) {
        this.hargaJual = hargaJual;
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
}
