
interface TimeTableRepositoryInterface {

    suspend fun fetchTimetable(user: String, startDate: String, endDate: String): List<Event>

}
