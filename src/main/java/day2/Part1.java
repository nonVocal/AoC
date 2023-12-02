package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 2 - PART 1");

        try (var stream = Files.lines(Path.of(args[0])))
//        try (var stream =
//                """
//                        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
//                        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
//                        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
//                        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
//                        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
//                        """.lines()
//        )
        {
            int result = stream
                    .mapToInt(Part1::computeLineScore)
                    .sum();

            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }

    static int computeLineScore(String line)
    {
        Game game = GameParser.parse(line);
        return test(game) ? game.getId() : 0;
    }

    static boolean test(Game g)
    {
        return (g.getRedCubes() <= 12 && g.getGreenCubes() <= 13 && g.getBlueCubes() <= 14);
    }
}

