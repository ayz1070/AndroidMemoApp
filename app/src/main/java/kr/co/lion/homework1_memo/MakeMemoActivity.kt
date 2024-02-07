package kr.co.lion.homework1_memo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.homework1_memo.databinding.ActivityMakeMemoBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MakeMemoActivity : AppCompatActivity() {
    lateinit var binding:ActivityMakeMemoBinding
    lateinit var memoTitle:String
    lateinit var memoContent:String
    lateinit var memoData:Memo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
        setEvent()
    }

    fun initData(){

    }

    fun initView(){
        setToolbar()
    }

    fun setEvent(){

    }

    fun setToolbar(){
        binding.apply{
            toolbarMakeMemo.apply{
                title ="메모 작성"
                setNavigationIcon(R.drawable.arrow_back_24px)
                inflateMenu(R.menu.menu_make_memo)
                setNavigationOnClickListener {
                    finish()
                }

                setOnMenuItemClickListener {
                    // 메뉴 완료 툴바 버튼 클릭 시
                    when(it.itemId){
                        R.id.menu_item_add_make_memo -> {
                            processInput()
                        }
                    }
                    true
                }

            }
        }
    }

    fun processInput() {
        binding.apply{
            // 예외 처리 필요
            memoTitle = editTextTitle.text.toString()
            if(memoTitle.isEmpty()){
                Snackbar.make(binding.root,"빈 칸이 존재합니다!!",Snackbar.LENGTH_SHORT).show()
                return
            }
            memoContent = editTextContent.text.toString()
            if(memoContent.isEmpty()){
                Snackbar.make(binding.root,"빈 칸이 존재합니다!!",Snackbar.LENGTH_SHORT).show()
                return
            }

            memoData = Memo(memoTitle,memoContent,getCurrentDate())

            Util.memoList.add(memoData)

            finish()
        }
    }

    fun getCurrentDate():String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

}