package com.example.searchfunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.searchfunction.databinding.ActivityMainBinding
import java.util.Locale.filter

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filter(p0)
                return false
            }

            private fun filter(p0: String?) {
                val filteredList: ArrayList<Item> = arrayListOf()
                if (p0 != null) {
                    for (item in items) {
                        if (item.name.contains(p0, true)) {
                            filteredList.add(item)
                        }
                    }
                }
                rvAdapter.filterList(filteredList)
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionSearch -> {
                return true
            } else -> super.onOptionsItemSelected(item)
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
            Item("Name AA1", 1),
            Item("Name AA2", 2),
            Item("Name AA3", 3),
            Item("Name BB1", 4),
            Item("Name BB2", 5)
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