package net.sarasarasa.redeemcode.db

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import net.sarasarasa.redeemcode.CountStatus
import net.sarasarasa.redeemcode.base.getApplicationContext
import net.sarasarasa.redeemcode.base.logI
import net.sarasarasa.redeemcode.db.data.RedeemCode
import org.litepal.LitePal
import org.litepal.extension.count
import org.litepal.extension.find
import org.litepal.extension.findFirst
import java.io.BufferedReader
import java.io.InputStreamReader

class MainDataRepository {

    private val sp by lazy {
        getApplicationContext().getSharedPreferences("main", MODE_PRIVATE)
    }

    private var currentId: Long
        get() = sp.getLong("currentId", 0)
        set(value) = sp.edit { putLong("currentId", value) }

    private val nullRedeemCode by lazy {
        RedeemCode().apply {
            code = "NULL"
            isSent = true
        }
    }

    fun getCurrentCode(): RedeemCode {
        return LitePal.find(currentId) ?: nullRedeemCode
    }

    fun getNextCode(): RedeemCode {
        return (LitePal.where("id > ?", currentId.toString()).order("id asc").findFirst()
            ?: nullRedeemCode).also {
            currentId = it.id ?: 0
        }
    }

    fun getPreviousCode(): RedeemCode {
        return (LitePal.where("id < ?", currentId.toString()).order("id desc").findFirst()
            ?: nullRedeemCode).also {
            currentId = it.id ?: 0
        }
    }

    fun switchMarkStateOfRedeemCode(redeemCode: RedeemCode?): RedeemCode {
        return redeemCode?.also {
            it.isSent = !it.isSent
            if (it.id != null) {
                it.save()
            }
        } ?: getCurrentCode()
    }

    fun getCurrentCountStatus(): CountStatus {
        val currentPos = LitePal.where("id <= ?", currentId.toString()).count<RedeemCode>()
        val totalCount = LitePal.count<RedeemCode>()
        val sentCount = LitePal.where("isSent = ?", "1").count<RedeemCode>()
        return CountStatus(currentPos, totalCount, sentCount)
    }

    /**
     * 初始化数据
     */
    fun initData() {
        if (LitePal.count<RedeemCode>() == 0) {
            val startTime = System.currentTimeMillis()

            val inputStream = getApplicationContext().assets.open("redeemCode.txt")
            val reader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(reader)
            var str: String? = ""
            do {
                str = bufferedReader.readLine()
                if (str != null && str.isNotBlank()) {
                    RedeemCode().apply {
                        code = str
                    }.save()
                }
            } while (str != null)

            bufferedReader.close()
            inputStream.close()
            logI("init cost time = ${System.currentTimeMillis() - startTime}")
            logI("Inited data size = ${LitePal.count<RedeemCode>()}")
        }
    }
}