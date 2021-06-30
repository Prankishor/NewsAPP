package com.example.newsbreeze

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsbreeze.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getNews()
    }

    private fun getNews() {

        //API call
        val news = NewsService.newsInstance.getHeadlines("in", 1)

        binding.progressBar.isVisible = true

        //Response enque, Callback check
        news.enqueue(object: Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val newz : News? = response.body()
                if(newz!=null){

                    binding.progressBar.isVisible = false

                    Log.d("Pk", newz.toString())
                    adapter = NewsAdapter(this@MainActivity, newz.articles)
                    binding.rvNews.adapter = adapter
                    binding.rvNews.layoutManager = LinearLayoutManager(this@MainActivity)

                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Pk", "Error", t)
                binding.progressBar.isVisible = false
            }
        })
    }
}