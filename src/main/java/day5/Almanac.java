package day5;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public record Almanac(long[] seeds, CombinedConverter seedToSoil, CombinedConverter soilToFertilizer,
                      CombinedConverter fertilizerToWater, CombinedConverter waterToLight, CombinedConverter lightToTemp,
                      CombinedConverter tempToHumidity, CombinedConverter humidityToLocation)
{

    Almanac(long[] seeds, List<Converter> seedToSoil, List<Converter> soilToFertilizer,
            List<Converter> fertilizerToWater, List<Converter> waterToLight, List<Converter> lightToTemp,
            List<Converter> tempToHumidity, List<Converter> humidityToLocation)
    {
        this(seeds, new CombinedConverter(seedToSoil), new CombinedConverter(soilToFertilizer),
                new CombinedConverter(fertilizerToWater), new CombinedConverter(waterToLight), new CombinedConverter(lightToTemp),
                new CombinedConverter(tempToHumidity), new CombinedConverter(humidityToLocation));
    }


    public long getLocationForSeed(long seed)
    {
        return map(map(map(map(map(map(map(seed, seedToSoil),soilToFertilizer), fertilizerToWater),waterToLight), lightToTemp), tempToHumidity), humidityToLocation);
    }

    private long map(long input, CombinedConverter converters)
    {
        return converters.convert(input);
    }

}
