package day5;

public record Range(long start, long length)
{
    boolean inRange(long i)
    {
        return i >= start && i < (start + length);
    }
}
