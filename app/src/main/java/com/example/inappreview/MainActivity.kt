package com.example.inappreview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.android.synthetic.main.activity_main.*

private var reviewInfo: ReviewInfo? = null
private lateinit var reviewManager: ReviewManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reviewManager = ReviewManagerFactory.create(this)

        val requestFlow = reviewManager.requestReviewFlow()
        requestFlow.addOnCompleteListener { request ->
            reviewInfo = if (request.isSuccessful) {
                //Received ReviewInfo object
                request.result
            } else {
                //Problem in receiving object
                null
            }
        }

        button.setOnClickListener {
            reviewInfo?.let {
                val flow = reviewManager.launchReviewFlow(this@MainActivity, it)
                flow.addOnSuccessListener {
                    //Showing toast is only for testing purpose, this shouldn't be implemented
                    //in production app.
                    Toast.makeText(
                            this@MainActivity,
                            "Thanks for the feedback!",
                            Toast.LENGTH_LONG
                    ).show()
                }
                flow.addOnFailureListener {
                    //Showing toast is only for testing purpose, this shouldn't be implemented
                    //in production app.
                    Toast.makeText(this@MainActivity, "${it.message}", Toast.LENGTH_LONG).show()
                }
                flow.addOnCompleteListener {
                    //Showing toast is only for testing purpose, this shouldn't be implemented
                    //in production app.
                    Toast.makeText(this@MainActivity, "Completed!", Toast.LENGTH_LONG).show()
                }
            }
        }


    }

}