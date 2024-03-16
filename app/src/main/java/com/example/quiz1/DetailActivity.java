package com.example.quiz1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Ambil data dari Intent
        String namaPembeli = getIntent().getStringExtra("Nama Pembeli");
        String kodeBarang = getIntent().getStringExtra("Kode Barang");
        int jumlahBarang = getIntent().getIntExtra("Jumlah Barang", 0);
        String jenisKeanggotaan = getIntent().getStringExtra("Keanggotaan");
        double totalHarga = getIntent().getDoubleExtra("Total Harga", 0);
        double hargaSatuan = getIntent().getDoubleExtra("Harga Satuan", 0);
        double totalDiskon = 0; // Inisialisasi totalDiskon

        // Hitung diskon dan harga setelah diskon
        double diskonMember = 0;
        if (jenisKeanggotaan != null && !jenisKeanggotaan.isEmpty()) {
            switch (jenisKeanggotaan) {
                case "Gold":
                    diskonMember = totalHarga * 0.1; // Diskon 10% untuk keanggotaan Gold
                    break;
                case "Silver":
                    diskonMember = totalHarga * 0.05; // Diskon 5% untuk keanggotaan Silver
                    break;
                case "Bronze":
                    diskonMember = totalHarga * 0.02; // Diskon 2% untuk keanggotaan Bronze
                    break;
            }
        }

        // Terapkan cashback 100 ribu jika total harga melebihi 10 juta
        double cashback = (totalHarga > 10000000) ? 100000 : 0;

        // Hitung total diskon
        totalDiskon = diskonMember + cashback;

        // Hitung total harga setelah diskon
        double hargaSetelahDiskon = totalHarga - totalDiskon;

        // Tampilkan data pada TextView
        TextView tvNamaPembeli = findViewById(R.id.tv_nama_pembeli);
        tvNamaPembeli.setText("" + namaPembeli);

        TextView tvDetailBarang = findViewById(R.id.tv_detail_barang);
        tvDetailBarang.setText("Kode Barang: " + kodeBarang + " (" + jumlahBarang + ": PCS )");

        TextView tvHargaSatuan = findViewById(R.id.tv_harga_satuan);
        tvHargaSatuan.setText("Harga Satuan: Rp " + new DecimalFormat("#,###").format(hargaSatuan));

        TextView tvJenisKeanggotaan = findViewById(R.id.tv_jenis_keanggotaan);
        tvJenisKeanggotaan.setText("Member : " + jenisKeanggotaan);

        TextView tvDiskonMember = findViewById(R.id.tv_diskon_member);
        tvDiskonMember.setText("Diskon Member : Rp " + new DecimalFormat("#,###").format(diskonMember));

        TextView tvCashback = findViewById(R.id.tv_cashback);
        tvCashback.setText("Cashback : Rp " + new DecimalFormat("#,###").format(cashback));

        TextView tvTotalDiskon = findViewById(R.id.tv_total_diskon);
        tvTotalDiskon.setText("Total Semua Diskon : Rp " + new DecimalFormat("#,###").format(totalDiskon));

        // Tampilkan total harga
        TextView tvTotalHarga = findViewById(R.id.tv_total_harga);
        tvTotalHarga.setText("Total Harga: Rp " + new DecimalFormat("#,###").format(totalHarga));

        final String finalNamaPembeli = namaPembeli;
        final String finalKodeBarang = kodeBarang;
        final int finalJumlahBarang = jumlahBarang;
        final String finalJenisKeanggotaan = jenisKeanggotaan;
        final double finalTotalHarga = totalHarga;
        final double finalHargaSatuan = hargaSatuan;
        final double finalDiskonMember = diskonMember;
        final double finalCashback = cashback;
        final double finalTotalDiskon = totalDiskon;

        Button btnShare = findViewById(R.id.btn_share);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData(finalNamaPembeli, finalKodeBarang, finalJumlahBarang, finalJenisKeanggotaan, finalTotalHarga, finalHargaSatuan, finalDiskonMember, finalCashback, finalTotalDiskon);
            }
        });


    }

    private void shareData(String namaPembeli, String kodeBarang, int jumlahBarang, String jenisKeanggotaan,
                           double totalHarga, double hargaSatuan, double diskonMember, double cashback, final double totalDiskon) {
        // Membuat teks yang akan dibagikan
        String shareText = "Nama Pembeli: " + namaPembeli + "\n" +
                "Kode Barang: " + kodeBarang + "\n" +
                "Jumlah Barang: " + jumlahBarang + "\n" +
                "Jenis Keanggotaan: " + jenisKeanggotaan + "\n" +
                "Total Harga: Rp " + new DecimalFormat("#,###").format(totalHarga) + "\n" +
                "Harga Satuan: Rp " + new DecimalFormat("#,###").format(hargaSatuan) + "\n" +
                "Diskon Member: Rp " + new DecimalFormat("#,###").format(diskonMember) + "\n" +
                "Cashback: Rp " + new DecimalFormat("#,###").format(cashback) + "\n" +
                "Total Semua Diskon: Rp " + new DecimalFormat("#,###").format(totalDiskon);

        // Membuat Intent untuk berbagi teks
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, "Bagikan via"));
    }
}
