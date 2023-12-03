



//####################################
// does not work, and currently I don't
// want to investigate java-simd stuff.
//####################################













//package day1;
//
//
//import jdk.incubator.vector.*;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Objects;
//
//public class Part2V3
//{
//    public static void main(String[] args) throws IOException
//    {
//        Instant start = Instant.now();
//        System.out.println("DAY 1 - PART 2V3");
//        try (var stream = Files.lines(Path.of(args[0])))
////        try(var stream = Stream.of("two1nine",
////                "eightwothree",
////                "abcone2threexyz",
////                "xtwone3four",
////                "4nineeightseven2",
////                "zoneight234",
////                "7pqrstsixteen"))
//
//        {
//            int result = stream
//                    .mapToInt(Part2V3::computeLineScore)
//                    .sum();
//
//            System.out.println(result);
//        }
//        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
//    }
//
//
//    static int computeLineScore(String line)
//    {
//        char[] chars = line.toLowerCase().toCharArray();
//        byte[] bytes = line.toLowerCase().getBytes();
//        int firstDigit = searchFirstDigit(bytes);
//        int secondDigit = searchSecondDigit(chars);
//
//        int score = firstDigit * 10 + secondDigit;
//
//        System.out.printf("'%s' => %d + %d = %d%n", line, firstDigit, secondDigit, score);
//
//        return score;
//    }
//
//    static final byte zeroAsciiByte = "0".getBytes()[0];
//    static final byte nineAsciiByte = "9".getBytes()[0];
//
//    static int searchFirstDigit(byte[] bytes)
//    {
//        int found = Integer.MAX_VALUE;
//
//        VectorSpecies<Byte> speciesPreferred = ByteVector.SPECIES_PREFERRED;
////        for (int i = 0; i < bytes.length; i += speciesPreferred.length())
//        int lindex = 0;
//        for (; lindex < speciesPreferred.loopBound(bytes.length); lindex += speciesPreferred.length())
//        {
////            System.out.println("i=" + i);
////            System.out.println("len=" + speciesPreferred.length());
////
////            int i1 = Objects.checkIndex(i, bytes.length - (speciesPreferred.length() - 1));
//
//
//            ByteVector v1 = ByteVector.fromArray(speciesPreferred, bytes, lindex);
//            var lower = v1.compare(VectorOperators.GE, zeroAsciiByte);
//            var upper = v1.compare(VectorOperators.LE, nineAsciiByte);
//
//            var or = lower.and(upper);
//
//            int index = or.firstTrue();
//
//            found = Math.min(found, index + lindex);
//        }
//
//        for (int i = lindex; i < bytes.length; ++i )
//        {
//            if (bytes[i] >= zeroAsciiByte && bytes[i] <= nineAsciiByte)
//                found = i;
//        }
//
//
//        if (found < bytes.length)
//        {
////            return found;
//            return bytes[found] - zeroAsciiByte;
//        }
//
//
//        char[] chars = new String(bytes).toCharArray();
//
//
//        for (int i = 0; i < chars.length; i++)
//        {
//            int p1 = i + 1, p2 = i + 2, p3 = i + 3, p4 = i + 4;
//
//
//            char c = chars[i];
////            if (Character.isDigit(c))
////                return Character.digit(c, 10);
////            else
//            if (c == 'o' && p2 < chars.length && chars[p1] == 'n' && chars[p2] == 'e')
//                return 1;
//            else if (c == 't')
//            {
//                if (p2 < chars.length && chars[p1] == 'w' && chars[p2] == 'o')
//                    return 2;
//                else if (p4 < chars.length && chars[p1] == 'h' && chars[p2] == 'r' && chars[i + 3] == 'e' && chars[p4] == 'e')
//                    return 3;
//
//            } else if (c == 'f')
//            {
//                if (p3 < chars.length && chars[p1] == 'o' && chars[p2] == 'u' && chars[p3] == 'r')
//                    return 4;
//                else if (p3 < chars.length && chars[p1] == 'i' && chars[p2] == 'v' && chars[p3] == 'e')
//                    return 5;
//
//            } else if (c == 's')
//            {
//                if (p2 < chars.length && chars[p1] == 'i' && chars[p2] == 'x')
//                    return 6;
//                else if (p4 < chars.length && chars[p1] == 'e' && chars[p2] == 'v' && chars[i + 3] == 'e' && chars[p4] == 'n')
//                    return 7;
//
//            } else if (c == 'e' && p4 < chars.length && chars[p1] == 'i' && chars[p2] == 'g' && chars[p3] == 'h' && chars[p4] == 't')
//                return 8;
//            else if (c == 'n' && p3 < chars.length && chars[p1] == 'i' && chars[p2] == 'n' && chars[p3] == 'e')
//                return 9;
//        }
//        return -1;
//    }
//
//    static int searchSecondDigit(char[] chars)
//    {
//        for (int i = chars.length - 1; i >= 0; i--)
//        {
//            int m1 = i - 1, m2 = i - 2, m3 = i - 3, m4 = i - 4;
//
//            char c = chars[i];
//            if (Character.isDigit(c))
//                return Character.digit(c, 10);
//            else if (c == 'e')
//            {
//                if (m2 >= 0 && chars[m1] == 'n' && chars[m2] == 'o')
//                    return 1;
//                else if (m4 >= 0 && chars[m1] == 'e' && chars[m2] == 'r' && chars[m3] == 'h' && chars[m4] == 't')
//                    return 3;
//                else if (m3 >= 0)
//                {
//                    if (chars[m1] == 'v' && chars[m2] == 'i' && chars[m3] == 'f')
//                        return 5;
//                    else if (chars[m1] == 'n' && chars[m2] == 'i' && chars[m3] == 'n')
//                        return 9;
//                }
//            } else if (c == 'o' && m2 >= 0 && chars[m1] == 'w' && chars[m2] == 't')
//                return 2;
//            else if (c == 'r' && m3 >= 0 && chars[m1] == 'u' && chars[m2] == 'o' && chars[m3] == 'f')
//                return 4;
//            else if (c == 'x' && m2 >= 0 && chars[m1] == 'i' && chars[m2] == 's')
//                return 6;
//            else if (c == 'n' && m4 >= 0 && chars[m1] == 'e' && chars[m2] == 'v' && chars[m3] == 'e' && chars[m4] == 's')
//                return 7;
//            else if (c == 't' && m4 >= 0 && chars[m1] == 'h' && chars[m2] == 'g' && chars[m3] == 'i' && chars[m4] == 'e')
//                return 8;
//        }
//        return -1;
//    }
//}
