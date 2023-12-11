package day8;

import java.util.Iterator;

public class Helper
{
    public static int threeCharStringToInt(String s)
    {
        return ((int) s.charAt(0)) << 16 | ((int) s.charAt(1)) << 8 | (int) s.charAt(2);
    }

    public static long threeCharStringTupleToLong(String s)
    {
        String[] split = s.substring(1, s.length() - 1).split(",\\s+");

        return ((long) threeCharStringToInt(split[0])) << 32 | threeCharStringToInt(split[1]);
    }

    public static int left(long branch)
    {
        return (int) (branch >>> 32);
    }

    public static int right(long branch)
    {
        return (int) branch;
    }

    public static boolean isStartPoint(int location)
    {
        return (location << 24) >> 24 == 'A';
    }

    public static boolean isDestination(int location)
    {
        return (location << 24) >> 24 == 'Z';
    }

    public static <T> Iterator<T> iterator(T[] array)
    {
        return new Iterator<>()
        {
            int pos = 0;

            @Override
            public boolean hasNext()
            {
                return true;
            }

            @Override
            public T next()
            {
                T r = array[pos];
                pos = ++pos % array.length;

                return r;
            }
        };
    }
}
