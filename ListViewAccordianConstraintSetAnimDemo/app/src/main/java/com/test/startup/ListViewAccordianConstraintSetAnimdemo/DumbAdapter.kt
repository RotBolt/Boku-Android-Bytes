package com.test.startup.ListViewAccordianConstraintSetAnimdemo

import android.content.Context
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class DumbAdapter(val list:List<Dummy>,val listView: ListView):BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView=li.inflate(R.layout.layout_item_s,parent,false)
        setListeners(itemView,position,parent.context)
        itemView.findViewById<TextView>(R.id.tvHead).text=getItem(position).head
        return itemView
    }

    private var lastExpandPos=-1

    override fun getItem(position: Int)=list[position]
    override fun getItemId(position: Int)=position.toLong()
    override fun getCount()=list.size

    private fun setListeners(itemView: View,position: Int,context:Context){
        itemView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val dummy=list[position]
                Log.i("PUI","onClick : state ${dummy.state}")
                val root = itemView.findViewById<ConstraintLayout>(R.id.rootLayout)
                val layoutS = ConstraintSet()
                layoutS.load(context,R.layout.layout_item_s)
                val layoutL = ConstraintSet()
                layoutL.load(context,R.layout.layout_item_l)
                val constraint = if (dummy.state) layoutS else layoutL
                constraint.applyTo(root)
                if (lastExpandPos!=-1)
                closePrev(context,list[lastExpandPos])
                val transition=ChangeBounds()
                transition.interpolator=OvershootInterpolator()
                TransitionManager.beginDelayedTransition(root,transition)
                dummy.state=!dummy.state
                lastExpandPos=position

            }
        }
    }

    private fun closePrev(context: Context,dummy: Dummy){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val prev = listView.getChildAt(lastExpandPos)
            Log.d("PUI","lastExpand : $lastExpandPos head : ${prev.findViewById<TextView>(R.id.tvHead).text}")
            val root = prev.findViewById<ConstraintLayout>(R.id.rootLayout)
            val layoutS = ConstraintSet()
            layoutS.load(context,R.layout.layout_item_s)
            layoutS.applyTo(root)
            TransitionManager.beginDelayedTransition(root)
            dummy.state=false
        }

    }
}