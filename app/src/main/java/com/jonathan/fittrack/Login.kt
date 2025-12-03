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

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputPassword = binding.edtPassword.text.toString()

            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val userEmail = sharedPreferences.getString("EMAIL", null)
            val userPassword = sharedPreferences.getString("PASSWORD", null)

            val adminEmail = "admin@admin.com"
            val adminPassword = "123456"

            val isAdmin = inputEmail == adminEmail && inputPassword == adminPassword
            val isUser = inputEmail == userEmail && inputPassword == userPassword

            if (isAdmin || isUser) {
                Toast.makeText(this, "Login Berhasil! Welcome.", Toast.LENGTH_SHORT).show()

                val editor = sharedPreferences.edit()
                editor.putBoolean("IS_LOGGED_IN", true)
                editor.apply()

                // === PERUBAHAN DI SINI ===
                // Pindah ke UserSetup dulu untuk isi data BB/TB
                val intent = Intent(this, UserSetup::class.java)
                startActivity(intent)
                finish() // Tutup Login agar tidak bisa kembali
            } else {
                Toast.makeText(this, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}