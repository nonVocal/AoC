package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 3 - PART 1");


        try (var stream = Files.lines(Path.of(args[0])))
//        try (var stream =
//                     """
//                             467..114..
//                             ...*......
//                             ..35..633.
//                             ......#...
//                             617*......
//                             .....+.58.
//                             ..592.....
//                             ......755.
//                             ...$.*....
//                             .664.598..
//                             """.lines()
//        )
        {
            List<String> strings = stream.collect(Collectors.toList());
            int length = strings.getFirst().length();

            char[] ca = new char[length];
            Arrays.fill(ca, '.');
            String pseudoLine = new String(ca);

            strings.addFirst(pseudoLine);
            strings.addLast(pseudoLine);

            int result = compute(strings);

            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }

    static int compute(List<String> schematic)
    {
        int sum = 0;
        for (int i = 1; i < schematic.size() - 1; i++)
        {
            String line1 = schematic.get(i - 1);
            String line2 = schematic.get(i);
            String line3 = schematic.get(i + 1);

            int buffer = 0;
            boolean foundSign = false;
            for (int j = 0; j < line2.length(); j++)
            {
                char c = line2.charAt(j);
                if (Character.isDigit(c))
                {
                    InspectionResult r = inspectNeighbours(line1, line2, line3, j);

                    foundSign |= r.foundSign;
                    if (r.foundSign || r.rightNeighbourIsDigit || buffer != 0)
                        buffer = buffer * 10 + (c - '0');
                } else if (c == '.')
                {
                    if (buffer != 0)
                        System.out.println(buffer);
                    if (foundSign)
                        sum += buffer;

                    buffer = 0;
                    foundSign = false;
                } else
                {
                    if (buffer != 0)
                    {
                        System.out.println(buffer);
                        sum += buffer;
                        buffer = 0;
                    }
                }

                if (j + 1 == line2.length() && buffer != 0 && foundSign)
                {
                    System.out.println(buffer);
                    sum += buffer;
                }
            }
        }

        return sum;
    }

    static InspectionResult inspectNeighbours(String line1, String line2, String line3, int indexLine2)
    {
        boolean isLastChar = indexLine2 == line2.length() - 1;
        boolean isFirstChar = indexLine2 == 0;

        boolean rightNeighbourIsDigit = !isLastChar && Character.isDigit(line2.charAt(indexLine2 + 1));

        int m1 = indexLine2 - 1, n = indexLine2, p1 = indexLine2 + 1;
        boolean foundSign = (!isFirstChar && isSign(line1.charAt(m1))) || isSign(line1.charAt(n)) || (!isLastChar && isSign(line1.charAt(p1)))
                || (!isFirstChar && isSign(line2.charAt(m1))) || (!isLastChar && isSign(line2.charAt(p1)))
                || (!isFirstChar && isSign(line3.charAt(m1))) || isSign(line3.charAt(n)) || (!isLastChar && isSign(line3.charAt(p1)));

        return new InspectionResult(rightNeighbourIsDigit, foundSign);
    }

    static boolean isSign(char c)
    {
        return c != '.' && !Character.isDigit(c);
    }


    record InspectionResult(boolean rightNeighbourIsDigit, boolean foundSign)
    {

    }
}
