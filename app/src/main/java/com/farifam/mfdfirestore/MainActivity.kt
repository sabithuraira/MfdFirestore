package com.farifam.mfdfirestore

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import com.farifam.mfdfirestore.adapters.MfdAdapter
import com.farifam.mfdfirestore.models.Mfd
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    var list_mfd= mutableListOf<Mfd>()
    var mfdAdapter : MfdAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        list_mfd.add(Mfd("1673010001001P", "Sumatera Selatan", 16, "Pagar Alam", "73", "Dempo Selatan", "010", "Penjalang", "001", "001P"))
        list_mfd.add(Mfd("1673010001001P", "Sumatera Selatan", 16, "Pagar Alam", "73", "Dempo Selatan", "010", "Penjalang", "001", "002B"))
        list_mfd.add(Mfd("1673010001001P", "Sumatera Selatan", 16, "Pagar Alam", "73", "Dempo Selatan", "010", "Penjalang", "001", "003B"))
        list_mfd.add(Mfd("1673010001001P", "Sumatera Selatan", 16, "Pagar Alam", "73", "Dempo Selatan", "010", "Penjalang", "001", "004B"))
        list_mfd.add(Mfd("1673010001001P", "Sumatera Selatan", 16, "Pagar Alam", "73", "Dempo Selatan", "010", "Penjalang", "001", "005B"))

        mfdAdapter = MfdAdapter(ArrayList(list_mfd), applicationContext)
        listview.setAdapter(mfdAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
