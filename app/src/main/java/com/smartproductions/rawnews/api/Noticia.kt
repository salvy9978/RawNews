package com.smartproductions.rawnews.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/* Usar esta cuando se consulta por varias noticias, temas o se busca */
data class NoticiasResponse (
    @SerializedName("meta")
    var meta: Meta? = null,
    @SerializedName("data")
    var data: List<Noticia>? = null,
    @SerializedName("error")
    var error: Error? = null
)

/* Usar esta unicamente cuando se pregunte por una noticia concreta */
data class NoticiaRespone(
    @SerializedName("uuid")
    var uuid: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("keywords")
    var keywords: String? = null,
    @SerializedName("snippet")
    var snippet: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null,
    @SerializedName("language")
    var language: String? = null,
    @SerializedName("published_at")
    var publishedAt: Date? = null,
    @SerializedName("source")
    var source: String? = null,
    @SerializedName("categories")
    var categories: List<String>? = null,
    @SerializedName("relevance_score")
    var relevanceScore: Double? = null,
    @SerializedName("locale")
    var locale: String? = null,
    @SerializedName("error")
    var error: Error? = null
)

@Parcelize
data class Noticia (
    @SerializedName("uuid")
    var uuid: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("keywords")
    var keywords: String? = null,
    @SerializedName("snippet")
    var snippet: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null,
    @SerializedName("language")
    var language: String? = null,
    @SerializedName("published_at")
    var publishedAt: Date? = null,
    @SerializedName("source")
    var source: String? = null,
    @SerializedName("categories")
    var categories: List<String>? = null,
    @SerializedName("relevance_score")
    var relevanceScore: Double? = null,
    @SerializedName("locale")
    var locale: String? = null
) : Parcelable


class Meta(
    @SerializedName("found")
    var found: Int? = null,
    @SerializedName("returned")
    var returned: Int? = null,
    @SerializedName("limit")
    var limit: Int? = null,
    @SerializedName("page")
    var page: Int? = null
)


class Error(
    @SerializedName("code")
    var code: String? = null,
    @SerializedName("message")
    var message: String? = null
)



/* EXAMPLE
{
            "uuid": "1399ccc7-c5ef-4090-9820-ce30de3a15a8",
            "title": "40-year-old doctor's half-burnt body found in forest in UP; 2 held",
            "description": "The half-burnt body of a 40-year-old doctor, who had gone missing on February 11, was found in a forest area here, police said on Sunday. Jaikaran Pra",
            "keywords": "Lucknow news, Lucknow latest news, Lucknow news live, Lucknow news today, Today news Lucknow, Santosh Tiwari, Prajapati, forest area, Crime in UP, amethi",
            "snippet": "AMETHI: The half-burnt body of a 40-year-old doctor, who had gone missing on February 11, was found in a forest area here, police said on Sunday.Jaikaran Prajap...",
            "url": "https://timesofindia.indiatimes.com/city/lucknow/40-year-old-doctors-half-burnt-body-found-in-forest-in-up-2-held/articleshow/80911792.cms",
            "image_url": "https://static.toiimg.com/thumb/msid-80911811,width-1070,height-580,imgsize-182867,resizemode-75,overlay-toi_sw,pt-32,y_pad-40/photo.jpg",
            "language": "en",
            "published_at": "2021-02-14T19:17:05.000000Z",
            "source": "timesofindia.indiatimes.com",
            "categories": [
                "general"
            ],
            "relevance_score": null
        }


 */
