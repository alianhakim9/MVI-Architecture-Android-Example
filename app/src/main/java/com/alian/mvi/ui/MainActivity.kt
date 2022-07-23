package com.alian.mvi.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alian.mvi.databinding.ActivityMainBinding
import com.alian.mvi.model.Blog
import com.alian.mvi.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObserver()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitle(dataState.data)
                }

                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }

                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        }
    }

    private fun displayError(message: String?) {
        with(binding) {
            if (message != null) {
                text.text = message
            } else {
                text.text = "Unknown Error"
            }
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        with(binding) {
            progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
        }
    }

    private fun appendBlogTitle(blogs: List<Blog>) {
        val sb = StringBuffer()
        for (blog in blogs) {
            sb.append(blog.title + "\n")
        }
        binding.text.text = sb.toString()
    }
}