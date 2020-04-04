package com.app.spinnerdemo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class KotlinActivity : AppCompatActivity() {
    private lateinit var countryArray: Array<String>
    private var selectedPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countryArray = resources.getStringArray(R.array.country)


        //define custom width of spinner
        spinnerCountry.dropDownWidth = resources.getDimension(R.dimen._150dp).toInt()

        var spinnerAdapter = MyCustomAdapter(this@KotlinActivity, countryArray)

        spinnerCountry.adapter = spinnerAdapter

        val countrySelectedListener: OnItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(spinner: AdapterView<*>?, container: View,
                                        position: Int, id: Long) {
                selectedPosition = position
                txtSelectedItem.text = countryArray[position]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }
        spinnerCountry.onItemSelectedListener = countrySelectedListener
        spinnerCountry.setSelection(selectedPosition)
        relSpinner.setOnClickListener { spinnerCountry.performClick() }
    }

    inner class MyCustomAdapter(val context: Context, var countryArray: Array<String>) : BaseAdapter() {

        val mInflater: LayoutInflater = LayoutInflater.from(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh: ItemRowHolder
            if (convertView == null) {
                view = mInflater.inflate(R.layout.spinner_custom_text_view, parent, false)
                vh = ItemRowHolder(view)
                view?.tag = vh
            } else {
                view = convertView
                vh = view.tag as ItemRowHolder
            }

            vh.label.text = countryArray.get(position)

            if (countryArray.size == 1) { //only 1 item
                vh.relMain.background = context.resources.getDrawable(R.drawable.all_corners_rounder)
            } else {
                if (0 == position) { //first item
                    vh.relMain.background = context.resources.getDrawable(R.drawable.rounded_top)
                } else if (countryArray.size - 1 == position) {  //last item
                    vh.relMain.background = context.resources.getDrawable(R.drawable.rounded_bottom)
                } else { //other item
                    vh.relMain.background = context.resources.getDrawable(R.drawable.corners_not_rounded)
                }
            }
            if (selectedPosition == position) {
                vh.imgSelected.visibility = View.VISIBLE
            } else {
                vh.imgSelected.visibility = View.GONE
            }
            return view
        }

        override fun getItem(position: Int): Any? {
            return null

        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return countryArray.size
        }

        private inner class ItemRowHolder(row: View?) {
            val label: TextView = row?.findViewById(R.id.spinnerText) as TextView
            val relMain: RelativeLayout = row?.findViewById(R.id.relMain) as RelativeLayout
            val imgSelected: ImageView = row?.findViewById(R.id.imgSelected) as ImageView

        }
    }


}