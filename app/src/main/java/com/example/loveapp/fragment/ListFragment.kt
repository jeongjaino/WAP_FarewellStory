package com.example.loveapp.fragment

import android.content.Context
import android.icu.text.CaseMap
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fragment.DiaryAdapter
import com.example.loveapp.*
import com.example.loveapp.databinding.FragmentListBinding
import com.example.loveapp.Diary
import com.example.loveapp.MyApplication

import com.example.loveapp.MainActivity
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

class ListFragment : Fragment(), DiaryAdapter.OnItemClickListener {
    private var mBinding: FragmentListBinding? = null
    private val binding get() = mBinding!!
    var mainActivity: MainActivity? = null

    override fun onStart(){
        super.onStart()
        attach()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is MainActivity) mainActivity =context
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentListBinding.inflate(inflater, container, false)
        val data:List<Diary> = fileList()
        val adapter = DiaryAdapter(data,this    )
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)

        binding.addBtd.setOnClickListener{
            mainActivity?.goWrite()
        }
        return binding.root
    }

    override fun onItemClick(position: Int) {
        //번들로 보내
        val path = File(MyApplication.ApplicationContext().filesDir.toString())
        val files = path.listFiles()
        val title = files.elementAt(position).name
        val bundle = bundleOf("Title" to "${title}")
        setFragmentResult("request",bundle)
        mainActivity?.goWrite()
    }

    override fun onButtonClick(position: Int) {
        val path = File(MyApplication.ApplicationContext().filesDir.toString())
        val files = path.listFiles()
        files[position].delete()
        /*val bundle = bundleOf("Position" to position)
        setFragmentResult("request",bundle)
        MyDialogFragment().show(
            childFragmentManager,MyDialogFragment.TAG
        )*/
        attach()
    }

    fun fileList(): MutableList<Diary> {

        val list = ArrayList<Diary>()

        val path = File(MyApplication.ApplicationContext().filesDir.toString())
        val files = path.listFiles()

        if(files != null) {
            for (i in files) {
                val item = Diary(i.name, loadText(i.name))
                list += item
            }
        }
        else{}
        return list
    }
    fun attach(){
        val data:List<Diary> = fileList()
        val adapter = DiaryAdapter(data,this)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=LinearLayoutManager(context)
    }
    fun loadText(etitle:String):String{
        try{
            val file = File(MyApplication.ApplicationContext().filesDir,etitle)
            val fileInputStream = FileInputStream(file)
            val byteArray = fileInputStream.readBytes()
            return String(byteArray,charset("UTF_8"))
        }
        catch(e: Exception){
            e.printStackTrace()
            return ""
        }
    }
}