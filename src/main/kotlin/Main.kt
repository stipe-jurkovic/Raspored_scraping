import okhttp3.OkHttpClient
import java.io.File
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

suspend fun main() {
    val userNames = readUsernamesFromFile("data/usernames.txt")
    if (userNames.isEmpty()) {
        throw IllegalArgumentException("usernames.txt should contain at least 1 username")
    }
    val dates = readUsernamesFromFile("data/dates.txt")
    if (dates.size != 2) {
        throw IllegalArgumentException("dates.txt should contain exactly 2 dates")
    }
    val startDate = dates.first()
    val endDate = dates[1]

    print("Fetching timetables for ${userNames.size} users from $startDate to $endDate\n")
    println("For each user, a CSV file will be created in the data/timetables directory.")
    println("The bigger time range, the longer it will take to fetch the data for each user.")

    val timetableService = TimetableService(OkHttpClient())
    val timeTableRepository = TimeTableRepository(timetableService)

    val allEvents = userNames.map { userName ->
        try {
            val events = timeTableRepository.fetchTimetable(userName, startDate, endDate)
            saveToCsv(userName, events, startDate, endDate)
            print("$userName done\n")
            events
        } catch (e: Exception) {
            print("Error fetching timetable for $userName: ${e.message}\n")
            emptyList<Event>()
        }
    }

    val periods = mergePeriods(allEvents)
    val freeTimes = flipPeriods(periods, LocalTime.of(8, 0), LocalTime.of(20, 0))

    print("done")
}

fun mergePeriods(allEvents: List<List<Event>>): Map<DayOfWeek, MutableList<PeriodTime>> {
    return allEvents.flatten().groupBy { it.start.dayOfWeek }.toSortedMap().map { (day, per) ->
        val events = per.map { event -> PeriodTime(event.start.toLocalTime(), event.end.toLocalTime()) }.distinct()
            .sortedBy { it.end }.sortedBy { it.start }

        val mergedPeriods = mutableListOf(PeriodTime(events.first().start, events.first().end))
        var lastPeriod: PeriodTime = mergedPeriods.last()

        for (i in 1 until events.size) {
            if (!events[i].overlapsWith(lastPeriod)) {
                mergedPeriods.add(PeriodTime(events[i].start, events[i].end))
                lastPeriod = mergedPeriods.lastOrNull() ?: continue
            }
            for (j in i until events.size) {
                if (events[j].overlapsWith(lastPeriod) && lastPeriod.end < events[j].end)
                    lastPeriod.end = events[j].end
                else break
            }
        }
        return@map day to mergedPeriods
    }.toMap()
}

fun flipPeriods(
    wholeWeekPeriods: Map<DayOfWeek, MutableList<PeriodTime>>, startTime: LocalTime, end: LocalTime
): Map<DayOfWeek, MutableList<PeriodTime>> {

    return wholeWeekPeriods.map { (day, periods) ->
        val freeTimes = mutableListOf<PeriodTime>()
        var start = startTime
        periods.forEach {
            if (it.start > start) {
                freeTimes.add(PeriodTime(start, it.start))
            }
            start = it.end
        }
        if (start < end) {
            freeTimes.add(PeriodTime(start, end))
        }
        return@map day to freeTimes
    }.toMap()
}

fun readUsernamesFromFile(filename: String): List<String> {
    return File(filename).readLines().filter { it.isNotBlank() }
}

fun saveToCsv(userName: String, events: List<Event>, startDate: String, endDate: String) {
    if (File("data/timetables").mkdirs()) {
        print("Directory data created\n")
    }
    val file = File("data/timetables/${userName}_${startDate}_to_${endDate}.csv")
    file.printWriter().use { out ->
        out.println(events.first().toCSVHeader())
        events.forEach { event ->
            out.print(event.toCSV() + "\r\n")
        }
    }
}