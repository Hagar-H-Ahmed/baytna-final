package com.example.baytna.Const

fun capitalizeEachWord(name: String?): String {
    return name?.split(" ")
        ?.joinToString(" ") { word -> word.capitalize() }
        ?: ""
}