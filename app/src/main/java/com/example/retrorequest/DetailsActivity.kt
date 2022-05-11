package com.example.retrorequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.retrorequest.databinding.ActivityDetailsBinding
import com.example.retrorequest.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.file.Files.delete


private const val TAG = "DetailsActivity"
private const val BaseUrl =
    "https://jsonplaceholder.typicode.com"

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val ARG_ITEM_ID = "item_id"
    }

    lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        val bundle: Bundle? = intent.extras
        if (bundle?.containsKey(ARG_ITEM_ID)!!) {
            val id = intent.getIntExtra(ARG_ITEM_ID, 0)
            loadDetails(id)
            update(id)
            delete(id)
        }
    }

    fun update(id: Int) {
        binding.btnUpdate.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


            val title = binding.edTitle.text.toString()
            val body = binding.edBody.text.toString()

            val destinationService = retrofit.create(service::class.java)
            val requestCall = destinationService.putPost(id, title, body)

            requestCall.enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        var updatedDestination = response.body() // Use it or ignore It
                        Toast.makeText(
                            this@DetailsActivity,
                            "Item Updated Successfully", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DetailsActivity,
                            "Failed to update item", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(
                        this@DetailsActivity,
                        "Failed to update item", Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun loadDetails(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val destinationService = retrofit.create(service::class.java)
        val requestCall = destinationService.getPostsDetail(id)

        requestCall.enqueue(object : retrofit2.Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.isSuccessful) {
                    val destination = response.body()
                    destination?.let {
                        binding.edTitle.setText(destination.title)
                        binding.edBody.setText(destination.body)

                    }
                } else {
                    Toast.makeText(
                        this@DetailsActivity,
                        "Failed to retrieve details",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(
                    this@DetailsActivity,
                    "Failed to retrieve details " + t.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun delete(id: Int) {
        binding.btnDelete.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val postService = retrofit.create(service::class.java)
            postService.deletePost(id)

                .enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            finish() // Move back to DestinationListActivity
                            Toast.makeText(
                                this@DetailsActivity,
                                "Successfully Deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@DetailsActivity,
                                "Failed to Delete",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        }

                    }


                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Toast.makeText(this@DetailsActivity, "Failed to Delete", Toast.LENGTH_SHORT)
                            .show()

                    }

                })
        }
    }

    /* fun getMethod() {
    val retrofit = Retrofit.Builder()
        .baseUrl(BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val postService = retrofit.create(service::class.java)
    //postService.groupList("1")
    postService.getPostsDetail(10)

        .enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {


                binding.detailsText.text=response.body().toString()

                }


            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }

        })
}*/
}
