package day7;

import java.util.Arrays;

public class CamelCardHandJokerRule implements Comparable<CamelCardHandJokerRule>
{
    private final Kind kind;
    private final CamelCard[] cards;

    public CamelCardHandJokerRule(CamelCard[] cards)
    {
        this.cards = cards;
        kind = determineKind(cards);
    }

    @Override
    public int compareTo(CamelCardHandJokerRule o)
    {
        int v = kind.ordinal() - o.kind.ordinal();
        if (v == 0)
        {
            for (int i = 0; i < 5; i++)
            {
                v = cards[i].compareTo(o.cards[i]);
                if (v != 0)
                    break;
            }
        }

        return v;
    }

    @Override
    public String toString()
    {
        return "CamelCardHandJokerRule{" +
                "kind=" + kind +
                ", cards=" + Arrays.toString(cards) +
                '}';
    }

    private static Kind determineKind(CamelCard[] cards)
    {
        int[] tmp = new int[15];

        for (CamelCard card : cards)
            tmp[card.getValue()]++;

        boolean jokerPresent = tmp[1] != 0;
        if (tmp[1] == 5 || tmp[1] == 4)
            return Kind.FIVE_OF_A_KIND;

        for (int i = 2; i < tmp.length; i++)
        {
            if (i == 11)
                continue;

            if (tmp[i] == 5)
                return Kind.FIVE_OF_A_KIND;
            else if (tmp[i] == 4)
            {
                if (jokerPresent)
                    return Kind.FIVE_OF_A_KIND;
                else
                    return Kind.FOUR_OF_A_KIND;
            }
            else if (tmp[i] == 3)
            {
                if (jokerPresent)
                {
                    if (tmp[1] == 2)
                        return Kind.FIVE_OF_A_KIND;
                    else
                        return Kind.FOUR_OF_A_KIND;
                }

                for (int j = i + 1; j < tmp.length; ++j)
                {
                    if (tmp[j] == 2)
                        return Kind.FULL_HOUSE;
                }
                return Kind.THREE_OF_A_KIND;
            }
            else if (tmp[i] == 2)
            {
                if (jokerPresent)
                {
                    if (tmp[1] == 3)
                        return Kind.FIVE_OF_A_KIND;
                    else if (tmp[1] == 2)
                        return Kind.FOUR_OF_A_KIND;
                    else
                    {
                        for (int j = i + 1; j < tmp.length; ++j)
                        {
                            if (tmp[j] == 2)
                                return Kind.FULL_HOUSE;
                        }

                        return Kind.THREE_OF_A_KIND;
                    }
                }

                for (int j = i + 1; j < tmp.length; ++j)
                {
                    if (tmp[j] == 3)
                        return Kind.FULL_HOUSE;
                    else if (tmp[j] == 2)
                        return Kind.TWO_PAIR;
                }
                return Kind.ONE_PAIR;
            }
        }


        if (jokerPresent)
        {
            return switch (tmp[1])
            {
                case 3 -> Kind.FOUR_OF_A_KIND;
                case 2 -> Kind.THREE_OF_A_KIND;
                case 1 -> Kind.ONE_PAIR;
                default -> Kind.HIGH_CARD;
            };
        }
        else
            return Kind.HIGH_CARD;

    }

    enum Kind
    {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND
    }
}
