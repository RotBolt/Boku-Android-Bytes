package com.pervysage.databindingrecyclerviewdemo

import android.app.AlertDialog
import android.content.Context
import android.databinding.BindingMethod
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pervysage.databindingrecyclerviewdemo.databinding.LayoutDumbassBinding

class DumbAdapter(val list:List<Dumbass>) : RecyclerView.Adapter<DumbAdapter.DumbHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DumbHolder {
        val li = p0.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       return DumbHolder(LayoutDumbassBinding.inflate(li,p0,false))
    }
    override fun getItemCount()=list.size

    override fun onBindViewHolder(p0: DumbHolder, p1: Int) {
        p0.bind(list[p1])
    }
    class DumbHolder(private val binding:LayoutDumbassBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(dumb:Dumbass){
            binding.dumb=dumb
        }

    }
}