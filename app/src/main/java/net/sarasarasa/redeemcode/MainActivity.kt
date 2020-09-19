package net.sarasarasa.redeemcode

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.codeState.observe(this) {
            tv_code.text = it.redeemCode.code

            if (!it.redeemCode.isSent) {
                tv_code.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                tv_code.paint.flags = tv_code.paintFlags.and(Paint.STRIKE_THRU_TEXT_FLAG.inv())
            } else {
                tv_code.setTextColor(ContextCompat.getColor(this, R.color.grey))
                tv_code.paint.flags = tv_code.paintFlags.or(Paint.STRIKE_THRU_TEXT_FLAG)
            }

            val countStatus = it.countStatus
            tv_status.text =
                "${countStatus.currentPos}/${countStatus.totalCount}\n已发出：${countStatus.sentCount}"
        }

        btn_mark.setOnClickListener {
            viewModel.switchMarkStateOfCurrentCode()
        }
        btn_previous.setOnClickListener {
            viewModel.getPreviousCode()
        }
        btn_next.setOnClickListener {
            viewModel.getNextCode()
        }

        tv_code.setOnClickListener {
            val clipBoardManager = getSystemService(ClipboardManager::class.java)
            val clipData = ClipData.newPlainText("Label", tv_code.text.toString())
            clipBoardManager?.setPrimaryClip(clipData)
            Toast.makeText(this, "成功复制到粘贴板", Toast.LENGTH_SHORT).show()
        }

        viewModel.checkInit()
    }
}