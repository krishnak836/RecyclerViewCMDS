package com.krishna.task2app.adapters

import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.util.contains
import androidx.recyclerview.widget.RecyclerView
import com.krishna.task2app.R
import com.krishna.task2app.models.LeftRvModel

class LeftAdapter(
    leftArrayList: ArrayList<LeftRvModel>,
) :
    RecyclerView.Adapter<LeftAdapter.MyViewHolder>() {
    private val leftModelArrayList: ArrayList<LeftRvModel>
    private val selectedItem: SparseBooleanArray = SparseBooleanArray()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox
        var name: TextView

        init {
            checkBox = itemView.findViewById(R.id.checkBox)
            name = itemView.findViewById(R.id.itemName)
            checkBox.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    if (!selectedItem.contains(absoluteAdapterPosition)) {
                        selectedItem.put(absoluteAdapterPosition, true)
                    } else {
                        selectedItem.delete(absoluteAdapterPosition)
                    }
                }
            }
        }
    }

    init {
        leftModelArrayList = leftArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_items, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return leftModelArrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: LeftRvModel = leftModelArrayList[position]
        holder.name.text = currentItem.itemName
        holder.checkBox.isChecked = selectedItem.contains(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: LeftRvModel) {
        leftModelArrayList.add(item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(item: LeftRvModel) {
        leftModelArrayList.remove(item)
        notifyDataSetChanged()
    }

    fun getSelectedItemList(): ArrayList<LeftRvModel> {
        val list = ArrayList<LeftRvModel>()
        for (i in 0 until selectedItem.size()) {
            list.add(leftModelArrayList[selectedItem.keyAt(i)])
        }
        return list
    }
}