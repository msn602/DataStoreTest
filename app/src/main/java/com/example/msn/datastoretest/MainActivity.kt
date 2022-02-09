package com.example.msn.datastoretest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.msn.datastoretest.data.SettingsDataStore
import com.example.msn.datastoretest.data.dataStore
import com.example.msn.datastoretest.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var preferencesManager : SettingsDataStore
    private var text = ""
    private var text2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = SettingsDataStore(dataStore)
        restorePreferences()
        binding.buttonSave.setOnClickListener {savePreferences()}
        binding.buttonRestore.setOnClickListener {restorePreferences()}
    }

    private fun savePreferences() {
        text=binding.textInputLayout.editText?.text.toString()
        text2=binding.textInputLayout2.editText?.text.toString()

        GlobalScope.launch {
            preferencesManager.saveToPreferencesStore(text, text2)
        }
    }

    private fun restorePreferences() {

        preferencesManager.textDataFlow.asLiveData().observe(this) {
            text=it?:text
            binding.textInputLayout.editText?.setText(it)
        }

        preferencesManager.text2DataFlow.asLiveData().observe(this) {
                text2 = it?:text2
                binding.textInputLayout2.editText?.setText(it)
        }
    }

}
