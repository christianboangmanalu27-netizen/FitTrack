package com.jonathan.fittrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jonathan.fittrack.databinding.ActivityUserSetupBinding

class UserSetup : AppCompatActivity() {

    private lateinit var binding: ActivityUserSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnNext.setOnClickListener {
            val berat = binding.edtWeight.text.toString()
            val tinggi = binding.edtHeight.text.toString()
            val umur = binding.edtAge.text.toString()

            if (berat.isEmpty() || tinggi.isEmpty() || umur.isEmpty()) {
                Toast.makeText(this, "Mohon isi semua data ya!", Toast.LENGTH_SHORT).show()
            } else {

                val weight = berat.toDoubleOrNull()
                val height = tinggi.toIntOrNull()
                val age = umur.toIntOrNull()

                if (weight == null || height == null || age == null || weight <= 0 || height <= 0 || age <= 0) {
                    Toast.makeText(this, "Data harus berupa angka valid (> 0)", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                // Simpan Data
                val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("WEIGHT", berat)
                editor.putString("HEIGHT", tinggi)
                editor.putString("AGE", umur)
                editor.putBoolean("HAS_FILLED_DATA", true)

                editor.apply()

                Toast.makeText(this, "Data tersimpan! Masuk ke Home.", Toast.LENGTH_SHORT).show()

                // Pindah ke MainActivity (Home Page)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}