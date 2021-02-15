package com.smartproductions.rawnews.models

import android.icu.text.CaseMap
import com.google.gson.annotations.SerializedName
import java.security.CodeSource
import java.util.*

data class Noticia(
        @SerializedName("uuid")
        private val uuid :String,
        @SerializedName("title")
        private val title: String,
        @SerializedName("description")
        private val description: String,
        @SerializedName("keywords")
        private val keywords: String,
        @SerializedName("snippet")
        private val snippet : String,
        @SerializedName("url")
        private val url : String,
        @SerializedName("image_url")
        private val image_url: String,
        @SerializedName("language")
        private val language : String,
        @SerializedName("published_at")
        private val published_at: Date,
        @SerializedName("source")
        private val source: String,
        @SerializedName("categories")
        private val categories: List<String>
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