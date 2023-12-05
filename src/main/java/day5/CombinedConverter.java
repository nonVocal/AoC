package day5;

import java.util.List;

public class CombinedConverter
{
    private final List<Converter> converterList;
    private final Range combinedRange;

    public CombinedConverter(List<Converter> converters)
    {
        this.converterList = converters;

        long minStart = Long.MAX_VALUE, maxEnd = 0;

        for (Converter converter : converterList)
        {
            long start = converter.source().start();
            long end = start + converter.source().length();

            minStart = Long.min(minStart, start);
            maxEnd = Long.max(maxEnd, end);
        }

        combinedRange = new Range(minStart, maxEnd - minStart);

    }

    public long convert(long i)
    {
        if (combinedRange.inRange(i))
        {
            for (Converter converter : converterList)
            {
                if (converter.source().inRange(i))
                    return converter.convert(i);
            }

            return i;
        }
        return i;
    }

}
