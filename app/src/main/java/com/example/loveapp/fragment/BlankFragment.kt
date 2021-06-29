package com.example.loveapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loveapp.R
import android.content.Intent
import android.media.MediaPlayer
import android.os.PersistableBundle
import android.renderscript.ScriptGroup
import com.example.loveapp.databinding.FragmentBlankBinding
import org.jsoup.Jsoup
import org.jsoup.select.Elements


class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var mBinding: FragmentBlankBinding? = null
    private val binding get() = mBinding!!

    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentBlankBinding.inflate(inflater, container, false)
        binding.btnPlay.setOnClickListener {
            if (binding.btnPlay.isChecked == true) { //or mediaPlayer?.isPlaying == false
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(activity, R.raw.busker_loneliness_amplifier)


                    //음악 리스트에서 음악 클릭 시
                    // 노래 가사 출력 - jsoup
                    Thread(Runnable {
                        val builder = StringBuilder()
                        val url = "https://www.genie.co.kr/detail/songInfo?xgnm="
                        val addr = "81302357"
                        val doc = Jsoup.connect(url + addr).get()
                        val link = doc.select("#pLyrics").select("p").text()

                        val builder_info = StringBuilder()
                        val link_info = doc.select(".name").text()

                        builder.append(link).append(" ")
                        builder_info.append(link_info).append(" ")


                        activity?.runOnUiThread() {
                            binding.tvInfo.setText(builder_info.toString())
                            binding.tvInfo.isSelected = true

                            binding.tvMusic.setText(builder.toString())
                            //text 흐르게 출력
                            binding.tvMusic.isSelected = true
                        }
                    }).start()

                }
                binding.btnPlay.setBackgroundResource(R.drawable.ic_pausemusic)
                mediaPlayer?.start()



            } else {
                binding.btnPlay.setBackgroundResource(R.drawable.ic_startmusic)
                mediaPlayer?.pause()
                binding.tvMusic.isActivated=false
            }

        }
        // Inflate the layout for this fragment
        return binding.root
    }

}