package gachon.teama.frimo.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import gachon.teama.frimo.R
import gachon.teama.frimo.retrofit.dao.User
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DiaryAPI {

    // 유저가 작성한 일기를 최신순으로 가져오는 API
    @GET("diary/{userPK}")
    suspend fun getDiary(@Path("userPK") userId: Long) : Response<List<Diary>>

    // 유저가 작성한 일기의 개수를 가져오는 API
    @GET("diary/{userPK}/cnt")
    suspend fun getDiaryCount(@Path("userPK") userId: Long) : Response<Int>

    // 유저가 특정 연도에 작성한 diary를 가져오는 API
    @GET("diary/{userPK}/{year}")
    suspend fun getDiaryByYear(@Path("userPK") userId: Long, @Path("year") year: Int) : List<Diary>

    // 유저가 특정 달에 작성한 diary를 가져오는 API
    @GET("diary/{userPK}/{year}/{month}")
    suspend fun getDiaryByMonth(@Path("userPK") userId: Long, @Path("year") year: Int, @Path("month") month: Int) : List<Diary>

    // 유저가 특정 감정으로 작성한 diary를 가져오는 API
    @GET("diary/{userPK}/mainSent/{sent}")
    suspend fun getDiaryBySentiment(@Path("userPK") userId: Long, @Path("sent") sentiment: Int) : List<Diary> // sent :0~5

    // diary id로 해당 diary 가져오는 API
    @GET("diary/{diaryPK}/only1")
    suspend fun getDiaryById(@Path("diaryPK") diaryId: Long) : Response<Diary>

    // Todo: 키워드, 댓글 추가 방법 알아볼 것
    @Parcelize
    class Diary(
        @SerializedName("diaryPk") val id: Long, // diary 구분자
        @SerializedName("diaryTitle") val title: String, // diary 재목
        @SerializedName("diaryContent") val content: String, // diary 내용
        @SerializedName("user") val user: User, // diary 작성한 사람
        @SerializedName("dateCreated") val created: String, // diary 작성 날짜
        @SerializedName("dateCreatedinString") val createdString: String,
        @SerializedName("dateCreatedYear") val createdYear: Int,
        @SerializedName("dateCreatedMonth") val createdMonth: Int,
        @SerializedName("mainSent") val sentiment: Int, // diary 대표 감정
    ) : Parcelable {

        /**
         * @description - Type 변경 ( toString 같은 느낌 )
         * @param - None
         * @return - sentiment(String) : String으로 변환된 해당 diary의 대표 감정
         * @author - namsh1125
         */
        fun getTextSentiment(): String {
            return when (sentiment) {
                Sentiment.Anger.value -> "#분노"
                Sentiment.Sadness.value -> "#슬픔"
                Sentiment.Anxiety.value -> "#불안"
                Sentiment.Wound.value -> "#상처"
                Sentiment.Embarrassment.value -> "#당황"
                Sentiment.Pleasure.value -> "#기쁨"
                else -> "#에러"
            }
        }

        /**
         * @description - diary의 감정에 맞는 배경화면 색상을 return
         * @param - None
         * @return - color(Int) : 해당 diary의 배경화면 색상
         * @author - namsh1125
         */
        fun getSentimentColor(): Int {
            return when (sentiment) {
                Sentiment.Pleasure.value -> R.color.pleasure
                Sentiment.Sadness.value -> R.color.sadness
                Sentiment.Anxiety.value -> R.color.anxiety
                Sentiment.Wound.value -> R.color.wound
                Sentiment.Embarrassment.value -> R.color.embarrassment
                Sentiment.Anger.value -> R.color.anger
                else -> R.color.black
            }
        }

        enum class Sentiment(val value: Int) {
            Anger(0), Sadness(1), Anxiety(2), Wound(3), Embarrassment(4), Pleasure(5)
        }
    }

}