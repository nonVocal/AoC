package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part2
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
//            List<List<Integer>> allPartNumbers = new ArrayList<>();

            String line1 = schematic.get(i - 1);
            String line2 = schematic.get(i);
            String line3 = schematic.get(i + 1);

            for (int j = 0; j < line2.length(); j++)
            {
                char c = line2.charAt(j);
                if (c == '*')
                {
                    List<Integer> partNumbers = inspectNeighbours(line1, line2, line3, j);
//                    allPartNumbers.add(partNumbers);
                    if (partNumbers.size() == 2)
                        sum += partNumbers.getFirst() * partNumbers.getLast();
                }
            }
            // for debugging
            //            System.out.println("############################");
            //            System.out.println(line1);
            //            System.out.println(line2);
            //            System.out.println(line3);
            //            System.out.println(allPartNumbers.stream().map(l -> l.stream().map(Object::toString).collect(Collectors.joining(",", "[", "]"))).collect(Collectors.joining("\n")));
            //            System.out.println("############################");
        }

        return sum;
    }

    static List<Integer> inspectNeighbours(String line1, String line2, String line3, int indexLine2)
    {
        boolean isLastChar = indexLine2 == line2.length() - 1;
        boolean isFirstChar = indexLine2 == 0;

        int m1 = indexLine2 - 1, n = indexLine2, p1 = indexLine2 + 1;

        List<Integer> partNumbers = new ArrayList<>();

        if (!isFirstChar)
        {
            if (Character.isDigit(line2.charAt(m1)))
            {
                int pos = m1 - 1;
                while (pos > 0 && Character.isDigit(line2.charAt(pos)))
                    pos--;

                partNumbers.add(Integer.parseInt(line2.substring(pos == 0 && Character.isDigit(line2.charAt(0)) ? 0 : pos + 1, n)));
            }
        }

        if (!isLastChar)
        {
            if (Character.isDigit(line2.charAt(p1)))
            {
                int pos = p1 + 1;
                while (pos < line2.length() && Character.isDigit(line2.charAt(pos)))
                    pos++;

                partNumbers.add(Integer.parseInt(line2.substring(n + 1, pos)));
            }
        }

        if (isFirstChar)
        {
            int startLine1 = Character.isDigit(line1.charAt(n)) ? n : Character.isDigit(line1.charAt(p1)) ? p1 : line1.length();
            if (startLine1 < line1.length())
            {
                int pos = startLine1 + 1;
                while (pos < line1.length() && Character.isDigit(line1.charAt(pos)))
                    pos++;

                partNumbers.add(Integer.parseInt(line1.substring(startLine1, pos)));
            }

            int startLine3 = Character.isDigit(line3.charAt(n)) ? n : Character.isDigit(line3.charAt(p1)) ? p1 : line3.length();
            if (startLine3 < line3.length())
            {
                int pos = startLine3 + 1;
                while (pos < line3.length() && Character.isDigit(line3.charAt(pos)))
                    pos++;

                partNumbers.add(Integer.parseInt(line3.substring(startLine3, pos + 1)));
            }
        } else if (isLastChar)
        {
            int endLine1 = Character.isDigit(line1.charAt(n)) ? n : Character.isDigit(line1.charAt(p1)) ? p1 : -1;
            if (endLine1 > -1)
            {
                int pos = endLine1 - 1;
                while (pos > 0 && Character.isDigit(line1.charAt(pos)))
                    pos--;

                partNumbers.add(Integer.parseInt(line1.substring(pos, endLine1 + 1)));
            }

            int endLine3 = Character.isDigit(line3.charAt(n)) ? n : Character.isDigit(line3.charAt(p1)) ? p1 : -1;
            if (endLine3 > -1)
            {
                int pos = endLine3 - 1;
                while (pos > 0 && Character.isDigit(line3.charAt(pos)))
                    pos--;

                partNumbers.add(Integer.parseInt(line3.substring(pos, endLine3)));
            }
        } else
        {
            if (Character.isDigit(line1.charAt(n)))
            {
                int startLine1 = n - 1;
                int endLine1 = n + 1;

                while (startLine1 > 0 && Character.isDigit(line1.charAt(startLine1)))
                    startLine1--;

                while (endLine1 < line1.length() && Character.isDigit(line1.charAt(endLine1)))
                    endLine1++;

                partNumbers.add(Integer.parseInt(line1.substring(startLine1 == 0 && Character.isDigit(line1.charAt(0)) ? 0 : startLine1 + 1, endLine1)));

            } else
            {
                if (Character.isDigit(line1.charAt(m1)))
                {
                    int start = m1 - 1;

                    while (start > 0 && Character.isDigit(line1.charAt(start)))
                        start--;

                    partNumbers.add(Integer.parseInt(line1.substring(start == 0 && Character.isDigit(line1.charAt(0)) ? 0 : start + 1, n)));
                }

                if (Character.isDigit(line1.charAt(p1)))
                {
                    int end = p1 + 1;
                    while (end < line1.length() && Character.isDigit(line1.charAt(end)))
                        end++;

                    partNumbers.add(Integer.parseInt(line1.substring(p1, end)));
                }
            }

            if (Character.isDigit(line3.charAt(n)))
            {
                int startLine3 = n - 1;
                int endLine3 = n + 1;

                while (startLine3 > 0 && Character.isDigit(line3.charAt(startLine3)))
                    startLine3--;

                while (endLine3 < line3.length() && Character.isDigit(line3.charAt(endLine3)))
                    endLine3++;

                partNumbers.add(Integer.parseInt(line3.substring(startLine3 + 1, endLine3)));
            } else
            {
                if (Character.isDigit(line3.charAt(m1)))
                {
                    int start = m1 - 1;

                    while (start > 0 && Character.isDigit(line3.charAt(start)))
                        start--;

                    partNumbers.add(Integer.parseInt(line3.substring(start == 0 && Character.isDigit(line3.charAt(0)) ? 0 : start + 1, n)));
                }

                if (Character.isDigit(line3.charAt(p1)))
                {
                    int end = p1 + 1;
                    while (end < line3.length() && Character.isDigit(line3.charAt(end)))
                        end++;

                    partNumbers.add(Integer.parseInt(line3.substring(p1, end)));
                }
            }
        }

        return partNumbers;
    }

}
