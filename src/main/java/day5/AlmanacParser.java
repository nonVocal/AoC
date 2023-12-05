package day5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AlmanacParser
{
    public static Almanac parse(List<String> raw)
    {
        long[] seeds = null;
        List<Converter> seedToSoil = new ArrayList<>();
        List<Converter> soilToFertilizer = new ArrayList<>();
        List<Converter> fertilizerToWater = new ArrayList<>();
        List<Converter> waterToLight = new ArrayList<>();
        List<Converter> lightToTemp = new ArrayList<>();
        List<Converter> tempToHumidity = new ArrayList<>();
        List<Converter> humidityToLocation = new ArrayList<>();

        ListIterator<String> it = raw.listIterator();
        while (it.hasNext())
        {
            String s = it.next();

            String[] split = s.split(":");
            switch (split[0].strip())
            {
                case "seeds" ->
                {
                    String[] seedLiterals = split[1].strip().split("\\s+");
                    seeds = new long[seedLiterals.length];

                    for (int i = 0; i < seedLiterals.length; i++)
                        seeds[i] = Long.parseLong(seedLiterals[i]);
                }
                case "seed-to-soil map" -> parse(it, seedToSoil);
                case "soil-to-fertilizer map" -> parse(it, soilToFertilizer);
                case "fertilizer-to-water map" -> parse(it, fertilizerToWater);
                case "water-to-light map" -> parse(it, waterToLight);
                case "light-to-temperature map" -> parse(it, lightToTemp);
                case "temperature-to-humidity map" -> parse(it, tempToHumidity);
                case "humidity-to-location map" -> parse(it, humidityToLocation);
                case "" -> {

                }
                default -> throw new IllegalArgumentException(split[0]);
            }
        }

        return new Almanac(seeds == null ? new long[0] : seeds, seedToSoil, soilToFertilizer, fertilizerToWater,
                waterToLight, lightToTemp, tempToHumidity, humidityToLocation);
    }

    static void parse(Iterator<String> it, List<Converter> targetList)
    {
        String s;
        while (it.hasNext() && !(s = it.next()).isBlank())
        {
            Converter converter = parseConverter(s);
            targetList.add(converter);
        }
    }

    static Converter parseConverter(String rawConv)
    {
        String[] split = rawConv.strip().split("\\s+");
        long destinationStart = Long.parseLong(split[0]);

        long sourceStart = Long.parseLong(split[1]);
        long rangeLength = Long.parseLong(split[2]);

        return new Converter(destinationStart, new Range(sourceStart, rangeLength));
    }
}
