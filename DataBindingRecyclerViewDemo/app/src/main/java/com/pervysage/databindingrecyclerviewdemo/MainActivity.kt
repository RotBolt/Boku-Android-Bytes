package com.pervysage.databindingrecyclerviewdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf(
            Dumbass("Rohan","Maity"),
            Dumbass("Niharika","Srivastav"),
            Dumbass("Rituka","Patwal"),
            Dumbass("Piyush","Aggarwal"),
            Dumbass("Varun","Chopra"),
            Dumbass("Garvit","Taneja")
        )

        val adapter = DumbAdapter(list)
        rvDumbs.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvDumbs.adapter=adapter
    }
}
