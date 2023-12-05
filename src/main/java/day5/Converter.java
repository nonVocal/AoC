package day5;

public record Converter(long destinationStart, Range source)
{
    public long convert(long i)
    {
        if (source.inRange(i))
        {
            long offset = i - source.start();
            return destinationStart + offset;
        }
        else
            return i;
    }
}
