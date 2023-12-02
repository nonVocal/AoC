package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.OptionalInt;
import java.util.function.Function;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 1 - PART 1");

        try (var stream = Files.lines(Path.of(args[0])))
        {
            int result = stream
                    .map(Part1::computeLineScore)
                    .mapToInt(i -> i)
                    .sum();

            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }


    static int computeLineScore(String line)
    {
        OptionalInt firstDigit = line.chars()
                .filter(Character::isDigit)
                .map(c -> Character.digit(c, 10))
                .findFirst();

        int secondDigit = 0;
        char[] charArray = line.toCharArray();
        for (int i = charArray.length - 1; i >= 0; i--)
        {
            if (Character.isDigit(charArray[i]))
            {
                secondDigit = Character.digit(charArray[i], 10);
                break;
            }
        }

        int score = firstDigit.orElse(0) * 10 + secondDigit;

        System.out.printf("'%s' => %d + %d = %d%n", line, firstDigit.getAsInt(), secondDigit,score);

        return score;
    }
}
