package day4;

import java.util.HashSet;
import java.util.Set;

public class CardParser
{
    public static Card parseCard(String s)
    {
        String substring = s.substring("Card ".length());
        String[] idNumbersPair = substring.split(":");
        int id = Integer.parseInt(idNumbersPair[0].strip());

        String[] numberSets = idNumbersPair[1].split(" \\| ");

        String[] winningNumbersRaw = numberSets[0].strip().split("\s+");
        String[] myNumbersRaw = numberSets[1].strip().split("\s+");

        Set<Integer> winningNumbers = parseStrings(winningNumbersRaw);
        Set<Integer> myNumbers = parseStrings(myNumbersRaw);

        return new Card(id, winningNumbers, myNumbers);
    }

    private static Set<Integer> parseStrings(String[] arr)
    {
        Set<Integer> numbers = new HashSet<>(arr.length << 1);
        for (String s : arr)
        {
            int i = Integer.parseInt(s.strip());
            numbers.add(i);
        }

        return numbers;
    }
}
