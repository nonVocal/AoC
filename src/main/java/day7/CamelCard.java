package day7;

public class CamelCard implements Comparable<CamelCard>
{
    private final char card;
    private final int value;

    private final boolean jokerRule;

    public CamelCard(char card, boolean jokerRule)
    {
        this.card = card;
        this.jokerRule = jokerRule;
        this.value = determineValue(card, jokerRule);
    }

    public char getCard()
    {
        return card;
    }

    public int getValue()
    {
        return value;
    }

    private static int determineValue(char card, boolean jokerRule)
    {
        if (Character.isDigit(card))
            return card - '0';
        else
        {
            return switch (card)
            {
                case 'A' -> 14;
                case 'K' -> 13;
                case 'Q' -> 12;
                case 'J' -> jokerRule ? 1 : 11;
                case 'T' -> 10;
                default -> -1;
            };
        }
    }

    @Override
    public int compareTo(CamelCard o)
    {
        return value - o.value;
    }

    @Override
    public String toString()
    {
        return "" + card;
    }
}
