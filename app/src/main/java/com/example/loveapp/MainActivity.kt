package com.example.loveapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.loveapp.databinding.ActivityMainBinding
import com.example.loveapp.fragment.DiaryFragment
import com.example.loveapp.fragment.FavoriteFragment
import com.example.loveapp.fragment.ListFragment
import com.example.loveapp.fragment.WriteFragment
import com.example.loveapp.fragment.BlankFragment


import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val fragmentManager: FragmentManager = supportFragmentManager
    private val fragmentDiary : DiaryFragment = DiaryFragment()
    private val fragmentFavorite : FavoriteFragment = FavoriteFragment()
    private val blankFragment = BlankFragment()
    private lateinit var binding: ActivityMainBinding
    val listfragmnent = ListFragment()
    val writeFragment = WriteFragment()


    lateinit var navBar : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navBar = findViewById(R.id.bnv_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initNavBar(navBar)
    }

    private fun initNavBar(navBar : BottomNavigationView){
        navBar.run {
            setOnNavigationItemSelectedListener{
                when(it.itemId){
                    R.id.fragment_menu_item1 -> {
                        changeFragment(fragmentFavorite)
                    }
                    R.id.fragment_menu_item2 -> {
                        changeFragment(listfragmnent)
                    }
                    R.id.fragment_menu_item3 -> {
                        changeFragment(blankFragment)

                    }
                }
                true
            }
            selectedItemId = R.id.fragment_menu_item1
        }
    }

    private fun changeFragment(fragment : Fragment){
        fragmentManager
            .beginTransaction()
            .replace(R.id.fl_container,fragment)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_container, fragment)
            transaction.commit()
        }
    }
    fun goList(){
        replaceFragment(listfragmnent)
    }
    fun goWrite(){
        replaceFragment(writeFragment)
    }

}