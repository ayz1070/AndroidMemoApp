package kr.co.lion.homework1_memo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import kr.co.lion.homework1_memo.databinding.ActivityShowMemoBinding

class ShowMemoActivity : AppCompatActivity() {
    lateinit var binding:ActivityShowMemoBinding
    lateinit var modifyMemoLauncher:ActivityResultLauncher<Intent>
    var position:Int = 0
    var memoData: Memo? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
        setEvent()
    }

    fun setToolbar(){
        binding.apply{
            toolbarShowMemo.apply{
                title = "메모 보기"
                inflateMenu(R.menu.menu_show_memo)
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {

                    val resultIntent = Intent(this@ShowMemoActivity,MainActivity::class.java)
                    setResult(RESULT_OK,resultIntent)
                    finish()
                }
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_item_modify_show_memo -> {
                            val newIntent = Intent(this@ShowMemoActivity,ModifyMemoActivity::class.java)
                            newIntent.putExtra("position",position)
                            modifyMemoLauncher.launch(newIntent)
                        }

                        R.id.menu_item_delete_show_memo -> {

                            val resultIntent = Intent(this@ShowMemoActivity,MainActivity::class.java)
                            Util.memoList.removeAt(position)

                            setResult(RESULT_OK,resultIntent)
                            finish()
                        }
                    }
                    true
                }
            }
        }
    }

    fun initData() {
        position = intent?.getIntExtra("position",0)!!
        memoData = Util.memoList[position]

        val contractModifyMemo = ActivityResultContracts.StartActivityForResult()
        modifyMemoLauncher = registerForActivityResult(contractModifyMemo){
            binding.editTextTitleShowMemo.setText(Util.memoList[position].title)
            binding.editTextContentShowMemo.setText(Util.memoList[position].content)
            binding.editTextDateShowMemo.setText("${Util.memoList[position].date} (수정됨)")
        }

    }

    fun initView(){
        setToolbar()
    }

    fun setEvent(){
        binding.apply {
            editTextTitleShowMemo.setText(memoData?.title)
            editTextContentShowMemo.setText(memoData?.content)
            editTextDateShowMemo.setText(memoData?.date)
        }
    }
}