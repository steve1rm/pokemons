package me.androidbox.pokemon.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.androidbox.pokemon.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runBlocking {
            coroutineTestResults()
        }
    }

    private suspend fun coroutineTestResults() {
        GlobalScope.launch {
            delay(2000)
        }
    }

    // 1
    private fun complexMethod(
        name: String,
        email: String,
        phone: String,
        address: String,
        zipCode: String,
        city: String,
        country: String
    ): String {
        return name
    }

    // 2
    private fun emptyMethod() {}

    // 3
    override fun toString(): String {
        throw IllegalStateException()
    }

    // 4
    fun performanceIssues() {
        (1..19).forEach {
            print(it.toString())
        }
    }

    // 5
    fun potentialBugs() {
        val test = when ("type") {
            "main" -> 1
            "main" -> 2
            else -> 3
        }
    }
}