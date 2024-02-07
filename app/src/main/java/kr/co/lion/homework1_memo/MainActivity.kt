package kr.co.lion.homework1_memo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.homework1_memo.databinding.ActivityMainBinding
import kr.co.lion.homework1_memo.databinding.RowMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var recyclerViewAdapterMain:RecyclerViewAdapterMain
    var modifiedMemo:Memo? = null

    // 런처
    lateinit var makeMemoLauncher:ActivityResultLauncher<Intent>
    lateinit var showMemoLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initData()
        initView()
        setEvent()
    }
    //-------------------------------------------------------------------------
    fun initData(){
        recyclerViewAdapterMain = RecyclerViewAdapterMain()

        // MakeMemoActivity에 대한 계약
        val contractMakeMemo = ActivityResultContracts.StartActivityForResult()
        makeMemoLauncher = registerForActivityResult(contractMakeMemo){
            binding.recycerViewMain.adapter?.notifyDataSetChanged()
        }

        // ShowMemoActivity에 대한 계약
        val contractShowMemo = ActivityResultContracts.StartActivityForResult()
        showMemoLauncher = registerForActivityResult(contractShowMemo){
            if(it.resultCode == RESULT_OK){
                if(it.data!=null){
                    if(Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU){
                        val deletedMemoData = it.data?.getParcelableExtra("deletedMemoData",Memo::class.java)
                        modifiedMemo = it.data?.getParcelableExtra("modifiedMemoData",Memo::class.java)
                        //memoList.remove(deletedMemoData)
                        binding.recycerViewMain.adapter?.notifyDataSetChanged()
                    }else{
                        val deletedMemoData = it.data?.getParcelableExtra<Memo>("deletedMemoData")
                        modifiedMemo = it.data?.getParcelableExtra<Memo>("modifiedMemoData")
                        //memoList.remove(deletedMemoData!!)
                        binding.recycerViewMain.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }




        // 테스트 데이터
//        memoList.add(Memo("제목1","내용1",getCurrentDate()))
//        memoList.add(Memo("제목2","내용2",getCurrentDate()))
//        memoList.add(Memo("제목3","내용3",getCurrentDate()))
    }

    fun initView(){
        setToolbar()

        binding.apply{
            recycerViewMain.layoutManager = LinearLayoutManager(this@MainActivity)
            recycerViewMain.adapter = recyclerViewAdapterMain

            val decoration = MaterialDividerItemDecoration(this@MainActivity,MaterialDividerItemDecoration.VERTICAL)
            recycerViewMain.addItemDecoration(decoration)
        }

    }

    fun setEvent(){

    }
    //-------------------------------------------------------------------------



    fun setToolbar(){
        binding.apply{
            toolBarMain.apply{
                title = "메모 관리"
                // 메뉴 띄우기
                inflateMenu(R.menu.menu_main)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_item_add_main -> {
                            val newIntent = Intent(this@MainActivity,MakeMemoActivity::class.java)
                            makeMemoLauncher.launch(newIntent)
                        }
                    }
                    true
                }

            }
        }
    }

    inner class RecyclerViewAdapterMain: RecyclerView.Adapter<RecyclerViewAdapterMain.ViewHolderMain>() {
        inner class ViewHolderMain(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root) {
            val rowMainBinding:RowMainBinding
            init{
                this.rowMainBinding = rowMainBinding

                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                this.rowMainBinding.root.setOnClickListener {
                    val newIntent = Intent(this@MainActivity,ShowMemoActivity::class.java)
                    newIntent.putExtra("position",adapterPosition)
                    showMemoLauncher.launch(newIntent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            var rowMainBinding = RowMainBinding.inflate(layoutInflater)
            var viewHolderMain = ViewHolderMain(rowMainBinding)
            return viewHolderMain
        }

        override fun getItemCount(): Int = Util.memoList.size

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            holder.rowMainBinding.textViewTitleRowMain.text = Util.memoList[position].title
            holder.rowMainBinding.textViewDateRowMain.text = Util.memoList[position].date
        }
    }
}