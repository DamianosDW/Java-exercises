package lab2;

import java.io.*;

public class Main
{
    private static File mainFile = new File("emails.txt");
    private static int mailCounter = 0;
    public static void main(String[] args)
    {

    }

    private static boolean checkifPersonIsInFile(Person person)
    {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(mainFile));
            // Sprawdzenie czy imie i nazwisko jest w pliku
            String textLine;
            while((textLine = bufferedReader.readLine()) != null)
            {
                // Usuniecie @domena z tekstu
                textLine = textLine.substring(0, textLine.indexOf("@"));
                // Sprawdzenie czy podana osoba jest juz w pliku
                if(textLine.equals((person.name + person.surname).toLowerCase()))
                    mailCounter++;
            }
            // Zwrocenie true, gdy osoba zostala znaleziona w pliku
            if(mailCounter > 0)
                return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Zamkniecie pliku
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    private static void saveDataToFile(Person person)
    {
        // Zapisanie nowego e-maila do pliku
        BufferedWriter bufferedWriter = null;
        String mail;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(mainFile, true));

            if(checkifPersonIsInFile(person))
            {
                // Wygenerowanie e-maila z numerem
                mail = (person.name.trim() + person.surname.trim()).toLowerCase() + mailCounter + "@inferno-ts3.pl";
            }
            else
            {
                // Wygenerowanie e-maila bez numeru
                mail = (person.name.trim() + person.surname.trim()).toLowerCase() + "@inferno-ts3.pl";
                // Wyzerowanie zmiennej pomocniczej
                mailCounter = 0;
            }
            // Zapisanie e-maila do pliku
            bufferedWriter.write(mail);
            bufferedWriter.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            // Zamkniecie pliku
            if(bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
