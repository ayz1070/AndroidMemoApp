package kr.co.lion.homework1_memo

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Util {
    companion object{
        val memoList = mutableListOf<Memo>()
        fun getCurrentDate():String{
            val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초", Locale.getDefault())
            val currentDate = Date()
            return dateFormat.format(currentDate)
        }
    }


}