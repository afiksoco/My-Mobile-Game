data class Scorelist(
    val name: String,
    val allScores: List<Score>
)
//{
//    class Builder(
//        var name: String = "",
//        var allScores: List<Score> = mutableListOf()
//    ) {
//        fun name(name: String) = apply { this.name = name }
//        fun addScore(score: Score) = apply { (this.allScores as MutableList).add(score) }
//        fun build() = Scorelist(name, allScores)
//    }
//}
