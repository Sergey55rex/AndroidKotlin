package ru.netology.utils

object Calculator {

    fun calculator(date:Int):String {
        val thousands = date/1000
        val millions = date/1000000
        val result  = when (date) {
            in 0..999 -> date.toString()
            in 1000..1099 -> thousands.toString() + "K"
            in 1100..9999 -> thousands.toString() + "K" + (date - thousands*1000)/100
            in 10000..999999 -> thousands.toString() + "K"
            else -> millions.toString() + "M" + (date - millions*1000000)/100000
        }
        return result
    }

}