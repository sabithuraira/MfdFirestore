package com.farifam.mfdfirestore

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.*

import kotlinx.android.synthetic.main.activity_form.*

import java.util.HashMap

/**
 * A login screen that offers login via email/password.
 */
class FormActivity : AppCompatActivity(){
    val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
    private val db by lazy { FirebaseFirestore.getInstance() }
    var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        db.setFirestoreSettings(settings);

        if (intent.hasExtra("id"))
            id = intent.getStringExtra("id")


//        Log.d(ContentValues.TAG, "idnya:" + id)

        if(id.length!=0){
            initializeUpdate()
        }

        submit.setOnClickListener {
            err_msg.visibility = View.GONE
            progress.visibility = View.VISIBLE

            val data = HashMap<String, Any>()
            data.put("prov_no", prov_no.text.toString())
            data.put("prov_nama", prov_nama.text.toString())
            data.put("kab_no", kab_no.text.toString())
            data.put("kab_nam", kab_nama.text.toString())
            data.put("kec_no", kec_no.text.toString())
            data.put("kec_nama", kec_nama.text.toString())
            data.put("desa_no", desa_no.text.toString())
            data.put("desa_nama", desa_nama.text.toString())
            data.put("blok_sensus", blok_sensus.text.toString())
            data.put("kk", kk.text.toString())
            data.put("bsbtt", bsbtt.text.toString())
            data.put("muatan_dominan", muatan_dominan.text.toString())
            data.put("ruta_biasa", ruta_biasa.text.toString())
            data.put("ruta_khusus", ruta_khusus.text.toString())
            data.put("art_laki", art_laki.text.toString())
            data.put("art_perempuan", art_perempuan.text.toString())

            if(id.length==0){
                db.collection("datas").document()
                    .addSnapshotListener(object : EventListener<DocumentSnapshot> {
                        override fun onEvent(snapshot: DocumentSnapshot?,
                                             e: FirebaseFirestoreException?) {
                            if (e != null) {
                                Log.w(ContentValues.TAG, "Listen error", e)
                                err_msg.text = e.message
                                err_msg.visibility = View.VISIBLE;
                                return
                            }
                            snapshot?.reference?.set(data)

                            endActivity()
                        }
                    })
            }
            else{
                db.collection("datas").document(id)
                    .addSnapshotListener(object : EventListener<DocumentSnapshot> {
                        override fun onEvent(snapshot: DocumentSnapshot?,
                                             e: FirebaseFirestoreException?) {
                            if (e != null) {
                                Log.w(ContentValues.TAG, "Listen error", e)
                                err_msg.text = e.message
                                err_msg.visibility = View.VISIBLE;
                                return
                            }

                            snapshot?.reference?.update(data)
                            endActivity()
                        }
                    })
            }
        }
    }

    fun endActivity(){
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        this@FormActivity.finish()
    }

    override fun onDestroy() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        super.onDestroy()
    }

    fun initializeUpdate(){
        prov_no.setText(intent.getStringExtra("prov_no"))
        prov_nama.setText(intent.getStringExtra("prov_nama"))
        kab_no.setText(intent.getStringExtra("kab_no"))
        kab_nama.setText(intent.getStringExtra("kab_nama"))
        kec_no.setText(intent.getStringExtra("kec_no"))
        kec_nama.setText(intent.getStringExtra("kec_nama"))
        desa_no.setText(intent.getStringExtra("desa_no"))
        desa_nama.setText(intent.getStringExtra("desa_nama"))
        blok_sensus.setText(intent.getStringExtra("blok_sensus"))
        kk.setText(intent.getStringExtra("kk"))
        bsbtt.setText(intent.getStringExtra("bsbtt"))
        muatan_dominan.setText(intent.getStringExtra("muatan_dominan"))
        ruta_biasa.setText(intent.getStringExtra("ruta_biasa"))
        ruta_khusus.setText(intent.getStringExtra("ruta_khusus"))
        art_laki.setText(intent.getStringExtra("art_laki"))
        art_perempuan.setText(intent.getStringExtra("art_perempuan"))
    }
}

