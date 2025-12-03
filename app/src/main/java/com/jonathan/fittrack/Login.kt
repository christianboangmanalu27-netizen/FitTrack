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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // =============================
        //   LOGIKA TOMBOL LOGIN
        // =============================
        binding.btnLogin.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputPassword = binding.edtPassword.text.toString()

            // 1. Cek apakah kolom kosong
            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ambil data user yg tersimpan dari Register
            val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val userEmail = sharedPreferences.getString("EMAIL", null)
            val userPassword = sharedPreferences.getString("PASSWORD", null)

            // Default admin
            val adminEmail = "admin@admin.com"
            val adminPassword = "123456"

            // Cek admin
            val isAdmin = inputEmail == adminEmail && inputPassword == adminPassword

            // Cek user
            val isUser = inputEmail == userEmail && inputPassword == userPassword

            if (isAdmin || isUser) {
                // LOGIN BERHASIL
                Toast.makeText(this, "Login Berhasil! Welcome.", Toast.LENGTH_SHORT).show()

                // Simpan status login
                val editor = sharedPreferences.edit()
                editor.putBoolean("IS_LOGGED_IN", true)
                editor.apply()

                // =============================
                //  INTENT KE MAIN ACTIVITY
                // =============================
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // LOGIN GAGAL
                Toast.makeText(this, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }

        // =============================
        //     TEKS "SIGN UP"
        // =============================
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}
