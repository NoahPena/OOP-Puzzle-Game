package test;

/**
 * Created by noah-pena on 4/28/16.
 */
public enum Direction
{
    NORTH("North"),
    SOUTH("South"),
    EAST("East"),
    WEST("West");


    private String name;

    @Override
    public String toString()
    {
        return name;
    }

    Direction(String name)
    {
        this.name = name;
    }
}
