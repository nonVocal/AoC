package day2;

public class GameParser
{
    static Game parse(String g)
    {
        String substring = g.substring("Game ".length());
        String[] split = substring.split(":");

        int gameId = Integer.parseInt(split[0]);
        Game game = new Game(gameId);

        String[] draws = split[1].split(";");
        for (String draw : draws)
        {
            String[] cubes = draw.split(",");
            for (String cube : cubes)
            {
                String[] amountAndColor = cube.strip().split(" ");
                int amount = Integer.parseInt(amountAndColor[0]);

                switch (amountAndColor[1])
                {
                    case "blue" -> game.adjustBlueCubes(amount);
                    case "red" -> game.adjustRedCubes(amount);
                    case "green" -> game.adjustGreebCubes(amount);
                }
            }
        }


        return game;
    }
}
