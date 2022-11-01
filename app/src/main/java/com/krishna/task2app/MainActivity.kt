package com.krishna.task2app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.krishna.task2app.adapters.LeftAdapter
import com.krishna.task2app.adapters.RightAdapter
import com.krishna.task2app.databinding.ActivityMainBinding
import com.krishna.task2app.models.LeftRvModel
import com.krishna.task2app.models.RightRvModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var leftAdapter: LeftAdapter
    private lateinit var rightAdapter: RightAdapter
    private var leftArrayList: ArrayList<LeftRvModel> = ArrayList()
    private var rightArrayList: ArrayList<RightRvModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLeftRv()
        initRightRv()
        onClick()
    }

    private fun checkExistenceInLeft(userInput: String): Boolean {
        var checked = false
        for (i in 0 until leftArrayList.size) {
            if (leftArrayList[i].itemName == userInput) {
                checked = true
                break
            } else {
                checked = false
            }
        }
        return checked
    }

    private fun checkExistenceInRight(userInput: String): Boolean {
        var checked = false
        for (i in 0 until rightArrayList.size) {
            if (rightArrayList[i].itemName == userInput) {
                checked = true
                break
            } else {
                checked = false
            }
        }
        return checked
    }

    private fun onClick() {
        binding.btnAdd.setOnClickListener {
            addItemsToRightRv()
        }
        binding.btnDelete.setOnClickListener {
            if (binding.etEnter.text.trim().toString().isEmpty()) {
                Components.showDialogBox(this, "Please enter the item name")
            } else {
                removeItemFromRightRv()
                Handler(Looper.getMainLooper()).postDelayed({
                    removeItemFromLeftRv()
                }, 1000)
            }
        }
        binding.btnCopyToRight.setOnClickListener {
            val list = leftAdapter.getSelectedItemList()
            for (i in 0 until list.size) {
                rightAdapter.addItem(RightRvModel(list[i].itemName))
            }
            Log.e("listOfSelectedItem", leftAdapter.getSelectedItemList().toString())

        }
        binding.btnCopyToLeft.setOnClickListener {
            val list = rightAdapter.getSelectedItemList()
            for (i in 0 until list.size) {
                leftAdapter.addItem(LeftRvModel(list[i].itemName))
            }
            Log.e("listOfSelectedItem", rightAdapter.getSelectedItemList().toString())

        }
        binding.btnMoveToRight.setOnClickListener {
            val list = leftAdapter.getSelectedItemList()
            for (i in 0 until list.size) {
                rightAdapter.addItem(RightRvModel(list[i].itemName))
//                leftAdapter.deleteItem(LeftRvModel(list[i].itemName))
            }
            Log.e("listOfSelectedItem", leftAdapter.getSelectedItemList().toString())
        }
    }

    private fun removeItemFromLeftRv() {
        if (checkExistenceInLeft(binding.etEnter.text.trim().toString())) {
            leftAdapter.deleteItem(LeftRvModel(binding.etEnter.text.trim().toString()))
            binding.etEnter.text.clear()
        } else {
            Components.showDialogBox(this, "Item not exist. please enter a valid item")
        }
    }

    private fun removeItemFromRightRv() {
        if (checkExistenceInRight(binding.etEnter.text.trim().toString())) {
            rightAdapter.deleteItem(RightRvModel(binding.etEnter.text.trim().toString()))
            binding.etEnter.text.clear()
        } else {
            Components.showDialogBox(this, "Item not exist. please enter a valid item")
        }
    }

    private fun addItemsToRightRv() {
        if (binding.etEnter.text.trim().toString().isEmpty()) {
            Components.showDialogBox(this, "Please enter the item name")
        } else if (checkExistenceInRight(binding.etEnter.text.trim().toString())) {
            Components.showDialogBox(this, "Please enter the Unique Item")
            binding.etEnter.text.clear()
        } else {
            rightAdapter.addItem(RightRvModel(binding.etEnter.text.trim().toString()))
            binding.etEnter.text.clear()
        }
    }

    private fun initLeftRv() {
        leftAdapter = LeftAdapter(leftArrayList)
        binding.rvLeft.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = leftAdapter
        }
    }

    private fun initRightRv() {
        rightAdapter = RightAdapter(rightArrayList)
        binding.rvRight.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = rightAdapter
        }
    }
}