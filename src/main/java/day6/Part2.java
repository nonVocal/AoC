package day6;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Part2
{
    static Map<Metric, Long> metrics = new HashMap<>();

    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 6 - PART 2");

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
            stream.forEach(Part2::read);

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
            case "Time" -> metrics.put(Metric.TIME, parseNumber(split[1]));
            case "Distance" -> metrics.put(Metric.DISTANCE, parseNumber(split[1]));
            default -> throw new IllegalArgumentException("invalid metric: '" + split[0].strip() + "'");
        }
    }

    static long parseNumber(String raw)
    {
        String split = raw.strip().replaceAll("\\s+", "");
        return Long.parseLong(split);
    }

    static long compute()
    {
        long raceTime = metrics.get(Metric.TIME);
        long distanceToBeat = metrics.get(Metric.DISTANCE);

        long lower = 0, upper = 0;
        for (int timeButtonsPressed = 1; timeButtonsPressed < raceTime - 1; timeButtonsPressed++)
        {
            if (wins(raceTime, distanceToBeat, timeButtonsPressed))
            {
                lower = timeButtonsPressed;
                break;
            }
        }

        for (long timeButtonsPressed = raceTime - 1; timeButtonsPressed > 0; timeButtonsPressed--)
        {
            if (wins(raceTime, distanceToBeat, timeButtonsPressed))
            {
                upper = timeButtonsPressed;
                break;
            }
        }

        return upper - lower + 1;
    }

    static boolean wins(long raceTime, long recordDistance, long timeButtonPressed)
    {
        return timeButtonPressed < (raceTime - (recordDistance / timeButtonPressed));
    }

    enum Metric
    {
        TIME, DISTANCE
    }
}