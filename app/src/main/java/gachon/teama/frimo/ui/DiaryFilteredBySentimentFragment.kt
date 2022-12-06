package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.databinding.FragmentDiaryFilteredSentimentBinding
import gachon.teama.frimo.databinding.FragmentFilteredDiaryBinding
import kotlin.collections.ArrayList

class DiaryFilteredBySentimentFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentDiaryFilteredSentimentBinding.inflate(layoutInflater) }

    // Diary
    private lateinit var filter1Diary: ArrayList<Diary>
    private lateinit var filter2Diary: ArrayList<Diary>

    /**
     * @description - 생명주기 onCreateView
     * @param - inflater(LayoutInflater)
     * @param - container(ViewGroup)
     * @param - savedInstanceState(Bundle)
     * @return - v(View)
     * @author - namsh1125
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        getDiary()
        setScreen()
        setClickListener()

        return binding.root
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() {

//        binding.layoutFilter1Detail.setOnClickListener {
//
//            // Intent로 필터링된 diary 넣어 전달
//            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
//            // Todo: 어떤 내용을 기반으로 필터링하는지 intent 수정 필요
//            intent.putExtra("filter", "필터1")
//            intent.putExtra("filteredDiary", filter1Diary)
//            startActivity(intent)
//        }
//
//        binding.layoutFilter2Detail.setOnClickListener {
//
//            // Intent로 필터링된 diary 넣어 전달
//            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
//            // Todo: 어떤 내용을 기반으로 필터링하는지 intent 수정 필요
//            intent.putExtra("filter", "필터2")
//            intent.putExtra("filteredDiary", filter2Diary)
//            startActivity(intent)
//        }
//
//        // When filter1 diary1 clicked
//        binding.filter1Diary1.setOnClickListener {
//
//            // Intent로 diary id 넣어 전달
//            val intent = Intent(requireContext(), DiaryActivity::class.java)
//            intent.putExtra("id", filter1Diary[0].id)
//            startActivity(intent)
//        }
//
//        // When filter1 diary2 clicked
//        binding.filter1Diary2.setOnClickListener {
//
//            // Intent로 diary id 넣어 전달
//            val intent = Intent(requireContext(), DiaryActivity::class.java)
//            intent.putExtra("id", filter1Diary[1].id)
//            startActivity(intent)
//        }
//
//        // When filter2 diary1 clicked
//        binding.filter2Diary1.setOnClickListener {
//
//            // Intent로 diary id 넣어 전달
//            val intent = Intent(requireContext(), DiaryActivity::class.java)
//            intent.putExtra("id", filter2Diary[0].id)
//            startActivity(intent)
//        }
//
//        // When filter2 diary2 clicked
//        binding.filter2Diary2.setOnClickListener {
//
//            // Intent로 diary id 넣어 전달
//            val intent = Intent(requireContext(), DiaryActivity::class.java)
//            intent.putExtra("id", filter2Diary[1].id)
//            startActivity(intent)
//        }

    }

    /**
     * @description - Set screen
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() {

//        with(binding) {
//
//            // Todo: 해당 화면에 보여줄 diary가 없다면 visibility를 gone으로 설정
//
//           // Filter1 Diary1 셋팅
//            textviewFilter1Diary1Date.text = filter1Diary[0].created
//            textviewFilter1Diary1Sentiment.text = getTextSentiment(filter1Diary[0].sentiment)
//            imageViewFilter1Diary1.background.setTint(getColor(filter1Diary[0].sentiment))
//
//            // Filter1 Diary2 셋팅
//            textviewFilter1Diary2Date.text = filter1Diary[1].created
//            textviewFilter1Diary2Sentiment.text = getTextSentiment(filter1Diary[1].sentiment)
//            imageViewFilter1Diary2.background.setTint(getColor(filter1Diary[1].sentiment))
//
//            // Filter2 Diary1 셋팅
//            textviewFilter2Diary1Date.text = filter2Diary[0].created
//            textviewFilter2Diary1Sentiment.text = getTextSentiment(filter2Diary[0].sentiment)
//            imageViewFilter2Diary1.background.setTint(getColor(filter2Diary[0].sentiment))
//
//            // Filter2 Diary2 셋팅
//            textviewFilter2Diary2Date.text = filter2Diary[1].created
//            textviewFilter2Diary2Sentiment.text = getTextSentiment(filter2Diary[1].sentiment)
//            imageViewFilter2Diary2.background.setTint(getColor(filter1Diary[1].sentiment))
//
//        }

    }

    /**
     * @description - Server에서 filtering된 diary 가져오기
     * @param - sentiment(Int) : 필터링 기준
     * @return - diaries(Arraylist<Diary>) : 필터링된 diary들 (감정)
     * @author - namsh1125
     */
    private fun getDiaries(sentiment: Int): ArrayList<Diary> {

        // Todo: 서버에서 filtering된 diary 가져오기
        val diaries: MutableList<Diary> = mutableListOf()

        diaries.add(Diary(id = 1, title = "1번째 일기", content = "나는 오늘 햄버거를 먹었다", created = "22.11.24", sentiment = pleasure))
        diaries.add(Diary(id = 2, title = "2번째 일기", content = "나는 오늘 게임을 했다", created = "22.11.25", sentiment = sadness))
        diaries.add(Diary(id = 3, title = "3번째 일기", content = "나는 집에 가고싶다", created = "22.11.26", sentiment = embarrassment))
        diaries.add(Diary(id = 4, title = "4번째 일기", content = "해외 여행 가고싶다", created = "22.11.27", sentiment = anxiety))

        return diaries as ArrayList<Diary>
    }

    companion object Sentiment {
        const val anger = 0
        const val sadness = 1
        const val anxiety = 2
        const val wound = 3
        const val embarrassment = 4
        const val pleasure = 5
    }

}