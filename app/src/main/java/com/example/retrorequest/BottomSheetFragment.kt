package com.example.retrorequest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retrorequest.databinding.ModalBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BaseUrl = "https://jsonplaceholder.typicode.com"
private const val TAG="BottomSheetFragment"
class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: ModalBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //  return inflater.inflate(R.layout.modal_bottom_sheet,container,false)
        binding = ModalBottomSheetBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bottomButtonSave.setOnClickListener {
            create(binding.bottomEdText.text.toString())
        }
       /* binding.editPost.setOnClickListener {
            Edit(binding.bottomEdText.text.toString())
        }*/
    }


    fun create(title: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postService = retrofit.create(service::class.java)
        val modal = User(0, 0, title, "body")
        postService.createPost(modal)
            .enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val responseFromAPI: User? = response.body()
                        val responseString = """
                            Response Code : ${response.code()}
                            Name : ${responseFromAPI?.title.toString()}
                          
                            """.trimIndent()

                        // below line we are setting our
                        // string to our text view.

                        // below line we are setting our
                        // string to our text view.
                        binding.bottomTextView.setText(responseString)

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")
                }


            })
    }

    /*fun Edit(title:String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postService = retrofit.create(service::class.java)
        val modal = User(0,0,title,"body")
        postService.putPost(10,modal)
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
                        binding.bottomTextView.setText(responseString)

                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i(TAG, "onFailure $t")                }



            })
    }*/
}



