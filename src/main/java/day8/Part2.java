package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Part2
{
    public static void main(String[] args) throws IOException
    {
        Instant start = Instant.now();
        System.out.println("DAY 8 - PART 2");

        try (var stream = Files.lines(Path.of(args[0])))
//        try (var stream =
//                     """
//                             LR
//
//                             11A = (11B, XXX)
//                             11B = (XXX, 11Z)
//                             11Z = (11B, XXX)
//                             22A = (22B, XXX)
//                             22B = (22C, 22C)
//                             22C = (22Z, 22Z)
//                             22Z = (22B, 22B)
//                             XXX = (XXX, XXX)
//                             """.lines()
//        )
        {
            RouterPart2.Parser p = RouterPart2.parer();
            stream.forEach(p::parse);

            RouterPart2 router = p.router();

            long result = router.calcStepsToDestination();

            System.out.println(result);
        }
        System.out.println(Duration.between(start, Instant.now()).toMillis() + " ms");
    }


}