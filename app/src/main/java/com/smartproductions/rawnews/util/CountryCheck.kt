package com.smartproductions.rawnews.util

class CountryCheck {

    fun localeCheck(locale: String):String{
        var locale = locale
        locale = when (locale) {
            "ar" -> "ar"
            "am" -> "am"
            "au" -> "au"
            "at" -> "at"
            "by" -> "by"
            "be" -> "be"
            "bo" -> "bo"
            "br" -> "br"
            "bg" -> "bg"
            "ca" -> "ca"
            "cl" -> "cl"
            "cn" -> "cn"
            "co" -> "co"
            "hr" -> "hr"
            "cz" -> "cz"
            "ec" -> "ec"
            "eg" -> "eg"
            "fr" -> "fr"
            "de" -> "de"
            "gr" -> "gr"
            "hn" -> "hn"
            "hk" -> "hk"
            "in" -> "in"
            "id" -> "id"
            "ir" -> "ir"
            "ie" -> "ie"
            "il" -> "il"
            "it" -> "it"
            "jp" -> "jp"
            "kr" -> "kr"
            "mx" -> "mx"
            "nl" -> "nl"
            "nz" -> "nz"
            "ni" -> "ni"
            "pk" -> "pk"
            "pa" -> "pa"
            "pe" -> "pe"
            "pl" -> "pl"
            "pt" -> "pt"
            "qa" -> "qa"
            "ro" -> "ro"
            "ru" -> "ru"
            "sa" -> "sa"
            "za" -> "za"
            "es" -> "es"
            "ch" -> "ch"
            "sy" -> "sy"
            "tw" -> "tw"
            "th" -> "th"
            "tr" -> "tr"
            "ua" -> "ua"
            "gb" -> "gb"
            "us" -> "us"
            "uy" -> "uy"
            "ve" -> "ve"
            else -> "gb"
        }
        return locale
    }

    fun languageCheck(language: String):String{
        var language = language
        language = when (language) {
            "ar" -> "ar"
            "bg" -> "bg"
            "bn" -> "bn"
            "cs" -> "cs"
            "da" -> "da"
            "de" -> "de"
            "el" -> "el"
            "en" -> "en"
            "es" -> "es"
            "et" -> "et"
            "fa" -> "fa"
            "fi" -> "fi"
            "fr" -> "fr"
            "he" -> "he"
            "hi" -> "hi"
            "hr" -> "hr"
            "hu" -> "hu"
            "id" -> "id"
            "it" -> "it"
            "ja" -> "ja"
            "ko" -> "ko"
            "lt" -> "lt"
            "nl" -> "nl"
            "no" -> "no"
            "pl" -> "pl"
            "pt" -> "pt"
            "ro" -> "ro"
            "ru" -> "ru"
            "sk" -> "sk"
            "sv" -> "sv"
            "ta" -> "ta"
            "th" -> "th"
            "tr" -> "tr"
            "uk" -> "uk"
            "vi" -> "vi"
            "zh" -> "zh"
            else -> "en"
        }


        return language
    }

}