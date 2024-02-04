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
    var isDeleted = false

    var memoData: Memo? = null
    var modifiedMemo: Memo? =null


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
                    finish()
                }
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_item_modify_show_memo -> {
                            isDeleted =false
                            val newIntent = Intent(this@ShowMemoActivity,ModifyMemoActivity::class.java)
                            newIntent.putExtra("memoData",memoData)
                            modifyMemoLauncher.launch(newIntent)
                        }

                        R.id.menu_item_delete_show_memo -> {
                            isDeleted = true
                            val resultIntent = Intent(this@ShowMemoActivity,MainActivity::class.java)
                            resultIntent.putExtra("deletedMemoData",memoData)
                            resultIntent.putExtra("isDeleted",isDeleted)

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
        memoData = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent?.getParcelableExtra("memoData",Memo::class.java)
        }else{
            intent?.getParcelableExtra<Memo>("memoData")
        }

        val contractModifyMemo = ActivityResultContracts.StartActivityForResult()
        modifyMemoLauncher = registerForActivityResult(contractModifyMemo){
            if(it.resultCode == RESULT_OK){
                // 구현
                if(it.resultCode == RESULT_OK){
                    if(it.data != null){
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                            modifiedMemo = it.data?.getParcelableExtra("modifiedMemo",Memo::class.java)
                        }else{
                            modifiedMemo = it.data?.getParcelableExtra<Memo>("modifiedMemo")
                        }
                        binding.editTextTitleShowMemo.setText(modifiedMemo?.title)
                        binding.editTextContentShowMemo.setText(modifiedMemo?.content)
                        binding.editTextDateShowMemo.setText("${modifiedMemo?.date} (수정됨)")
                    }
                }
            }
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