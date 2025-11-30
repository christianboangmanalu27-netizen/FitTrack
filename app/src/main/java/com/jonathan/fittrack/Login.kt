package com.jonathan.fittrack

import android.os.Bundle
import android.widget.Toast // Tambahkan import ini
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jonathan.fittrack.databinding.ActivityLoginBinding // Import View Binding

class Login : AppCompatActivity() {

    // Deklarasikan variabel binding
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Tetap aktifkan edge-to-edge

        // Langkah 1: Inflate layout menggunakan View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // Langkah 2: Set content view menggunakan root dari binding
        setContentView(binding.root) // Ini menggantikan R.layout.activity_main2

        // Langkah 3: Terapkan window insets listener ke root (ID 'main' Anda)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- INI ADALAH LOGIKA UTAMA ANDA ---

        // 4. Menangani klik pada Tombol Login
        binding.buttonLogin.setOnClickListener {
            // Ambil teks dari input fields
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            // Validasi sederhana
            if (email.isEmpty() || password.isEmpty()) {
                // Tampilkan pesan jika ada yang kosong
                Toast.makeText(this, "Email dan Password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Tambahkan logika login Anda di sini (misal: cek ke Firebase)

                // Untuk sekarang, kita tampilkan Toast saja
                Toast.makeText(this, "Login dengan email: $email", Toast.LENGTH_SHORT).show()

                // Contoh: Jika login sukses, pindah ke halaman lain (misal: HomeActivity)
                // val intent = Intent(this, HomeActivity::class.java)
                // startActivity(intent)
                // finish() // Tutup halaman login
            }
        }

        // 5. Menangani klik pada Teks "Sign Up"
        binding.textViewSignUp.setOnClickListener {
            // Tampilkan pesan Toast (untuk sementara)
            Toast.makeText(this, "Pindah ke halaman Registrasi...", Toast.LENGTH_SHORT).show()

            // TODO: Ganti ini dengan Intent untuk pindah ke Activity Registrasi Anda
            // val intent = Intent(this, RegisterActivity::class.java)
            // startActivity(intent)
        }
    }
}