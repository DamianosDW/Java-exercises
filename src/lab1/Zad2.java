package lab1;

public class Zad2
{
    private static int a = 13;
    private static int b = 13;
    private static int c = 2;

    public static void main(String[] args)
    {
        System.out.println("Sum: " + getSum());
    }
    // Metoda zwracajaca sume
    private static int getSum()
    {
        int sum = 0;
        if(a != 13)
        {
            sum += a;
            if(b != 13)
            {
                if(c != 13)
                    sum += b + c;
                else
                    sum += b;
            }
        }
        // Zwrocenie sumy
        return sum;
    }
}
