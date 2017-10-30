package com.farifam.mfdfirestore.models

/**
 * Created by sabithuraira on 10/22/17.
 */

class Mfd{
    var code: String = ""
    var prov: String = ""
    var kab: String = ""
    var kec: String = ""
    var desa: String = ""
    var bs: String = ""

    var prov_no: Int? = null
    var kab_no:  String? = null
    var kec_no: String? = null
    var desa_no: String? = null

    constructor(){}

    constructor(code: String, prov: String, prov_no: Int, kab: String, kab_no: String, kec: String, kec_no: String, desa: String, desa_no: String, bs: String){
        this.code = code
        this.prov = prov
        this.kab = kab
        this.kec = kec
        this.desa = desa
        this.bs = bs
        this.prov_no = prov_no
        this.kab_no = kab_no
        this.kec_no = kec_no
        this.desa_no = desa_no
    }
}
