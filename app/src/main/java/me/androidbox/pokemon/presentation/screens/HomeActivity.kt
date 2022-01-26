package me.androidbox.pokemon.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.androidbox.pokemon.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
