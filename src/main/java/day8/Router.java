package day8;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.LongToIntFunction;

public class Router
{
    private LongToIntFunction[] directions;
    private final Map<Integer, Long> branches = new HashMap<>();

    private Router()
    {

    }

    public int calcStepsToDestination(int start, int destination)
    {
        Iterator<LongToIntFunction> iterator = Helper.iterator(directions);

        int currentLocation = start;
        long branch = branches.get(start);
        int steps = 0;

        while (currentLocation != destination)
        {
            steps++;

            LongToIntFunction next = iterator.next();
            currentLocation = next.applyAsInt(branch);
            branch = branches.get(currentLocation);
        }

        return steps;
    }

    public static Parser parer()
    {
        return new Parser();
    }


    public static class Parser
    {
        private Router router = new Router();
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

            router.branches.put(location, branches);
        }


        public Router router()
        {
            Router r = router;
            router = new Router();
            currentParser = this::parseDirections;

            return r;
        }

    }

}
