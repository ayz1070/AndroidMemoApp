package kr.co.lion.homework1_memo

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.concurrent.thread

class Util {
    companion object{
        val memoList = mutableListOf<Memo>()
        fun getCurrentDate():String{
            val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초", Locale.getDefault())
            val currentDate = Date()
            return dateFormat.format(currentDate)
        }

        fun hideSoftInput(activity:AppCompatActivity){
            // 현재 포커스를 가지고 있는 View가 있다면 키보드를 내린다.
            if(activity.window.currentFocus != null){
                val inputMethodManager = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(activity.window.currentFocus?.windowToken,0)

            }
        }
    }


}