using System;
using System.Collections;
using System.Linq;

namespace LinqFaroShuffle;

public static class Extensions
{
    public static IEnumerable<T> InterleaveSeqenceWith<T>(this IEnumerable<T> first, IEnumerable<T> second)
    {
        var firstIter = first.GetEnumerator();
        var secondIter = second.GetEnumerator();

        while (firstIter.MoveNext() && secondIter.MoveNext())
        {
            yield return firstIter.Current;
            yield return secondIter.Current;
        }
    }

    public static bool SequenceEquals<T>(this IEnumerable<T> first, IEnumerable<T> second)
    {
        var firstIter = first.GetEnumerator();
        var secondIter = second.GetEnumerator();

        while ((firstIter?.MoveNext() == true) && secondIter.MoveNext())
        {
            if ((firstIter.Current != null) && !firstIter.Current.Equals(secondIter.Current))
            {
                return false;
            }
        }

        return true;
    }
}