package com.farifam.mfdfirestore

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import com.farifam.mfdfirestore.adapters.MfdAdapter
import com.farifam.mfdfirestore.models.Mfd
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {
    val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();

    private val db by lazy { FirebaseFirestore.getInstance() }
    var list_member= mutableListOf<Mfd>()
    var dataAdapter : MfdAdapter? = null

    private val FORM_ACTIVITY_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        db.setFirestoreSettings(settings);

        fab.setOnClickListener { view ->
            val intent = Intent(this, FormActivity::class.java)
            startActivityForResult(intent, FORM_ACTIVITY_CODE);
        }

        loadFirestoreDatas()

        val actions = listOf("Update", "Delete")
        listview.setOnItemClickListener { parent, view, position, id ->
            selector(null, actions, { dialogInterface, i ->
                if(i==0) updateData(position)
                else deleteData(position)
            })
        }


//        search.setOnEditorActionListener() { v, actionId, event ->
//            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
//                loadFirestoreDatas()
//                true
//            }
//            false
//        }
    }

    fun deleteData(position: Int){
        db.collection("members").document(list_member[position].code)
                .delete()
                .addOnSuccessListener{
                    loadFirestoreDatas()
                }
                .addOnFailureListener{
                    toast("Failed to delete data, please check your connection")
                }
    }

    fun updateData(position: Int){
        val intent = Intent(this, FormActivity::class.java)
//        intent.putExtra("id", list_member[position].id)
//        intent.putExtra("first_name", list_member[position].first)
//        intent.putExtra("last_name", list_member[position].last)
//        intent.putExtra("born", list_member[position].born)
        startActivityForResult(intent, FORM_ACTIVITY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FORM_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                loadFirestoreDatas()
            }
        }
    }

    fun loadFirestoreDatas(){
        progress.visibility = View.VISIBLE

        val memberCollection = db.collection("members");
        var query : Query = memberCollection.orderBy("first")

//        if(search.text.toString().length>0){
//            query = memberCollection.whereEqualTo("first", search.text.toString())
//
//        }

        query.
                addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(@Nullable querySnapshot: QuerySnapshot,
                                         @Nullable e: FirebaseFirestoreException?) {
                        if (e != null) {
//                            Log.w(FragmentActivity.TAG, "Listen error", e)
                            return
                        }

                        for (change in querySnapshot.documentChanges) {
                            if (change.type == DocumentChange.Type.ADDED) {
//                                Log.d(FragmentActivity.TAG, "New city:" + change.document.data)

                            }

                            val source = if (querySnapshot.metadata.isFromCache)
                                "local cache"
                            else
                                "server"
//                            Log.d(FragmentActivity.TAG, "Data fetched from " + source)
                        }

                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

//    var list_mfd= mutableListOf<Mfd>()
//    var mfdAdapter : MfdAdapter? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
//        setSupportActionBar(toolbar)
//
//        val fab = findViewById(R.id.fab) as FloatingActionButton
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
//
//        list_mfd.add(Mfd("1673010001001P", "Sumatera Selatan", 16, "Pagar Alam", "73", "Dempo Selatan", "010", "Penjalang", "001", "001P"))
//        list_mfd.add(Mfd("1673010001001P", "Sumatera Selatan", 16, "Pagar Alam", "73", "Dempo Selatan", "010", "Penjalang", "001", "002B"))
//        list_mfd.add(Mfd("1673010001001P", "Sumatera Selatan", 16, "Pagar Alam", "73", "Dempo Selatan", "010", "Penjalang", "001", "003B"))
//        list_mfd.add(Mfd("1673010001001P", "Sumatera Selatan", 16, "Pagar Alam", "73", "Dempo Selatan", "010", "Penjalang", "001", "004B"))
//        list_mfd.add(Mfd("1673010001001P", "Sumatera Selatan", 16, "Pagar Alam", "73", "Dempo Selatan", "010", "Penjalang", "001", "005B"))
//
//        mfdAdapter = MfdAdapter(ArrayList(list_mfd), applicationContext)
//        listview.setAdapter(mfdAdapter)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.itemId
//
//
//        return if (id == R.id.action_settings) {
//            true
//        } else super.onOptionsItemSelected(item)
//
//    }
}
