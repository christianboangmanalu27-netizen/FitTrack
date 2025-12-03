package com.jonathan.fittrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jonathan.fittrack.databinding.ActivityAuthRegisterBinding // Pastikan nama Binding Class sesuai dengan nama XML kamu

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityAuthRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // --- Setup Binding ---
        // Kalo ini merah, coba: Build -> Clean Project & Build -> Rebuild Project
        binding = ActivityAuthRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- Setup Insets (sudah menggunakan binding.root) ---
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Back (di header atas)


        // --- TOMBOL SIGN UP (SIMPAN DATA KE MEMORI HP) ---
        binding.btnSignUp.setOnClickListener {
            // Ambil data dari ID yang baru di XML
            val namaDepan = binding.edtFirstName.text.toString()
            val namaBelakang = binding.edtLastName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPass = binding.edtConfirmPassword.text.toString()

            if (namaDepan.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi data wajib (Nama Depan, Email, Password)", Toast.LENGTH_LONG).show()
            } else if (password != confirmPass) {
                Toast.makeText(this, "Password tidak sama!", Toast.LENGTH_SHORT).show()
            } else {

                // --- PROSES SIMPAN DATA (PENGGANTI DATABASE: SharedPrefs) ---
                val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                // Simpan data login yang akan dipakai di halaman Login.kt
                editor.putString("FULL_NAME", "$namaDepan $namaBelakang")
                editor.putString("EMAIL", email)
                editor.putString("PASSWORD", password)
                editor.apply() // Simpan perubahan

                Toast.makeText(this, "Registrasi Berhasil! Silakan Login.", Toast.LENGTH_LONG).show()

                // Lempar kembali ke halaman Login
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Sudah punya akun? Pindah ke Login
        binding.tvAlreadyAccount.setOnClickListener { finish() }
    }
}