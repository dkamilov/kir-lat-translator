package com.damir.android.translator.utils

val abayMessage =
    "Абай (Ибраһим) Құнанбаев (1845-1904) — ақын, ағартушы, " +
            "жазба қазақ әдебиетінің, " +
            "қазақ әдеби тілінің негізін қалаушы, " +
            "философ, композитор"
val salemMessage = "Сәлем, қалайсың?"

class KirLatTranslator {
    companion object {
        private val kirLatWords = mapOf(
            'а' to "a",
            'ә' to "á",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'ғ' to "ǵ",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "j",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'қ' to "q",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'ң' to "ń",
            'о' to "o",
            'ө' to "ó",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "ý",
            'ұ' to "u",
            'ү' to "ú",
            'ф' to "f",
            'х' to "h",
            'һ' to "h",
            'ц' to "ts",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "щ",
            'ъ' to "ъ",
            'ы' to "y",
            'і' to "i",
            'ь' to "ь",
            'э' to "e",
            'ю' to "ıý",
            'я' to "ia"
        )
        fun translate(msg: String): String {
            val builder = StringBuilder()
            for(c in msg) {
                if(kirLatWords.containsKey(c.toLowerCase())) {
                    if(c.isUpperCase()) {
                        val latin =
                            makeUpperCase(
                                c
                            )
                        builder.append(latin)
                    }else{
                        builder.append(kirLatWords[c])
                    }
                }else {
                    if(c.toLowerCase().toInt() == 601) {
                        if(c.isUpperCase())
                            builder.append('á'.toUpperCase())
                        else
                            builder.append('á')
                    }else {
                        builder.append(c)
                    }

                }
            }
            return builder.toString()
        }

        private fun makeUpperCase(c: Char): String{
            val latin = kirLatWords[c.toLowerCase()]!!
            return latin[0].toUpperCase() + latin.substring(1)
        }
    }
}