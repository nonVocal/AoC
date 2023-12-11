package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

public class Part1
{
    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 8 - PART 1");

        //        try (var stream = Files.lines(Path.of(args[0])))
        try (var stream =
                     """
                             RL
                                                          
                             AAA = (BBB, CCC)
                             BBB = (DDD, EEE)
                             CCC = (ZZZ, GGG)
                             DDD = (DDD, DDD)
                             EEE = (EEE, EEE)
                             GGG = (GGG, GGG)
                             ZZZ = (ZZZ, ZZZ)
                                   """.lines()
        )
//        try (var stream =
//                     """
//                             LLR
//
//                             AAA = (BBB, BBB)
//                             BBB = (AAA, ZZZ)
//                             ZZZ = (ZZZ, ZZZ)
//                                   """.lines()
//        )
        {
            Router.Parser p = Router.parer();
            stream.forEach(p::parse);

            Router router = p.router();

            long result = router.calcStepsToDestination(Helper.threeCharStringToInt("AAA"), Helper.threeCharStringToInt("ZZZ"));

            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }


}
