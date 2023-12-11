package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Part2
{

    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 9 - PART 1");

        try (var stream = Files.lines(Path.of(args[0])))
//        try (var stream =
//                     """
//                             0 3 6 9 12 15
//                             1 3 6 10 15 21
//                             10 13 16 21 30 45
//                             """.lines()
//        )
        {
            long result = stream
                    .mapToLong(Part2::extrapolate)
                    .sum();


            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }

    static long extrapolate(String line)
    {
        String[] historyRaw = line.strip().split("\\s+");
        int[] history = Arrays.stream(historyRaw).mapToInt(Integer::parseInt).toArray();

        return history[0] - extrapolate(history);
    }

    static long extrapolate(int[] history)
    {
        if (Arrays.stream(history).dropWhile(i -> i == 0).toArray().length == 0)
            return 0;

        int[] e = new int[history.length - 1];
        for (int i = 0; i < history.length - 1; i++)
            e[i] = history[i + 1] - history[i];

        return e[0] - extrapolate(e);
    }

}