package day2;

public class Game
{
    private final int id;
    private int redCubes;
    private int blueCubes;
    private int greenCubes;

    public Game(int id)
    {
        this.id = id;
        redCubes = 0;
        blueCubes = 0;
        greenCubes = 0;
    }

    public void adjustRedCubes(int cubes)
    {
        redCubes = Math.max(redCubes, cubes);
    }

    public void adjustBlueCubes(int cubes)
    {
        blueCubes = Math.max(blueCubes, cubes);
    }

    public void adjustGreebCubes(int cubes)
    {
        greenCubes = Math.max(greenCubes, cubes);
    }

    public int getBlueCubes()
    {
        return blueCubes;
    }

    public int getRedCubes()
    {
        return redCubes;
    }

    public int getGreenCubes()
    {
        return greenCubes;
    }

    public int getId()
    {
        return id;
    }
}
