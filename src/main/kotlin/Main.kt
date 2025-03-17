import okhttp3.OkHttpClient


suspend fun main() {
    val userNames = listOf("sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00","sjurko00",)
    val startDate = "02-24-2024"
    val endDate = "06-06-2024"

    val timetableService = TimetableService(OkHttpClient())
    val timeTableRepository = TimeTableRepository(timetableService)

    val events = userNames.map{ userName ->
        Pair (userName, timeTableRepository.fetchTimetable(userName, startDate, endDate))
    }
    print(events)
}