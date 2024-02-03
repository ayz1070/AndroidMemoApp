package kr.co.lion.homework1_memo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.homework1_memo.databinding.ActivityShowMemoBinding

class ShowMemoActivity : AppCompatActivity() {
    lateinit var binding:ActivityShowMemoBinding
    var memoData: Memo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
        setEvent()
    }

    fun initData() {
        memoData = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent?.getParcelableExtra("memoData",Memo::class.java)
        }else{
            intent?.getParcelableExtra<Memo>("memoData")
        }
    }

    fun initView(){

    }

    fun setEvent(){
        binding.apply {
            editTextTitleShowMemo.setText(memoData?.title)
            editTextContentShowMemo.setText(memoData?.content)
            editTextDateShowMemo.setText(memoData?.date)
        }
    }
}