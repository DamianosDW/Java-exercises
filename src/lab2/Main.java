package lab2;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main
{
    private static String filePath = "emails.txt";
    private static int mailCounter = 0;
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String name;
        String surname;
        while(true)
        {
            // Podanie imienia z klawiatury
            System.out.print("Enter name (type q to stop program): ");
            name = scanner.next();
            // Wyswietlenie zawartosci pliku i zakonczenie pracy programu, gdy zostanie wpisane: q
            if(name.equals("q"))
            {
                // Wyswietlenie zawartosci pliku, gdy plik istnieje
                if(Files.exists(Paths.get(filePath)))
                    showFileContents();
                // Zakonczenie pracy programu
                System.exit(0);
            }
            // Podanie nazwiska z klawiatury
            System.out.print("Enter surname (type q to stop program): ");
            surname = scanner.next();
            // Zakonczenie pracy programu, gdy zostanie wpisane: q
            if(surname.equals("q"))
            {
                // Wyswietlenie zawartosci pliku, gdy plik istnieje
                if(Files.exists(Paths.get(filePath)))
                    showFileContents();
                // Zakonczenie pracy programu
                System.exit(0);
            }
            // Wygenerowanie e-maila i zapisanie go do pliku
            saveDataToFile(new Person(name, surname));
        }
    }
    private static boolean checkifPersonIsInFile(Person person)
    {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            // Sprawdzenie czy imie i nazwisko jest w pliku
            String textLine;
            while((textLine = bufferedReader.readLine()) != null)
            {
                // Usuniecie @domena z tekstu
                textLine = textLine.substring(0, textLine.indexOf("@"));
                // Sprawdzenie czy podana osoba jest juz w pliku
                if(textLine.contains((person.name + person.surname).toLowerCase()))
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
            bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));

            if(checkifPersonIsInFile(person))
            {
                // Wygenerowanie e-maila z numerem
                mail = (person.name.trim() + person.surname.trim()).toLowerCase() + mailCounter + "@inferno-ts3.pl";
                // Wyzerowanie zmiennej pomocniczej
                mailCounter = 0;
            }
            else
            {
                // Wygenerowanie e-maila bez numeru
                mail = (person.name.trim() + person.surname.trim()).toLowerCase() + "@inferno-ts3.pl";
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
    private static void showFileContents()
    {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            // Wyswietlenie zawartosci pliku
            System.out.println("Saved emails: ");
            String textLine;
            while((textLine = bufferedReader.readLine()) != null)
            {
                // Wypisanie linii tekstu
                System.out.println(textLine);
            }
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
    }
}
