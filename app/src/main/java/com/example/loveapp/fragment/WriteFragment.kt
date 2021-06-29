package com.example.loveapp.fragment


import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.loveapp.MainActivity
import com.example.loveapp.MyApplication
import com.example.loveapp.databinding.FragmentWriteBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class WriteFragment : Fragment() {

    var mainActivity: MainActivity? = null
    private var mBinding: FragmentWriteBinding? = null
    private val binding get() = mBinding!!
    val date = System.currentTimeMillis()
    val calendar = DateFormat.format("yyyy년 MM월 dd일 ",date) as String

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is MainActivity) mainActivity =context
    }

    override fun onStart(){
        super.onStart()
        binding.editText.setText("")
        binding.editTitle.setText("")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentWriteBinding.inflate(inflater, container, false)
        binding.editTime.setText("$calendar")
        setFragmentResultListener("request") { key, bundle ->
            bundle.getString("Title")?.let{
                binding.editTitle.setText(it)
                binding.editText.setText(loadText(it))
                binding.savebtd.setOnClickListener{
                    update(binding.editTitle.text.toString())
                    mainActivity?.goList()
                }
            }
        }
        binding.savebtd.setOnClickListener {
            insert()
            mainActivity?.goList()
        }
        return binding.root
    }
    fun insert() {
        val etitle = binding.editTitle.text.toString()
        val etext = binding.editText.text.toString()

        if (etitle == "") {
            val toast = Toast.makeText(MyApplication.ApplicationContext(),"제목을 입력하세요!",Toast.LENGTH_SHORT)
            toast.show()
        } else {
            try {
                val file = File(MyApplication.ApplicationContext().filesDir, etitle)
                if (!file.exists()) {
                    val fileOutputStream = FileOutputStream(file)
                    val byteArray = etext.toByteArray(Charsets.UTF_8)
                    file.createNewFile()
                    fileOutputStream.write(byteArray, 0, byteArray.size)
                    fileOutputStream.close()
                    val toast = Toast.makeText(MyApplication.ApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    val toast = Toast.makeText(MyApplication.ApplicationContext(),"같은 제목의 일기가 있어요!",Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    fun update(Title : String) {
        val file = File(MyApplication.ApplicationContext().filesDir.toString(),Title)
        file.delete()
        insert()
    }
    fun loadText(etitle:String):String{
        try{
            val file = File(MyApplication.ApplicationContext().filesDir,etitle)
            val fileInputStream = FileInputStream(file)
            val byteArray = fileInputStream.readBytes()
            return String(byteArray,charset("UTF_8"))
        }
        catch(e:Exception){
            e.printStackTrace()
            return ""
        }
    }
}