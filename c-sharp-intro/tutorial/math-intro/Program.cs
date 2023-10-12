// See https://aka.ms/new-console-template for more information

namespace math_intro
{
    class Starter
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hell");

            Person p1 = new Person(32, "John Smith");

            Person? p2 = null;
            
            Console.WriteLine(p1);
            Console.WriteLine(p2 == null);

            Whatever whatever = new();
            var (age, name) = whatever;
            Console.WriteLine(age);
            Console.WriteLine(name);
        }
    }

    class Person
    {
        private readonly int _age;
        private readonly string _name;

        public Person(int age, string name)
        {
            _age = age;
            this._name = name;
        }

        public override string ToString()
        {
            return _name + " : " + _age;
        }
    }

    interface II1
    {
        void Speak();
    }

    interface II2
    {
        int Speak();
    }

    class Whatever : II1, II2
    {
        public void Speak()
        {
            throw new NotImplementedException();
        }

        int II2.Speak()
        {
            throw new NotImplementedException();
        }

        public void Deconstruct(out int age, out string name)
        {
            age = 12;
            name = "John";
        }

    }

}