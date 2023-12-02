package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

public class Part2
{
    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 1 - PART 2");
        try (var stream = Files.lines(Path.of(args[0])))
//        try(var stream = Stream.of("two1nine",
//                "eightwothree",
//                "abcone2threexyz",
//                "xtwone3four",
//                "4nineeightseven2",
//                "zoneight234",
//                "7pqrstsixteen"))

        {
            int result = stream
                    .mapToInt(Part2::computeLineScore)
                    .sum();

            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }


    static int computeLineScore(String line)
    {
        IndexedDigit[] indexedDigits = scanForDigits(line);
        IndexedDigit[] indexedDigits1 = scanForSpelledOutDigits(line);

        List<IndexedDigit> sorted =
                Stream.concat(Arrays.stream(indexedDigits), indexedDigits1 == null ? Stream.empty() : Arrays.stream(indexedDigits1))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(IndexedDigit::index))
                .toList();

        int firstDigit = sorted.getFirst().digit;
        int secondDigit = sorted.getLast().digit;


        int score = firstDigit * 10 + secondDigit;

        System.out.printf("'%s' => %d + %d = %d%n", line, firstDigit, secondDigit, score);

        return score;
    }

    static IndexedDigit[] scanForDigits(String line)
    {
        char[] charArray = line.toCharArray();

        IndexedDigit firstDigit = null;
        for (int i = 0; i < charArray.length; i++)
        {
            if (Character.isDigit(charArray[i]))
            {
                firstDigit = new IndexedDigit(i, Character.digit(charArray[i], 10));
                break;
            }
        }

        IndexedDigit secondDigit = null;
        for (int i = charArray.length - 1; i >= 0; i--)
        {
            if (Character.isDigit(charArray[i]))
            {
                secondDigit = new IndexedDigit(i, Character.digit(charArray[i], 10));
                break;
            }
        }

        return new IndexedDigit[]{firstDigit, secondDigit};
    }

    static IndexedDigit[] scanForSpelledOutDigits(String line)
    {
        List<IndexedDigit> firstDigits = new ArrayList<>();
        List<IndexedDigit> secondDigits = new ArrayList<>();

        // digit size 3
        firstDigits.add(scanForSpelledOutDigits(line, 3));
        secondDigits.add(scanForSpelledOutDigitsReversed(line, 3));

        // digit size 4
        firstDigits.add(scanForSpelledOutDigits(line, 4));
        secondDigits.add(scanForSpelledOutDigitsReversed(line, 4));

        // digit size 5
        firstDigits.add(scanForSpelledOutDigits(line, 5));
        secondDigits.add(scanForSpelledOutDigitsReversed(line, 5));

        List<IndexedDigit> allDigits = new ArrayList<>(firstDigits);
        allDigits.addAll(secondDigits);

        var sorted = allDigits.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(IndexedDigit::index))
                .toList();

        return sorted.isEmpty() ? null : new IndexedDigit[]{sorted.getFirst(), sorted.getLast()};
    }

    static IndexedDigit scanForSpelledOutDigits(String line, int windowSize)
    {
        for (int i = 0; i < line.length() - windowSize + 1; ++i)
        {
            String window = line.substring(i, i+ windowSize);
            OptionalInt oi = convert(window);
            if (oi.isPresent())
                return new IndexedDigit(i, oi.getAsInt());
        }

        return null;
    }

    static IndexedDigit scanForSpelledOutDigitsReversed(String line, int windowSize)
    {
        for (int i = line.length() - windowSize; i >= windowSize; --i)
        {
            String window = line.substring(i, i + windowSize);
            OptionalInt oi = convert(window);
            if (oi.isPresent())
                return new IndexedDigit(i, oi.getAsInt());
        }

        return null;
    }


    static OptionalInt convert(String spelledOutDigit)
    {
        return switch (spelledOutDigit.toLowerCase())
        {
            case "one" -> OptionalInt.of(1);
            case "two" -> OptionalInt.of(2);
            case "three" -> OptionalInt.of(3);
            case "four" -> OptionalInt.of(4);
            case "five" -> OptionalInt.of(5);
            case "six" -> OptionalInt.of(6);
            case "seven" -> OptionalInt.of(7);
            case "eight" -> OptionalInt.of(8);
            case "nine" -> OptionalInt.of(9);
            default -> OptionalInt.empty();
        };
    }

    record IndexedDigit(int index, int digit)
    {

    }
}
