package gachon.teama.frimo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.R
import gachon.teama.frimo.data.remote.DiaryKeywords
import gachon.teama.frimo.data.remote.Diary.Sentiment

class WordsAdapter(private val dataSet: List<DiaryKeywords>) : RecyclerView.Adapter<WordsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textview: TextView
        init {
            textview = view.findViewById(R.id.textview_word)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_word_i_wrote, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textview.text = dataSet[position].word

        // Set textview background and color
        when(dataSet[position].sentiment){
            Sentiment.Anger.value -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_anger_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.anger))
            }
            Sentiment.Sadness.value -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_sadness_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.sadness))
            }
            Sentiment.Anxiety.value -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_anxiety_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.anxiety))
            }
            Sentiment.Wound.value -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_wound_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.wound))
            }
            Sentiment.Embarrassment.value -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_embarrassment_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.embarrassment))
            }
            Sentiment.Pleasure.value -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_pleasure_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.pleasure))
            }
            else -> {
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.black))
            }
        }
    }

    override fun getItemCount() = dataSet.size
}