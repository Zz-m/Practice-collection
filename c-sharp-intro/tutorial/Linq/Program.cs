// See https://aka.ms/new-console-template for more information

using System;
using System.Collections.Generic;
using System.Linq;
using LinqFaroShuffle;

namespace Linq;
class MainClass
{
    
    static IEnumerable<string> Suits()
    {
        yield return "clubs";
        yield return "diamonds";
        yield return "hearts";
        yield return "spades";
    }

    static IEnumerable<string> Ranks()
    {
        yield return "two";
        yield return "three";
        yield return "four";
        yield return "five";
        yield return "six";
        yield return "seven";
        yield return "eight";
        yield return "nine";
        yield return "ten";
        yield return "jack";
        yield return "queen";
        yield return "king";
        yield return "ace";
    }
    
    static void Main(string[] args)
    {
        Console.WriteLine("Main start.");
        var startingDeck =
            from s in Suits()
            from r in Ranks()
            select new { Suits = s, Rank = r };

        foreach (var card in startingDeck)
        {
            Console.WriteLine(card);
        }

        var times = 0;
        var shuffle = startingDeck;
        do
        {
            shuffle = shuffle.Take(26).InterleaveSeqenceWith(shuffle.Skip(26));
            times++;
        } while (!startingDeck.SequenceEquals(shuffle));
        
        Console.WriteLine($"Total time:{times}");

    }
}





