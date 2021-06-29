package com.example.loveapp.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.PermissionRequest
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import com.example.loveapp.R
import org.w3c.dom.Text
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var result : Int? = null
    private lateinit var tts : TextToSpeech
    private lateinit var seekBarPitch : SeekBar
    private lateinit var seekBarSpeed: SeekBar
    private lateinit var speakButton : Button
    private lateinit var favoritePerson : ImageView

    private var testString = "난 외롭지 않다. 니가 있으니까"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        speakButton = view.findViewById(R.id.ttsButton)
        seekBarPitch = view.findViewById(R.id.seekPitchBar)
        seekBarSpeed = view.findViewById(R.id.seekSpeedBar)
        favoritePerson = view.findViewById(R.id.favoritePerson)

        favoritePerson.setOnClickListener{openGallery()}

        tts = TextToSpeech(activity , TextToSpeech.OnInitListener { status ->

            if(status == TextToSpeech.SUCCESS){
                Log.d("언어지원안함","a")
                tts.language = Locale.KOREA
                Toast.makeText(activity,"TTS 초기화 완료",Toast.LENGTH_SHORT).show()
            }else{
                if(status == TextToSpeech.LANG_MISSING_DATA){
                    Log.d("데이터메싱","a"+status)
                }
                Toast.makeText(activity,"TTS초기화 실패",Toast.LENGTH_SHORT).show()
            }
        })
        speakButton.setOnClickListener{ speak() }

        // Inflate the layout for this  fragment
        return view
    }

    private var OPEN_GALLERY = 1

    private fun openGallery(){
        val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,OPEN_GALLERY)
    }

    override fun onActivityResult(requestCode : Int , resultCode : Int , data : Intent?){
        super.onActivityResult(requestCode , resultCode , data)
            if(requestCode == OPEN_GALLERY){
                favoritePerson.setImageURI(data?.data)

            }
    }

    private fun speak(){
        var text = testString.toString()
        var pitch = seekBarPitch.progress.toFloat()/50
        if( pitch < 0.1) pitch = 0.1f
        var speed = seekBarSpeed.progress.toFloat()/50
        if( speed < 0.1) speed = 0.1f
        tts.setPitch(pitch)
        tts.setSpeechRate(speed)
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null)
    }

    override fun onDestroy() {
        if (tts != null){
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }


}