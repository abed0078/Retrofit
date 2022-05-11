package com.example.retrorequest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrorequest.databinding.ActivityCreateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BaseUrl = "https://jsonplaceholder.typicode.com"
private const val TAG="CreateActivity"
class CreateActivity : AppCompatActivity() {
    lateinit var binding:ActivityCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.buttonSave.setOnClickListener {
            create(binding.EditText.text.toString())
        }

    }
    fun create(title:String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postService = retrofit.create(service::class.java)
        val modal = User(0,0,title,"body")
        postService.createPost(modal)
            .enqueue(object: Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful) {
                        val responseFromAPI: User? = response.body()
                        val responseString = """
                            Response Code : ${response.code()}
                            Name : ${responseFromAPI?.title.toString()}
                          
                            """.trimIndent()

                        // below line we are setting our
                        // string to our text view.

                        // below line we are setting our
                        // string to our text view.
                        binding.textView.setText(responseString)

                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")                }



            })
    }



}