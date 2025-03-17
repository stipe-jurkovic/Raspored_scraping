import okhttp3.OkHttpClient
import java.io.File
import java.time.LocalDate


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

    userNames.map{ userName ->
        try {
            val events = timeTableRepository.fetchTimetable(userName, startDate, endDate)
            saveToCsv(userName, events, startDate, endDate)
            print("$userName done\n")
        }
        catch (e: Exception) {
            print("Error fetching timetable for $userName: ${e.message}\n")
        }
    }
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
            out.print(event.toCSV()+"\r\n")
        }
    }
}