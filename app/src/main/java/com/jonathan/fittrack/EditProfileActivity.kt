package com.jonathan.fittrack

import android.content.Context
import android.content.SharedPreferences // Import SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jonathan.fittrack.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    // Menggunakan SharedPreferences sebagai tipe yang tepat, bukan Context
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi SharedPreferences dengan tipe yang benar
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Ambil dan Isi Data Lama ke Form
        loadCurrentData()

        // 2. Logika Tombol Simpan
        binding.btnSaveProfile.setOnClickListener {
            saveNewData()
        }
    }

    private fun loadCurrentData() {
        // Ambil data yang sudah ada di SharedPreferences
        val fullName = sharedPreferences.getString("FULL_NAME", "")
        val email = sharedPreferences.getString("EMAIL", "")
        val age = sharedPreferences.getString("AGE", "")

        // Isi EditText dengan data yang sudah ada
        binding.edtEditFullName.setText(fullName)
        binding.edtEditEmail.setText(email)
        binding.edtEditAge.setText(age)
    }

    private fun saveNewData() {
        val newFullName = binding.edtEditFullName.text.toString()
        val newEmail = binding.edtEditEmail.text.toString()
        val newAge = binding.edtEditAge.text.toString()

        if (newFullName.isEmpty() || newEmail.isEmpty() || newAge.isEmpty()) {
            Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val editor = sharedPreferences.edit()

        // Simpan data baru
        editor.putString("FULL_NAME", newFullName)
        editor.putString("EMAIL", newEmail)
        editor.putString("AGE", newAge)

        editor.apply()

        Toast.makeText(this, "Profile berhasil diperbarui!", Toast.LENGTH_SHORT).show()

        // Kembali ke halaman Profile
        finish()
    }
}