// See https://aka.ms/new-console-template for more information

namespace Teleprompter;

using static Math;

internal class Program
{
    static async Task Main(string[] args)
    {
        await RunTeleprompter();
    }

    private static async Task RunTeleprompter()
    {
        var config = new TeleprompterConfig();
        var displayTask = ShowTeleprompter(config);
        var speedTask = GetInput(config);
        Console.WriteLine("join two task.");
        await Task.WhenAny(displayTask, speedTask);
    }

    static IEnumerable<string> ReadFrom(string file)
    {
        string? line;
        try
        {
            using var reader2 = File.OpenText(file);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
        using var reader = File.OpenText(file);
        Console.WriteLine("start read file.");
        while ((line = reader.ReadLine()) != null)
        {
            var words = line.Split(' ');
            var lineLength = 0;
            foreach (var word in words)
            {
                yield return word + " ";
                lineLength += word.Length + 1;
                if (lineLength > 70)
                {
                    yield return Environment.NewLine;
                    lineLength = 0;
                }
            }

            yield return Environment.NewLine;
        }
    }

    private static async Task ShowTeleprompter(TeleprompterConfig config)
    {
        Console.WriteLine("start ShowTeleprompter.");
        var words = ReadFrom("C:\\Users\\adj\\project\\Practice-collection\\c-sharp-intro\\tutorial\\Teleprompter\\sampleQuotes.txt");
        Console.WriteLine("words read ready.");
        foreach (var word in words)
        {
            Console.WriteLine(word);
            if (!string.IsNullOrWhiteSpace(word))
            {
                await Task.Delay(config.DelayInMilliseconds);
            }
        }
    }

    private static async Task GetInput(TeleprompterConfig config)
    {
        Action work = () =>
        {
            Console.WriteLine("start read user input.");
            do
            {
                var key = Console.ReadKey(true);
                if (key.KeyChar == '>')
                {
                    config.UpdateDelay(-10);
                }
                else if (key.KeyChar == '<')
                {
                    config.UpdateDelay(10);
                }
                else if (key.KeyChar == 'X' || key.KeyChar == 'x')
                {
                    config.SetDone();
                }
            } while (!config.Done);
            Console.WriteLine("Stop read user input.");
        };
        await Task.Run(work);
    }
}

internal class TeleprompterConfig
{
    public int DelayInMilliseconds { get; private set; } = 200;

    public void UpdateDelay(int increment)
    {
        var newDelay = Min(DelayInMilliseconds + increment, 1000);
        newDelay = Max(newDelay, 20);
    }

    public bool Done { get; private set; }

    public void SetDone()
    {
        Done = true;
    }
}