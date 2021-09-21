package mx.cano.mcfs.datamodel.collections

data class Device(
    val uuid: String? = null,
    val name: String? = null,
    var positions: List<Position>? = emptyList()
)
