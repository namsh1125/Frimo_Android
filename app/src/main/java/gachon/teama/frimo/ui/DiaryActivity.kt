package gachon.teama.frimo.ui

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import gachon.teama.frimo.R
import gachon.teama.frimo.adapter.WordsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.remote.Diary.Sentiment
import gachon.teama.frimo.data.remote.DiaryKeywords
import gachon.teama.frimo.data.remote.DiaryServer
import gachon.teama.frimo.databinding.ActivityDiaryBinding
import kotlinx.coroutines.*

class DiaryActivity : BaseActivity<ActivityDiaryBinding>(ActivityDiaryBinding::inflate) {

    private val diaryId by lazy { intent.getLongExtra("id", 0) }

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {
        setScreen()
        setClickListener()
    }


    /**
     * @description - 서버에서 받아온 diary를 화면에 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() {
        setDiary()
        setKeyword()
        // Todo: (Not now) 댓글 셋팅

    }

    /**
     * @description - 서버에서 diary 정보 받아와 화면에 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setDiary() = with(binding) {

        CoroutineScope(Dispatchers.Main).launch {
            val diary = DiaryServer.getDiaryById(diaryId)

            textviewDate.text = diary.createdString
            textviewDiaryTitle.text = diary.title
            textviewDiaryContents.text = diary.content
            textviewSentiment.text = diary.getTextSentiment()
            textviewSentiment.background.setTint(ContextCompat.getColor(this@DiaryActivity, diary.getSentimentColor()))
            imageViewDiary.background.setTint(ContextCompat.getColor(this@DiaryActivity, diary.getSentimentColor()))
        }
    }

    /**
     * @description - 서버에서 diary의 핵심 키워드 받아와 화면에 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setKeyword() {

        CoroutineScope(Dispatchers.Main).launch {
            val keywords = DiaryServer.getFourWord(diaryId)

            if (keywords.size >= 1) {
                binding.textviewKeyword1.text = getString(R.string.set_diary_keyword, keywords[0].word)
                binding.textviewKeyword1.visibility = View.VISIBLE
            }

            if (keywords.size >= 2) {
                binding.textviewKeyword2.text = getString(R.string.set_diary_keyword, keywords[1].word)
                binding.textviewKeyword2.visibility = View.VISIBLE
            }

            if (keywords.size >= 3) {
                binding.textviewKeyword3.text = getString(R.string.set_diary_keyword, keywords[2].word)
                binding.textviewKeyword3.visibility = View.VISIBLE
            }

            if (keywords.size >= 4) {
                binding.textviewKeyword4.text = getString(R.string.set_diary_keyword, keywords[3].word)
                binding.textviewKeyword4.visibility = View.VISIBLE
            }
        }
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() {

        // Set back button click listener
        binding.buttonBack.setOnClickListener {
            finish()
        }

        // Set share button click listener
        binding.buttonShare.setOnClickListener {
            // Todo: (Not now) 일기 공유하기 기능 추가
            showToast("추후 업데이트 예정입니다 :)")
        }

        // Set detail button click listener
        binding.buttonDetail.setOnClickListener {
            runBlocking {
                showPopupwindow(it)
            }
        }

    }

    /**
     * @description - Detail button 클릭시 보여줄 PopupWindow 셋팅
     * @param - v(View) : 보여질 화면
     * @return - None
     * @author - namsh1125
     */
    private suspend fun showPopupwindow(v: View) {

        val popupWindow = PopupWindow(v)
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Set popup window
        popupWindow.contentView = inflater.inflate(R.layout.view_words_i_wrote, null) // 팝업으로 띄울 화면
        popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) // popup window 크기 설정
        popupWindow.isTouchable = true // popup window 터치 되도록
        popupWindow.isFocusable = true // 포커스

        // popup window 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(BitmapDrawable())

        // Show popup window
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0)

        // Set (Popupwindow) add button click listener
        val buttonAdd = popupWindow.contentView.findViewById<ImageButton>(R.id.button_add)
        buttonAdd.setOnClickListener {

            // Run activity
            val intent = Intent(this, AddWordActivity::class.java)
            intent.putExtra("id", diaryId)
            startActivity(intent)

            // Dismiss popup window
            popupWindow.dismiss()
        }

        // (Popupwindow) reyclerview 설정 및 감정 갯수 설정
        CoroutineScope(Dispatchers.Main).launch {
            val words = DiaryServer.getWord(diaryId)

            FlexboxLayoutManager(this@DiaryActivity).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
            }.let {
                val recyclerView = popupWindow.contentView.findViewById<RecyclerView>(R.id.recyclerview_words_i_wrote)
                recyclerView.layoutManager = it
                recyclerView.adapter = WordsAdapter(words)
            }

            // 기쁨 감정 갯수 설정
            val textviewPleasure = popupWindow.contentView.findViewById<TextView>(R.id.textview_pleasure)
            textviewPleasure.text = getString(R.string.set_diary_pleasure_count, getWordsCount(words, Sentiment.Pleasure))

            // 슬픔 감정 갯수 설정
            val textviewSadness = popupWindow.contentView.findViewById<TextView>(R.id.textview_sadness)
            textviewSadness.text = getString(R.string.set_diary_sadness_count, getWordsCount(words, Sentiment.Sadness))

            // 불안 감정 갯수 설정
            val textviewAnxiety = popupWindow.contentView.findViewById<TextView>(R.id.textview_anxiety)
            textviewAnxiety.text = getString(R.string.set_diary_anxiety_count, getWordsCount(words, Sentiment.Anxiety))

            // 상처 감정 갯수 설정
            val textviewWound = popupWindow.contentView.findViewById<TextView>(R.id.textview_wound)
            textviewWound.text = getString(R.string.set_diary_wound_count, getWordsCount(words, Sentiment.Wound))

            // 당황 감정 갯수 설정
            val textviewEmbarrassment = popupWindow.contentView.findViewById<TextView>(R.id.textview_embarrassment)
            textviewEmbarrassment.text = getString(R.string.set_diary_embarrassment_count, getWordsCount(words, Sentiment.Embarrassment))

            // 분노 감정 갯수 설정
            val textviewAnger = popupWindow.contentView.findViewById<TextView>(R.id.textview_anger)
            textviewAnger.text = getString(R.string.set_diary_anger_count, getWordsCount(words, Sentiment.Anger))
        }
    }

    /**
     * @description - 찾고자 하는 감정으로 사용자가 작성한 단어의 갯수를 알려주는 함수
     * @param - sentiment(Int) : 감정
     * @return - count(Int) : 찾고자 하는 감정으로 사용자가 작성한 단어의 갯수
     * @author - namsh1125
     */
    private fun getWordsCount(words: List<DiaryKeywords>, sentiment: Sentiment): Int {
        return words.filter { it.sentiment == sentiment.value }.size
    }
}