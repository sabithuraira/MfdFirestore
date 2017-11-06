package com.farifam.mfdfirestore.models

/**
 * Created by sabithuraira on 10/22/17.
 */

class Mfd{
    var code: String = ""
    var prov_nama: String = ""
    var kab_nama: String = ""
    var kec_nama: String = ""
    var desa_nama: String = ""
    var blok_sensus: String = ""
    var jumlah_kk: Int? = null;
    var bsbtt: Int? = null;
    var muatan_dominan: String? = null;
    var ruta_biasa: Int? = null;
    var ruta_khusus: Int? = null;
    var art_laki: Int? = null;
    var art_perempuan: Int? = null;

    var prov_no: String? = null
    var kab_no:  String? = null
    var kec_no: String? = null
    var desa_no: String? = null

    constructor(){}

    constructor(code: String, prov_nama: String, prov_no: String, kab_nama: String, kab_no: String, kec_nama: String, kec_no: String, desa_nama: String, desa_no: String, blok_sensus: String){
        this.code = code
        this.prov_nama = prov_nama
        this.kab_nama = kab_nama
        this.kec_nama = kec_nama
        this.desa_nama = desa_nama
        this.blok_sensus = blok_sensus
        this.prov_no = prov_no
        this.kab_no = kab_no
        this.kec_no = kec_no
        this.desa_no = desa_no
    }
}
