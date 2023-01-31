package com.example.searchfunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.searchfunction.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var items: ArrayList<Item>
    private lateinit var rvAdapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRV()

        binding.btnReset.setOnClickListener {
            createRV()
        }
    }

    private fun createRV() {
        createData()
        rvAdapter = RVAdapter(items)
        binding.rv.adapter = rvAdapter
        swipeFunctions()
    }

    private fun createData() {
        items = arrayListOf(
            Item("Name A1", 1),
            Item("Name A2", 2),
            Item("Name A3", 3),
            Item("Name B1", 4),
            Item("Name B2", 5)
        )
    }
    private fun swipeFunctions() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                items.removeAt(viewHolder.adapterPosition)
                rvAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(binding.rv)
    }

}