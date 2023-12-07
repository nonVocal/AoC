package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2
{
    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 7 - PART 2");

        try (var stream = Files.lines(Path.of(args[0])))
//        // test input
//        try (var stream =
//                     """
//                             32T3K 765
//                             T55J5 684
//                             KK677 28
//                             KTJJT 220
//                             QQQJA 483
//                                   """.lines()
//        )
        {
            List<CamelCardsRound> sorted = stream.map(Part2::read)
                    .sorted()
                    .toList();

            sorted = sorted.reversed();

            int result = 0;
            int rank = sorted.size();
            for (CamelCardsRound camelCardsRound : sorted)
            {
                result += camelCardsRound.bid() * rank--;
            }



            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }

    static CamelCardsRound read(String line)
    {
        String[] split = line.strip().split("\\s+");
        int length = split[0].length();

        CamelCard[] cards = new CamelCard[length];
        for (int i = 0; i < length; i++)
        {
            cards[i] = new CamelCard(split[0].charAt(i), true);
        }

        return new CamelCardsRound(new CamelCardHandJokerRule(cards), Integer.parseInt(split[1].strip()));
    }

    record CamelCardsRound(CamelCardHandJokerRule hand, int bid) implements Comparable<CamelCardsRound>
    {
        @Override
        public int compareTo(CamelCardsRound o)
        {
            return hand.compareTo(o.hand);
        }

        @Override
        public String toString()
        {
            return "CamelCardsRound{" +
                    "hand=" + hand +
                    ", bid=" + bid +
                    '}';
        }
    }
}
