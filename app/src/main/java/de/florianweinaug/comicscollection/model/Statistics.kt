package de.florianweinaug.comicscollection.model

data class Statistics(val comicsTotal: Int, val comicsRead: Int, val comicsConcluded: Int,
                      val issuesTotal: Int, val issuesRead: Int,
                      val publisherCount: List<PublisherCount>) {

    val issuesAverage: Double
        get() {
            if (comicsTotal <= 0)
                return 0.0

            return issuesTotal / comicsTotal.toDouble()
        }

    val comicsReadPercentage: Int
        get() {
            if (comicsTotal <= 0)
                return 0

            return comicsRead * 100 / comicsTotal
        }

    val issuesReadPercentage: Int
        get() {
            if (issuesTotal <= 0)
                return 0

            return issuesRead * 100 / issuesTotal
        }
}