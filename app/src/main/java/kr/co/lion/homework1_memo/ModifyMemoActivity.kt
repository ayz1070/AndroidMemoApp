package kr.co.lion.homework1_memo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.homework1_memo.databinding.ActivityModifyMemoBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ModifyMemoActivity : AppCompatActivity() {
    lateinit var binding:ActivityModifyMemoBinding
    lateinit var memoTitle:String
    lateinit var memoContent:String
    lateinit var memoData:Memo
    var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
        setEvent()

    }

    fun setToolbar(){
        binding.apply{
            toolbarModifyMemo.apply{
                title = "메모 수정"
                inflateMenu(R.menu.menu_modify_memo)
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    finish()
                }
            }
        }
    }

    fun initData(){

        position = intent?.getIntExtra("position",0)!!
        memoData = Util.memoList[position]
    }

    fun initView(){
        setToolbar()
        binding.apply{
            editTextTitleModifyMemo.setText(memoData.title)
            editTextContentModifyMemo.setText(memoData.content)
        }
    }

    fun setEvent(){
        binding.apply{
            toolbarModifyMemo.apply{
                setOnMenuItemClickListener{
                    when(it.itemId){
                        R.id.menu_item_complete_modify_memo -> {
                            processInput()
                        }
                    }
                    true
                }

            }
        }
    }

    fun processInput() {
        binding.apply {
            // 예외 처리 필요
            memoTitle = editTextTitleModifyMemo.text.toString()
            if (memoTitle.isEmpty()) {
                Snackbar.make(binding.root, "빈 칸이 존재합니다!!", Snackbar.LENGTH_SHORT).show()
                return
            }
            memoContent = editTextContentModifyMemo.text.toString()
            if (memoContent.isEmpty()) {
                Snackbar.make(binding.root, "빈 칸이 존재합니다!!", Snackbar.LENGTH_SHORT).show()
                return
            }

            Util.memoList[position] = Memo(memoTitle, memoContent,getCurrentDate())

            val resultIntent = Intent()

            setResult(RESULT_OK, resultIntent)
            finish()
        }

    }
    fun getCurrentDate():String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }
}