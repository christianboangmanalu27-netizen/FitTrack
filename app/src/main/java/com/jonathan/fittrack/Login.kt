package com.jonathan.fittrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jonathan.fittrack.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Setup Binding
        // Kalo ini masih merah, pastikan nama file XML layout kamu adalah: activity_login.xml
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- LOGIKA TOMBOL LOGIN (DENGAN "FAKE DATABASE") ---
        binding.btnLogin.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputPassword = binding.edtPassword.text.toString()

            // 1. Cek apakah kolom kosong
            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                // 2. Ambil data yang tersimpan di "UserPrefs" (dari hasil Register nanti)
                val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val registeredEmail = sharedPreferences.getString("EMAIL", "admin@admin.com") // Default admin
                val registeredPassword = sharedPreferences.getString("PASSWORD", "123456")   // Default password

                // 3. Cek Kecocokan Data
                if (inputEmail == registeredEmail && inputPassword == registeredPassword) {
                    // BERHASIL LOGIN
                    Toast.makeText(this, "Login Berhasil! Welcome.", Toast.LENGTH_SHORT).show()

                    // Simpan status login (biar sistem tau user lagi aktif)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("IS_LOGGED_IN", true)
                    editor.apply()

                    // Pindah ke Halaman Selanjutnya (Input Data / Onboarding)
                    // Pastikan kamu sudah buat Activity InputDataActivity (kalau belum, ganti ke HomeActivity)
                    // val intent = Intent(this, InputDataActivity::class.java)
                    // startActivity(intent)
                    // finish()
                } else {
                    // GAGAL LOGIN
                    Toast.makeText(this, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // --- TEKS SIGN UP ---
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}