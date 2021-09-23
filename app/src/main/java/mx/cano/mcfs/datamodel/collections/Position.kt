package mx.cano.mcfs.datamodel.collections

import java.util.*

data class Position(
    val date: String = now(),
    val latitude: Double? = null,
    val longitude: Double? = null
)

private fun now(): String{
    val calendar = Calendar.getInstance()
    return "${calendar.get(Calendar.YEAR)}-${twoDigits(calendar.get(Calendar.MONTH) + 1)}-${twoDigits(calendar.get(Calendar.DAY_OF_MONTH))}T${twoDigits(calendar.get(Calendar.HOUR_OF_DAY))}:${twoDigits(calendar.get(Calendar.MINUTE))}:${twoDigits(calendar.get(Calendar.SECOND))}"
}

private fun twoDigits(value: Int): String{
    if (value in 0..9){
        return "0$value"
    }
    return "$value"
}