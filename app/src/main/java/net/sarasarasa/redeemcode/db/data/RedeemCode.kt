package net.sarasarasa.redeemcode.db.data

import org.litepal.crud.LitePalSupport

class RedeemCode : LitePalSupport() {
    var id: Long? = null
    var code: String? = null
    var isSent: Boolean = false
}