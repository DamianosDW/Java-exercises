package lab1;

public class Zad1
{
    public static void main(String[] args)
    {
        int a = 14;
        int b = 13;

        if((a >= 13 && a <= 19) || (b >= 13 && b <= 19))
        {
            if(!((a >= 13 && a <= 19) && (b >= 13 && b <= 19)))
                System.out.println("teen");
        }
    }
}
