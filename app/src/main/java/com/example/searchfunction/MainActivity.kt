package com.example.searchfunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.searchfunction.databinding.ActivityMainBinding
import java.util.Locale.filter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var items: ArrayList<Item>
    private lateinit var originalItems: ArrayList<Item>
    private lateinit var rvAdapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRV()
        showList()

        binding.btnReset.setOnClickListener {
            createRV()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filter(p0)
                return false
            }

            private fun filter(p0: String?) {
                var filteredList: ArrayList<Item> = arrayListOf()
                if (p0 != null) {
                    for (item in items) {
                        if (item.name.contains(p0, true)) {
                            filteredList.add(item)
                        }
                    }
                    items = filteredList // update backend list
                }
                if (p0 == "") {
                    items = originalItems // update backend list
                    filteredList = originalItems
                }
                rvAdapter.filterList(filteredList)

                showList() // DEBUGGING
            }
        })
        return true
    }

    private fun showList() {
        binding.debuggingLayout.removeAllViews()

        for (item in items) {
            val textView = TextView(this)
            textView.text = item.name
            binding.debuggingLayout.addView(textView)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionSearch -> {
                return true
            }
            else -> super.onOptionsItemSelected(item)
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
            Item("Name AA1", 1, "01"),
            Item("Name AA2", 2, "02"),
            Item("Name AA3", 3, "03"),
            Item("Name BB1", 4, "04"),
            Item("Name BB2", 5, "05")
        )
        originalItems = arrayListOf()
        originalItems.addAll(items)
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
                val removedItem = items[viewHolder.adapterPosition]
                items.removeAt(viewHolder.adapterPosition)
                originalItems.remove(removedItem)
                rvAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                showList() // DEBUGGING
            }
        }).attachToRecyclerView(binding.rv)
    }

}