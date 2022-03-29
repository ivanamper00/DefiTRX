package com.defiance.defitrx.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dakuinternational.common.domain.model.DataContent
import com.dakuinternational.common.ui.utils.writeLog
import com.defiance.defitrx.databinding.ItemDetailsBinding

class DetailsAdapter: RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    private var data = mutableListOf<DataContent>()


    class ViewHolder(var binding: ItemDetailsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(data: DataContent) {
            binding.item = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailsBinding
            .inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.setData(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setList(data: List<DataContent>){
        this.data.clear()
        this.data.addAll(data)
        writeLog("Data Passed size [${this.data.size}]")
        notifyDataSetChanged()
    }
}