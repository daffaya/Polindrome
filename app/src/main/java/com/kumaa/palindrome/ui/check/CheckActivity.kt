package com.kumaa.palindrome.ui.check

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.google.android.material.snackbar.Snackbar
import com.kumaa.palindrome.R
import com.kumaa.palindrome.databinding.ActivityCheckBinding
import com.kumaa.palindrome.ui.home.HomeActivity

@ExperimentalPagingApi
class CheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBinding
    private lateinit var viewModel: CheckViewModel
    private var sentence: String = ""
    private var name: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[CheckViewModel::class.java]
        setupAction()
    }

    private fun setupAction(){
        binding.btnCheck.setOnClickListener {
            sentence = binding.etPalindrome.text.toString()
            if (sentence.isEmpty()){
                showSnackBar(getString(R.string.check_required))
            }else{
                viewModel.performPalindromeCheck(sentence)
            }
        }

        viewModel.checkResult.observe(this) { isPalindrome ->
            val message = if (isPalindrome) {
                getString(R.string.ispalindrome, sentence)
            } else {
                getString(R.string.not_palindrome, sentence)
            }
            showSnackBar(message)
        }

        binding.btnNext.setOnClickListener {
            name = binding.etName.text.toString()
            if (name.isEmpty()) {
                binding.etName.error = getString(R.string.name_required)
                showSnackBar(getString(R.string.name_required))
            } else {
                navigateToHome()
            }
        }

    }

    private fun navigateToHome(){
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(HomeActivity.EXTRA_NAME, name)
        startActivity(intent)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}