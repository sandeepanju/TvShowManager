package com.example.tvshowmanager.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tvshowmanager.R
import com.example.tvshowmanager.convertToDate
import com.example.tvshowmanager.databinding.ActivityCreateShowBinding
import com.example.tvshowmanager.pojo.ViewState
import com.example.tvshowmanager.viewModel.CreateMovieViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CreateShowActivity : AppCompatActivity() {
    private val createMovieViewModel: CreateMovieViewModel by viewModel()
    private var currentSelectedDate: Long? = null

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityCreateShowBinding>(
            this,
            R.layout.activity_create_show
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
        listner()
    }

    private fun observeData() {
        createMovieViewModel.movieDataObserver.apply {
            observe(this@CreateShowActivity, {
                when (it) {
                    is ViewState.Success -> {
                        binding.progressIndecator.visibility = View.GONE
                        Toast.makeText(
                            this@CreateShowActivity,
                            R.string.show_created_successfully,
                            Toast.LENGTH_SHORT
                        ).show()
                        backToInitialActivity()
                    }
                    is ViewState.NetworkError -> {
                        binding.progressIndecator.visibility = View.GONE
                        Toast.makeText(
                            this@CreateShowActivity,
                            R.string.connectivity_message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    is ViewState.Error -> {
                        binding.progressIndecator.visibility = View.VISIBLE
                        Toast.makeText(this@CreateShowActivity, it.data, Toast.LENGTH_SHORT).show()
                    }
                    is ViewState.Loading -> {
                        binding.progressIndecator.visibility = View.VISIBLE
                        Log.e("Loading State--", "-------")
                    }
                }
            })
        }
    }

    private fun backToInitialActivity() {
        Handler(mainLooper).postDelayed({
            finish()
        }, 2000)
    }

    private fun listner() {
        binding.etReleaseDate.setOnClickListener {
            showDatePicker()
        }
        binding.btnSaveShow.setOnClickListener {
            if (validateData()) {
                createMovieViewModel.createNewShow(
                    binding.etTvShow.text.toString(),
                    Date(currentSelectedDate!!),
                    binding.etSeason.text.toString()
                )
            }
        }
    }

    private fun validateData(): Boolean {
        binding.apply {
            return when {
                etTvShow.text.isEmpty() -> {
                    Toast.makeText(
                        this@CreateShowActivity,
                        R.string.error_message_show_blank,
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }
                etReleaseDate.text.isEmpty() -> {
                    Toast.makeText(
                        this@CreateShowActivity,
                        R.string.error_message_release_date_blank,
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }
                etSeason.text.isEmpty() -> {
                    Toast.makeText(
                        this@CreateShowActivity,
                        R.string.error_message_season_blank,
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }
                else -> true
            }
        }
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker().setSelection(System.currentTimeMillis()).build()
            .apply {
                addOnPositiveButtonClickListener { dateInMillis -> onDateSelected(dateInMillis) }
            }.show(supportFragmentManager, MaterialDatePicker::class.java.canonicalName)
    }

    private fun onDateSelected(dateInMillis: Long?) {
        currentSelectedDate = dateInMillis
        binding.etReleaseDate.setText(dateInMillis?.let { convertToDate(it) })
    }
}