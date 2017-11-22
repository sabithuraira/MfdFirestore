package com.farifam.mfdfirestore

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.MemoryFile
import android.support.annotation.Nullable
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
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
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot



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
        db.collection("datas").document(list_member[position].id)
                .addSnapshotListener(object : EventListener<DocumentSnapshot> {
                    override fun onEvent(snapshot: DocumentSnapshot?,
                                         e: FirebaseFirestoreException?) {
                        if (e != null) {
                            Log.w(ContentValues.TAG, "Listen error", e)
                            return
                        }
                        snapshot?.reference?.delete()
                        loadFirestoreDatas()
                    }
                })
    }

    fun updateData(position: Int){
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra("id",list_member[position].id)

        intent.putExtra("prov_no", list_member[position].prov_no)
        intent.putExtra("prov_nama", list_member[position].prov_nama)
        intent.putExtra("kab_no", list_member[position].kab_no)
        intent.putExtra("kab_nama", list_member[position].kab_nam)
        intent.putExtra("kec_no", list_member[position].kec_no)
        intent.putExtra("kec_nama", list_member[position].kec_nama)
        intent.putExtra("desa_no", list_member[position].desa_no)
        intent.putExtra("desa_nama", list_member[position].desa_nama)
        intent.putExtra("blok_sensus", list_member[position].blok_sensus)
        intent.putExtra("kk", list_member[position].kk)
        intent.putExtra("bsbtt", list_member[position].bsbtt)
        intent.putExtra("muatan_dominan", list_member[position].muatan_dominan)
        intent.putExtra("ruta_biasa", list_member[position].ruta_biasa)
        intent.putExtra("ruta_khusus", list_member[position].ruta_khusus)
        intent.putExtra("art_laki", list_member[position].art_laki)
        intent.putExtra("art_perempuan", list_member[position].art_perempuan)

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

        val memberCollection = db.collection("datas");
//        var query : Query = memberCollection.orderBy("first")
//
//        if(search.text.toString().length>0){
//            query = memberCollection.whereEqualTo("first", search.text.toString())
//
//        }

//        memberCollection.get()
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        list_member.clear()
//                        for (document in task.result) {
//                            list_member.add(document.toObject(Mfd::class.java))
//                        }
//
//                        dataAdapter = MfdAdapter(ArrayList(list_member), applicationContext)
//                        listview.setAdapter(dataAdapter)
//
//                        progress.visibility = View.GONE
//                    } else {
//                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
//                        progress.visibility = View.GONE
//                    }
//                }

        memberCollection
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(querySnapshot: QuerySnapshot?,
                            e: FirebaseFirestoreException?) {
                    if (e != null) {
                        Log.w(ContentValues.TAG, "Listen error", e)
                        return
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        when (change.type) {
                            DocumentChange.Type.ADDED -> onDocumentAdded(change)
                            DocumentChange.Type.MODIFIED -> onDocumentModified(change)
                            DocumentChange.Type.REMOVED -> onDocumentRemoved(change)
                        }
                    }

                    dataAdapter = MfdAdapter(ArrayList(list_member), applicationContext)
                    listview.setAdapter(dataAdapter)

                    progress.visibility = View.GONE
                }
            })
    }

    fun onDocumentAdded(change: DocumentChange){
        var cur_data: Mfd = change.document.toObject(Mfd::class.java)
        cur_data.id = change.document.id

        if(!list_member.any { x -> x.id == change.document.id })
            list_member.add(cur_data)
    }

    fun onDocumentModified(change: DocumentChange){
        var cur_data: Mfd = change.document.toObject(Mfd::class.java)
        cur_data.id = change.document.id
        if (change.getOldIndex() == change.getNewIndex()) {
            list_member.set(change.getOldIndex(),  cur_data);
        } else {
            list_member.removeAt(change.getOldIndex());
            list_member.add(change.getNewIndex(), cur_data);
        }
    }

    fun onDocumentRemoved(change: DocumentChange){
        list_member.removeAt(change.getOldIndex());
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
}
