package com.jonathan.fittrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jonathan.fittrack.databinding.ActivityProfileBinding

class Profile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Setup Binding
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Contoh action untuk tombol edit (terhubung ke EditProfileActivity yang sudah dibuat)
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Catatan: loadProfileData dipanggil di onResume()
    }

    // Dipanggil setiap kali Activity terlihat (termasuk setelah kembali dari EditProfile)
    override fun onResume() {
        super.onResume()
        loadProfileData()
    }

    private fun loadProfileData() {
        // Akses data yang sudah disimpan saat Register dan UserSetup
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Ambil data dari input awal
        val fullName = sharedPreferences.getString("FULL_NAME", "Guest User")
        val email = sharedPreferences.getString("EMAIL", "Email Not Set")
        val age = sharedPreferences.getString("AGE", "N/A")
        val weight = sharedPreferences.getString("WEIGHT", "0.0") // Data BB
        val height = sharedPreferences.getString("HEIGHT", "0")   // Data TB

        // Tampilkan data di TextViews
        binding.tvProfileName.text = fullName
        binding.tvDetailEmail.text = email
        binding.tvDetailAge.text = "$age Years"

        // Tampilkan BB dan TB
        binding.tvDetailWeight.text = "$weight Kg"
        binding.tvDetailHeight.text = "$height Cm"

        // Untuk field Birthday (kita gunakan data Umur karena input tanggal lahir ditiadakan)
        binding.tvBirthday.text = "$age Years Old"
    }
}