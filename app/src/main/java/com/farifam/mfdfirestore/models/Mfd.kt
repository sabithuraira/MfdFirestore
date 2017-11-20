package com.farifam.mfdfirestore.models

/**
 * Created by sabithuraira on 10/22/17.
 */

class Mfd{
    var id: String = ""
    val code: String
        get() = prov_no+kab_no+kec_no+desa_no+" "+blok_sensus

    var prov_nama: String = ""
    var kab_nam: String = ""
    var kec_nama: String = ""
    var desa_nama: String = ""
    var blok_sensus: String = ""
    var kk: String? = null;
    var bsbtt: String? = null;
    var muatan_dominan: String? = null;
    var ruta_biasa: String? = null;
    var ruta_khusus: String? = null;
    var art_laki: String? = null;
    var art_perempuan: String? = null;

    var prov_no: String? = null
    var kab_no:  String? = null
    var kec_no: String? = null
    var desa_no: String? = null

    constructor(){}

    constructor(prov_nama: String, prov_no: String, kab_nam: String, kab_no: String, kec_nama: String, kec_no: String, desa_nama: String, desa_no: String, blok_sensus: String){
        this.prov_nama = prov_nama
        this.kab_nam = kab_nam
        this.kec_nama = kec_nama
        this.desa_nama = desa_nama
        this.blok_sensus = blok_sensus
        this.prov_no = prov_no
        this.kab_no = kab_no
        this.kec_no = kec_no
        this.desa_no = desa_no
    }
}
