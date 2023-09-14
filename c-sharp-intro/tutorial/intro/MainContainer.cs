namespace intro;

using System;

class MainContainer
{
    static void Main()
    {
        Console.WriteLine("Hello world.");

        PointFactory generator = new(12);

        foreach (var point in generator.CreatePoints())
        {
            Console.WriteLine(point);
        }
        
        Divide(10, 3, out var quo, out int rem);
        Console.WriteLine(quo + " rem: " + rem);

        Point pp = new Point(12, 22);
        
    }

    static void Divide(int x, int y, out int quotient, out int remainder)
    {
        quotient = x / y;
        remainder = x % y;
    }
}

public class Point
{
    public int X { get; set; }
    public int Y { get; }

    public Point(int x, int y) => (X, Y) = (x, y);

    public override string ToString()
    {
        return "x:" + X + " y:" + Y;
    }
}

public struct PointS
{
    private Point _p1;
    private Point _p2;

    public PointS(Point p1, Point p2) => (this._p1, this._p2) = (p1, p2);
}

public class PointFactory
{
    private readonly int _count;

    public PointFactory(int count) => this._count = count;

    public IEnumerable<Point> CreatePoints()
    {
        var generator = new Random();
        for (int i = 0; i < _count; i++)
        {
            yield return new Point(generator.Next(), generator.Next());
        }
    }
}