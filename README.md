# CronSchedulerParser

## Task

We have a set of tasks, each running at least daily, which are scheduled with a simplified cron. We want to find when
each of them will next run.

The scheduler config looks like this:

```
30 1 /bin/run_me_daily
45 * /bin/run_me_hourly
* * /bin/run_me_every_minute
* 19 /bin/run_me_sixty_times
```

The first field is the minutes past the hour, the second field is the hour of the day and the third is the command to
run. For both cases * means that it should run for all values of that field. In the above example run_me_daily has been
set to run at 1:30am every day and run_me_hourly at 45 minutes past the hour every hour. The fields are whitespace
separated and each entry is on a separate line.

Write a command line program that when fed this config to stdin and the simulated 'current time' in the format HH:MM as
command line argument outputs the soonest time at which each of the commands will fire and whether it is today or
tomorrow.

> Note: When the task should fire at the simulated 'current time' then that is the time you should output, not the next one.

### Example
Given the above examples as input and the simulated 'current time' command-line argument 16:10 the output should be

```
1:30 tomorrow - /bin/run_me_daily
16:45 today - /bin/run_me_hourly
16:10 today - /bin/run_me_every_minute
19:00 today - /bin/run_me_sixty_times
```

## Source 
This source code of the solution can be found in [CronScheduler.kt](/src/main/kotlin/CronScheduler.kt)

To validate the solution, unit test is added [here](/src/test/kotlin/CronSchedulerTest.kt). 

## How to run
A [jar file](cronscheduler.jar) of the solution is provided in this repository. You can run that with the input configuration as following:
```
cat input.txt | java -jar cronscheduler.jar <scheduled_time>
```

To run the test cases, open the program in IntelliJ, and run the test case directly.
