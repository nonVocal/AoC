package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Part1
{
    static Map<Metric, long[]> metrics = new HashMap<>();

    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 6 - PART 1");

//        try (var stream = Files.lines(Path.of(args[0])))
        // test input
//        try (var stream =
//                     """
//                             Time:      7  15   30
//                             Distance:  9  40  200
//                                   """.lines()
//        )
        // puzzle input
        try (var stream =
                     """
                             Time:        53     71     78     80
                             Distance:   275   1181   1215   1524
                                   """.lines()
        )
        {
            stream.forEach(Part1::read);

            long result = compute();

            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }

    static void read(String line)
    {
        String[] split = line.strip().split(":");
        switch (split[0].strip())
        {
            case "Time" -> metrics.put(Metric.TIME, parseNumbers(split[1]));
            case "Distance" -> metrics.put(Metric.DISTANCE, parseNumbers(split[1]));
            default -> throw new IllegalArgumentException("invalid metric: '" + split[0].strip() + "'");
        }
    }

    static long[] parseNumbers(String raw)
    {
        String[] split = raw.strip().split("\\s+");
        long[] numbers = new long[split.length];

        for (int i = 0; i < split.length; i++)
            numbers[i] = Long.parseLong(split[i]);

        return numbers;
    }

    static long compute()
    {
        long[] times = metrics.get(Metric.TIME);
        long[] distances = metrics.get(Metric.DISTANCE);

        long[] waysToWin = new long[times.length];

        for (int race = 0; race < times.length; race++)
        {
            long raceTime = times[race];
            long distanceToBeat = distances[race];

            long lower = 0, upper = 0;
            for (int timeButtonsPressed = 1; timeButtonsPressed < raceTime - 1;  timeButtonsPressed++)
            {
                if (wins(raceTime, distanceToBeat, timeButtonsPressed))
                {
                    lower = timeButtonsPressed;
                    break;
                }
            }

            for (long timeButtonsPressed = raceTime - 1; timeButtonsPressed > 0;  timeButtonsPressed--)
            {
                if (wins(raceTime, distanceToBeat, timeButtonsPressed))
                {
                    upper = timeButtonsPressed;
                    break;
                }
            }

            waysToWin[race] = upper - lower + 1;
        }

        return Arrays.stream(waysToWin).reduce((x, y) -> x * y).getAsLong();
    }

    static boolean wins(long raceTime, long recordDistance, long timeButtonPressed)
    {
        return timeButtonPressed < (raceTime - (recordDistance / timeButtonPressed));
    }

    enum Metric{
        TIME, DISTANCE
    }
}
