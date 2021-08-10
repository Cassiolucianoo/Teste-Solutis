package com.cassiolucianodasilva.teste_solutios.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cassiolucianodasilva.teste_solutios.R
import com.cassiolucianodasilva.teste_solutios.model.StatmentModel
import kotlinx.android.synthetic.main.card_bills.view.*
import java.text.SimpleDateFormat

class StatmentAdapter(): RecyclerView.Adapter<StatmentAdapter.StatmentHolder>() {

    private var mStatment: List<StatmentModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatmentHolder {
        val statmentView = LayoutInflater.from(parent.context).inflate(R.layout.card_bills, parent, false)

        return StatmentHolder(statmentView)
    }

    override fun onBindViewHolder(holder: StatmentHolder, position: Int) {
        holder.bindingData(mStatment[position])
    }

    override fun getItemCount(): Int {
        return mStatment.count()
    }

    fun updateStatment(list: List<StatmentModel>){
        mStatment = list
        notifyDataSetChanged()
    }

    inner class StatmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindingData(statment: StatmentModel) {
            val formatter = SimpleDateFormat("dd/MM/yyyy")


            itemView.billDescription.text = statment.description
            itemView.billDate.text = formatter.format(statment.date)
            itemView.card_value.text = formatValue(statment.value)

        }

        /**
         * TODO
         *
         * @param value
         * @return
         */
        private fun formatValue(value: Float): String {
            return if (value > 0 ) {
                "R$ $value".replace(",",".")

            }else {

                itemView.card_value.setTextColor(Color.parseColor("#F60404"))
                val strValue = value.toString()
                strValue[0] + "R$ " + strValue.substring(1, strValue.lastIndex+1).replace(",",".")
            }

        }



    }




}