import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class CronSchedulerTest {


    @ParameterizedTest
    @MethodSource("cronEntries")
    fun `should return valid output for each cron entry`(entry: String, expected: String) {
        assertEquals(findNearestCronSchedule(entry, 10, 16), expected)
    }

    private companion object {
        @JvmStatic
        fun cronEntries() = Stream.of(
            Arguments.of("30 1 /bin/run_me_daily", "1:30 tomorrow - /bin/run_me_daily"),
            Arguments.of("45 * /bin/run_me_hourly", "16:45 today - /bin/run_me_hourly"),
            Arguments.of("* * /bin/run_me_every_minute", "16:10 today - /bin/run_me_every_minute"),
            Arguments.of("* 19 /bin/run_me_sixty_times", "19:00 today - /bin/run_me_sixty_times"),
        )
    }
}