package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Part2
{
    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 4 - PART 1");


        try (var stream = Files.lines(Path.of(args[0])))
//        try (var stream =
//                     """
//                             Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
//                             Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
//                             Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
//                             Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
//                             Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
//                             Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
//                             """.lines()
//        )
        {
            List<Card> cards = stream
                    .map(CardParser::parseCard)
                    .toList();

            int cardCount = cards.size();

            Queue<Card> q = new LinkedList<>(cards);
            while (!q.isEmpty())
            {
                Card card = q.poll();

                Set<Integer> matchingNumbers = new HashSet<>(card.winningNumbers());
                matchingNumbers.retainAll(card.myNumbers());

                int matches = matchingNumbers.size();
                if (matches > 0)
                {
                    List<Card> wonCards = cards.subList(card.id(), card.id() + matches);
                    if (!wonCards.isEmpty())
                    {
                        cardCount += wonCards.size();
                        q.addAll(wonCards);
                    }
                }
            }


            int result = cardCount;

            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }

}
