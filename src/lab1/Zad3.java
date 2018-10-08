package lab1;

public class Zad3
{
    public static void main(String[] args)
    {
        int[] array = {1, 2, 3, 3, 1, 1, 3};
        int lastValue = 1;
        boolean sequenceFound = false;

        for(int element : array)
        {
            if(element == lastValue + 1)
            {
                // Zapisanie ostatniej wartosci do zmiennej
                lastValue = element;
                if(lastValue == 3)
                    sequenceFound = true;
            }
            else
            {
                // Przywrocenie domyslnej wartosci zmiennej
                lastValue = 1;
            }
        }
        // Wypisanie wyniku dzialania programu
        System.out.println(sequenceFound);
    }
}
