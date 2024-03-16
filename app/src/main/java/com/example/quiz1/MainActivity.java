package com.example.quiz1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalHarga;
    private EditText etItemCode, etItemQuantity, etNamaPembeli;
    private RadioGroup radioGroupMembership;
    private Button btnCheckout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTotalHarga = findViewById(R.id.tv_total_harga);
        etItemCode = findViewById(R.id.et_item_code);
        etItemQuantity = findViewById(R.id.et_item_quantity);
        etNamaPembeli = findViewById(R.id.et_nama_pembeli);
        radioGroupMembership = findViewById(R.id.radioGroupMembership);
        btnCheckout = findViewById(R.id.btn_checkout);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });
    }

    private void checkout() {
        // Ambil data dari input pengguna
        String itemCode = etItemCode.getText().toString();
        int itemQuantity = Integer.parseInt(etItemQuantity.getText().toString());
        String namaPembeli = etNamaPembeli.getText().toString();

        // Ambil keanggotaan yang dipilih
        int selectedMembershipId = radioGroupMembership.getCheckedRadioButtonId();
        RadioButton selectedMembership = findViewById(selectedMembershipId);

        // Hitung harga total berdasarkan kode barang, jumlah barang, dan keanggotaan
        double totalPrice = calculatePrice(itemCode, itemQuantity, selectedMembership.getText().toString());

        // Deklarasikan variabel harga satuan sesuai dengan kode barang yang dipilih
        double hargaSatuan = 0;
        switch (itemCode) {
            case "IPX":
                hargaSatuan = 5725300; // Harga iPhone X
                break;
            case "OAS":
                hargaSatuan = 1989123; // Harga Oppo A5s
                break;
            case "MP3":
                hargaSatuan = 28999999; // Harga Macbook Pro M3
                break;
            // Tambahkan kode barang lain jika diperlukan
        }

        // Buat Intent untuk memulai DetailActivity dan sertakan semua data yang relevan
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("Nama Pembeli", namaPembeli);
        intent.putExtra("Kode Barang", itemCode);
        intent.putExtra("Jumlah Barang", itemQuantity);
        intent.putExtra("Keanggotaan", selectedMembership.getText().toString());
        intent.putExtra("Total Harga", totalPrice);
        intent.putExtra("Harga Satuan", hargaSatuan); // Sertakan harga satuan ke dalam intent


        // Mulai DetailActivity
        startActivity(intent);
    }

    private double calculatePrice(String itemCode, int itemQuantity, String keanggotaan) {
        double totalPrice = 0;

        // Implementasikan logika perhitungan harga berdasarkan kode barang
        switch (itemCode) {
            case "IPX":
                totalPrice += 5725300 * itemQuantity; // Harga iPhone X
                break;
            case "OAS":
                totalPrice += 1989123 * itemQuantity; // Harga Oppo A5s
                break;
            case "MP3":
                totalPrice += 28999999 * itemQuantity; // Harga Macbook Pro M3
                break;
            // Tambahkan kode barang lain jika diperlukan
        }

        // Jika total harga pembelian melebihi 10 juta, terapkan diskon 100.000
        if (totalPrice > 10000000) {
            totalPrice -= 100000;
        }

        // Implementasikan logika diskon berdasarkan keanggotaan
        double discountPercentage = 0; // Default: tidak ada diskon
        switch (keanggotaan) {
            case "Gold":
                discountPercentage = 0.1; // Diskon 10% untuk keanggotaan Gold
                break;
            case "Silver":
                discountPercentage = 0.05; // Diskon 5% untuk keanggotaan Silver
                break;
            case "Bronze":
                discountPercentage = 0.02; // Diskon 2% untuk keanggotaan Bronze
                break;
            // Tambahkan jenis keanggotaan lain jika diperlukan
        }

        // Terapkan diskon ke harga total
        totalPrice -= totalPrice * discountPercentage;

        return totalPrice;
    }
}
