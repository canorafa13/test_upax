package mx.cano.mcfs.datamodel

import android.view.View

data class HomeOption(
    val title: String,
    val description: String,
    val action: View.OnClickListener
)