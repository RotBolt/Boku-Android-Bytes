package com.test.startup.ListViewAccordianConstraintSetAnimdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<Dummy>()
        for ( i in 0..20){
            list.add(Dummy("head $i",false))
        }
        lvItems.adapter=DumbAdapter(list,lvItems)

    }
}
