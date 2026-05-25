package com.example.rby_wwe.Home.pertemuan_3

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.example.rby_wwe.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupDatePicker()
        setupAgamaDropdown()
        setupErrorClearers()

        binding.btnRegister.setOnClickListener {
            if (validateForm()) {
                saveToSharedPreference()
                finish()
            }
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun setupDatePicker() {
        binding.etTglLahir.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, yearSelected, monthOfYear, dayOfMonth ->
                binding.etTglLahir.setText("$dayOfMonth/${monthOfYear + 1}/$yearSelected")
                binding.tilTglLahir.error = null
            }, year, month, day)

            dpd.show()
        }
    }

    private fun setupAgamaDropdown() {
        val agamaList = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Khonghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, agamaList)
        binding.actAgama.setAdapter(adapter)
        binding.actAgama.setOnItemClickListener { _, _, _, _ ->
            binding.tilAgama.error = null
        }
    }

    private fun setupErrorClearers() {
        binding.etNama.doOnTextChanged { _, _, _, _ -> binding.tilNama.error = null }
        binding.etUsername.doOnTextChanged { _, _, _, _ -> binding.tilUsername.error = null }
        binding.etPassword.doOnTextChanged { _, _, _, _ -> binding.tilPassword.error = null }
        binding.etConfirmPassword.doOnTextChanged { _, _, _, _ -> binding.tilConfirmPassword.error = null }
        binding.cgGender.setOnCheckedStateChangeListener { _, _ ->
            binding.tvErrorGender.visibility = View.GONE
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (binding.etNama.text.toString().isEmpty()) {
            binding.tilNama.error = "Nama tidak boleh kosong"
            isValid = false
        }

        if (binding.etTglLahir.text.toString().isEmpty()) {
            binding.tilTglLahir.error = "Pilih tanggal lahir"
            isValid = false
        }

        if (binding.cgGender.checkedChipId == View.NO_ID) {
            binding.tvErrorGender.text = "Pilih jenis kelamin"
            binding.tvErrorGender.visibility = View.VISIBLE
            isValid = false
        }

        if (binding.actAgama.text.toString().isEmpty()) {
            binding.tilAgama.error = "Pilih agama"
            isValid = false
        }

        if (binding.etUsername.text.toString().isEmpty()) {
            binding.tilUsername.error = "Username tidak boleh kosong"
            isValid = false
        }

        val password = binding.etPassword.text.toString()
        if (password.isEmpty()) {
            binding.tilPassword.error = "Password tidak boleh kosong"
            isValid = false
        }

        val confirmPassword = binding.etConfirmPassword.text.toString()
        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Konfirmasi password"
            isValid = false
        } else if (confirmPassword != password) {
            binding.tilConfirmPassword.error = "Password tidak cocok"
            isValid = false
        }

        return isValid
    }

    private fun saveToSharedPreference() {
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val gender = if (binding.chipLaki.isChecked) "Laki-laki" else "Perempuan"

        editor.putString("reg_nama", binding.etNama.text.toString())
        editor.putString("reg_tgl_lahir", binding.etTglLahir.text.toString())
        editor.putString("reg_gender", gender)
        editor.putString("reg_agama", binding.actAgama.text.toString())
        editor.putString("reg_username", binding.etUsername.text.toString())
        editor.putString("reg_password", binding.etPassword.text.toString())
        editor.apply()
    }
}
