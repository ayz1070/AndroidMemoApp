package kr.co.lion.homework1_memo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    lateinit var memoList:MutableList<Memo>
    lateinit var recyclerViewAdapterMain:RecyclerViewAdapterMain

    // 런처
    lateinit var makeMemoLauncher:ActivityResultLauncher<Intent>


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
        memoList = mutableListOf()
        recyclerViewAdapterMain = RecyclerViewAdapterMain()

        val contractMakeMemo = ActivityResultContracts.StartActivityForResult()
        makeMemoLauncher = registerForActivityResult(contractMakeMemo){
            // 작업 결과가 OK라면
            if(it.resultCode== RESULT_OK){
                // 전달된 Intent 객체가 있다면
                if(it.data != null){
                    // 학생 객체를 추출한다.
                    if(Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU){
                        val memoData = it.data?.getParcelableExtra("memoData",Memo::class.java)
                        memoList.add(memoData!!)
                    }else{
                        val memoData = it.data?.getParcelableExtra<Memo>("memoData")
                        memoList.add(memoData!!)
                    }
                    binding.recycerViewMain.adapter?.notifyDataSetChanged()
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


    fun getCurrentDate():String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }
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

    inner class RecyclerViewAdapterMain(): RecyclerView.Adapter<RecyclerViewAdapterMain.ViewHolderMain>() {
        inner class ViewHolderMain(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root) {
            val rowMainBinding:RowMainBinding
            init{
                this.rowMainBinding = rowMainBinding

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            var rowMainBinding = RowMainBinding.inflate(layoutInflater)
            var viewHolderMain = ViewHolderMain(rowMainBinding)
            return viewHolderMain
        }

        override fun getItemCount(): Int = memoList.size

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            holder.rowMainBinding.textViewTitleRowMain.text = memoList[position].title
            holder.rowMainBinding.textViewDateRowMain.text = memoList[position].date
        }
    }
}