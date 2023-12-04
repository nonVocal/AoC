package day4;

import java.util.Set;

public record Card(int id, Set<Integer> winningNumbers, Set<Integer> myNumbers)
{
}
