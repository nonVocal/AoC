package day8;

import java.awt.event.HierarchyBoundsAdapter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.LongToIntFunction;

public class RouterPart2
{
    private LongToIntFunction[] directions;
    private final Map<Integer, Long> branches = new HashMap<>();
    private final List<Integer> startingNodes = new ArrayList<>();

    private RouterPart2()
    {

    }

    public long calcStepsToDestination(int start)
    {
        Iterator<LongToIntFunction> iterator = Helper.iterator(directions);

        boolean isStartADest = Helper.isDestination(start);

        int currentLocation = isStartADest ? iterator.next().applyAsInt(branches.get(start)) : start;
        long branch = branches.get(currentLocation);
        int steps = isStartADest ? 1 : 0;

        while (!Helper.isDestination(currentLocation))
        {
            steps++;

            LongToIntFunction next = iterator.next();
            currentLocation = next.applyAsInt(branch);
            branch = branches.get(currentLocation);
        }

        return steps;
    }


    public long calcStepsToDestination()
    {
        int[] locations = startingNodes.stream().mapToInt(i -> i).toArray();
        long[] steps = new long[locations.length];

        for (int i = 0; i < locations.length; i++)
            steps[i] = calcStepsToDestination(locations[i]);

        return lcm(steps);
    }

    static long gcd(long a, long b)
    {
        long _a = a, _b = b;
        while (_a != _b)
        {
            if (_a > _b)
                _a = _a - _b;
            else
                _b = _b - _a;
        }

        return _a;
    }

    static long lcm(long a, long b)
    {
        return Math.abs(a * b) / gcd(a, b);
    }

    static long lcm (long[] arr)
    {
        long lcm = lcm(arr[0], arr[1]);
        for (int i = 2; i < arr.length; i++)
            lcm = lcm(lcm, arr[i]);

        return lcm;
    }


//    public int calcStepsToDestination()
//    {
//        Iterator<LongToIntFunction> iterator = Helper.iterator(directions);
//
//        int[] locations = startingNodes.stream().mapToInt(i -> i).toArray();
//        long[] branchings = Arrays.stream(locations).mapToLong(branches::get).toArray();
//        int len = locations.length;
//
//        int steps = 0;
//
//        while (!onlyDestinations(locations))
//        {
//            steps++;
//
//            LongToIntFunction next = iterator.next();
//
//            for (int i = 0; i < len; i++)
//            {
//                locations[i] = next.applyAsInt(branchings[i]);
//                branchings[i] = branches.get(locations[i]);
//            }
//        }
//
//        return steps;
//    }
//
//    private boolean onlyDestinations(int[] locations)
//    {
//        for (int location : locations)
//        {
//            if (!Helper.isDestination(location))
//                return false;
//        }
//
//        return true;
//    }

    public static Parser parer()
    {
        return new Parser();
    }


    public static class Parser
    {
        private RouterPart2 router = new RouterPart2();
        private Consumer<String> currentParser = this::parseDirections;

        private Parser()
        {

        }

        public void parse(String s)
        {
            currentParser.accept(s);
        }

        public void parseDirections(String s)
        {
            if (s.isBlank())
                return;

            String[] split = s.split("");
            int len = split.length;
            router.directions = new LongToIntFunction[len];

            for (int i = 0; i < len; i++)
            {
                router.directions[i] = switch (split[i])
                {
                    case "R" -> Helper::right;
                    case "L" -> Helper::left;
                    default -> throw new IllegalArgumentException(split[i] + " is no valid direction!");
                };
            }

            currentParser = this::parseBranch;
        }

        public void parseBranch(String s)
        {
            if (s.isBlank())
                return;

            String[] split = s.strip().split("\\s+=\\s+");
            int location = Helper.threeCharStringToInt(split[0].strip());
            long branches = Helper.threeCharStringTupleToLong(split[1].strip());

            if (Helper.isStartPoint(location))
                router.startingNodes.add(location);

            router.branches.put(location, branches);
        }


        public RouterPart2 router()
        {
            RouterPart2 r = router;
            router = new RouterPart2();
            currentParser = this::parseDirections;

            return r;
        }

    }

}
