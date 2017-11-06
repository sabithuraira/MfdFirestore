package com.farifam.mfdfirestore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.farifam.mfdfirestore.R
import com.farifam.mfdfirestore.models.Mfd
import java.util.ArrayList

/**
 * Created by sabithuraira on 10/22/17.
 */

class MfdAdapter(private val dataSet: ArrayList<Mfd>, internal var mContext: Context) : ArrayAdapter<Mfd>(mContext, R.layout.row_mfd ,  dataSet) {
    private class ViewHolder {
        internal var mfd_code: TextView? = null
        internal var mfd_prov: TextView? = null
        internal var mfd_kab: TextView? = null
        internal var mfd_kec: TextView? = null
        internal var mfd_desa: TextView? = null
        internal var mfd_bs: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val dataModel = getItem(position)
        val viewHolder: MfdAdapter.ViewHolder

        val result: View

        if (convertView == null) {
            viewHolder = MfdAdapter.ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.row_mfd, parent, false)
            viewHolder.mfd_code = convertView!!.findViewById<TextView>(R.id.mfd_code) as TextView
            viewHolder.mfd_prov = convertView!!.findViewById<TextView>(R.id.mfd_prov) as TextView
            viewHolder.mfd_kab = convertView!!.findViewById<TextView>(R.id.mfd_kab) as TextView
            viewHolder.mfd_kec = convertView!!.findViewById<TextView>(R.id.mfd_kec) as TextView
            viewHolder.mfd_desa = convertView!!.findViewById<TextView>(R.id.mfd_desa) as TextView
            viewHolder.mfd_bs = convertView!!.findViewById<TextView>(R.id.mfd_bs) as TextView

            result = convertView

            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as MfdAdapter.ViewHolder
            result = convertView
        }


        viewHolder.mfd_code!!.setText(dataModel!!.code)
        viewHolder.mfd_prov!!.setText(dataModel!!.prov_no + " - " + dataModel!!.prov_nama)
        viewHolder.mfd_kab!!.setText(dataModel!!.kab_no + " - " + dataModel!!.kab_nama)
        viewHolder.mfd_kec!!.setText(dataModel!!.kec_no + " - " + dataModel!!.kec_nama)
        viewHolder.mfd_desa!!.setText(dataModel!!.desa_no + " - " + dataModel!!.desa_nama)
        viewHolder.mfd_bs!!.setText(dataModel!!.blok_sensus)


        return convertView
    }
}