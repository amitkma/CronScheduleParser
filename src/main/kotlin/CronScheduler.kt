import java.util.*
import kotlin.IllegalArgumentException

fun main(args: Array<String>) {
    // Current time as first argument.
    val currentTime = args[0]
    val hourMins = currentTime.split(':')
    if (hourMins.size != 2) {
        throw IllegalArgumentException("current time must be in format HH:MM. For example: 16:10")
    }

    val currentHour = hourMins[0].toInt()
    val currentMin = hourMins[1].toInt()
    validateTime(currentHour, currentMin)

    // Read cron entries input to stdin
    val scanner = Scanner(System.`in`)
    while (scanner.hasNextLine()) {
        val cronEntry = scanner.nextLine()

        val output = findNearestCronSchedule(cronEntry, currentMin, currentHour)
        println(output)
    }
}

/**
 * Parses the schedule of the cron entry and finds the nearest time at which the given entry will be fired.
 */
fun findNearestCronSchedule(entry: String, currentMin: Int, currentHour: Int): String {
    val cronEntry = entry.split(' ')
    if (cronEntry.size > 3) {
        throw IllegalArgumentException("Each cron entry can have maximum 3 fields")
    }

    val cronJobMin = cronEntry[0]
    val cronJobHour = cronEntry[1]
    val cronCommand = cronEntry[2]

    val (nearestHour, nearestMin) = when {
        cronJobHour == "*" && cronJobMin == "*" -> Pair(currentHour, currentMin)
        cronJobHour == "*" && cronJobMin != "*" -> {
            val hour = if (cronJobMin.toInt() < currentMin) {
                currentHour + 1
            } else currentHour

            Pair(hour % 24, cronJobMin.toInt())
        }
        cronJobHour != "*" && cronJobMin == "*" -> {
            val min = if (cronJobHour.toInt() != currentHour) {
                0
            } else currentMin
            Pair(cronJobHour.toInt(), min)
        }
        else -> Pair(cronJobHour.toInt(), cronJobMin.toInt())
    }

    validateTime(nearestHour, nearestMin)

    // Check if the task will be executed today or tomorrow
    val executionDay = if ((nearestHour * 60 + nearestMin) < (currentHour * 60 + currentMin)) {
        "tomorrow"
    } else "today"

    return String.format("%d:%02d %s - %s", nearestHour, nearestMin, executionDay, cronCommand)
}

/**
 * Validates if the given time in range or throws [IllegalArgumentException] if not.
 */
private fun validateTime(hour: Int, minutes: Int) {
    if (hour < 0 || hour > 23 || minutes < 0 || minutes > 59) {
        throw IllegalArgumentException("hour or minutes must be in their range")
    }
}
