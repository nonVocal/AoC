package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class Part2
{
    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 5 - PART 2");

        try (var stream = Files.lines(Path.of(args[0])))
//        try (var stream =
//                     """
//                             seeds: 79 14 55 13
//
//                             seed-to-soil map:
//                             50 98 2
//                             52 50 48
//
//                             soil-to-fertilizer map:
//                             0 15 37
//                             37 52 2
//                             39 0 15
//
//                             fertilizer-to-water map:
//                             49 53 8
//                             0 11 42
//                             42 0 7
//                             57 7 4
//
//                             water-to-light map:
//                             88 18 7
//                             18 25 70
//
//                             light-to-temperature map:
//                             45 77 23
//                             81 45 19
//                             68 64 13
//
//                             temperature-to-humidity map:
//                             0 69 1
//                             1 0 69
//
//                             humidity-to-location map:
//                             60 56 37
//                             56 93 4
//                                                          """.lines()
//        )
        {
            List<String> rawAlmanac = stream.toList();
            Almanac almanac = AlmanacParser.parse(rawAlmanac);

            long[] seedLine = almanac.seeds();

            long closestLocation = Long.MAX_VALUE;

            for (int i = 0; i < seedLine.length; i += 2)
            {
                System.out.println("seedline: " + i);
                    for (long offset = 0; offset < seedLine[i + 1]; offset++)
                    {
                        long locationForSeed = almanac.getLocationForSeed(seedLine[i] + offset);
                        closestLocation = Long.min(closestLocation, locationForSeed);
                    }
            }

            long result = closestLocation;

            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }

}
