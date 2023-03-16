package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat


class SpinnerAdapter(context: Context, private val options: Array<String>) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, options) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        if (view is TextView) {
            view.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        if (view is TextView) {
            view.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
        return view
    }
}
