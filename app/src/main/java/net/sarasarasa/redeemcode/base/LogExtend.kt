package net.sarasarasa.redeemcode.base

import android.util.Log

private const val COMMON_TAG = "RedeemCode"

fun logI(msg: String) =
    Log.i(COMMON_TAG, msg)

fun logD(msg: String) =
    Log.d(COMMON_TAG, msg)

fun logE(msg: String) =
    Log.e(COMMON_TAG, msg)