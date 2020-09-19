package net.sarasarasa.redeemcode

import net.sarasarasa.redeemcode.db.data.RedeemCode


data class MainCodeState(
    val redeemCode: RedeemCode,
    val countStatus: CountStatus
)

data class CountStatus(
    val currentPos: Int,
    val totalCount: Int,
    val sentCount: Int
)