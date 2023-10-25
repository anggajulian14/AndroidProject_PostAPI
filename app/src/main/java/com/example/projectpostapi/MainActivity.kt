package com.example.projectpostapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.projectpostapi.api.UserPostRequest
import com.example.projectpostapi.databinding.ActivityMainBinding
import com.example.rahmatantravel.api.RetrofitClient
import com.example.rahmatantravel.api.UserPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val etEmail = binding.email
        val etPassw = binding.password

        binding.send.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassw.text.toString()
            createPost(
                email,
                password
            )
            println("Request sent. Value: ${email}")
            println("Request sent. Value: ${password}")
        }
    }
        private fun createPost(email: String, password: String) {
            RetrofitClient.instance.post(
                UserPostRequest(email = email, password = password)
            ).enqueue(object : Callback<UserPostResponse> {
            override fun onResponse(
                call: Call<UserPostResponse>,
                response: Response<UserPostResponse>
            ) {
                if (response.isSuccessful) {
                    val userPostResponse: UserPostResponse? = response.body()
                    if (userPostResponse != null && userPostResponse.status == "success") {

                        binding.tvResponse.text = "Success: ${email}"

                        println("Request successful. Response: $email")
                        println("Request successful. Response: $password")
                        println("Request successful. Response: ${userPostResponse.response}")
                    } else {

                        binding.tvResponse.text = "Unexpected response"

                        println("Unexpected response: $userPostResponse")
                    }
                } else {

                    binding.tvResponse.text = "Server Error"

                    println("Server error. Status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserPostResponse>, t: Throwable) {

                binding.tvResponse.text = "Request Failed: ${t.message}"

                println("Request failed: ${t.message}")
            }
        })
    }


}