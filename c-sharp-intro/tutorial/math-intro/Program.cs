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
            
            Console.WriteLine(p1.NickName);
            p1.NickName = "asd";
            Console.WriteLine(p1.NickName);
        }
    }

    class Person
    {
        private readonly int _age;
        private readonly string _name;

        public string NickName { get; set; }

        public Person(int age, string name)
        {
            _age = age;
            this._name = name;
            NickName = "default";
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
        void II1.Speak()
        {
            throw new NotImplementedException();
        }

        public int Speak()
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