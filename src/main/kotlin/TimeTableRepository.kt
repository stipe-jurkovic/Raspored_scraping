

class TimeTableRepository(
    private val timetableService: TimetableServiceInterface
) : TimeTableRepositoryInterface {


    override suspend fun fetchTimetable(
        user: String,
        startDate: String,
        endDate: String
    ): List<Event> {
        val params: HashMap<String, String> = hashMapOf(
            "DataType" to "User",
            "DataId" to user,
            "MinDate" to startDate,
            "MaxDate" to endDate
        )

        when (val result = timetableService.fetchTimeTable(params = params)) {
            is NetworkServiceResult.TimeTableResult.Success -> {
                val events = parseTimetable(result.data)
                return events
            }
            is NetworkServiceResult.TimeTableResult.Failure -> {
                throw Exception("Timetable fetching error")
            }
        }
    }

    companion object {
        private val TAG = this.javaClass.canonicalName
    }

}