import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("Şifre Uzunluğunu Belirtin: ");
            int length = scanner.nextInt();

            System.out.println("Büyük harf kullanılsın mı? (E/H)");
            boolean includeUpperCase = scanner.next().equalsIgnoreCase("E");

            System.out.println("Küçük harf kullanılsın mı? (E/H)");
            boolean includeLowerCase = scanner.next().equalsIgnoreCase("E");

            System.out.println("Rakam kullanılsın mı? (E/H)");
            boolean includeNumber = scanner.next().equalsIgnoreCase("E");

            System.out.println("Özel karakter kullanılsın mı? (E/H)");
            boolean includeSpecialChars = scanner.next().equalsIgnoreCase("E");

            String password = generateValidPassword(length,includeUpperCase,includeLowerCase,includeNumber,includeSpecialChars);

            System.out.println("Oluşturulan Şifre: " + password);

            System.out.println("Şifrenizi kaydetmek ister misiniz? (E/H)");
            boolean savePassword = scanner.next().equalsIgnoreCase("E");

            if (savePassword){
                savePasswordToFile(password);
            }

            System.out.println("Çıkış yapmak için Q tuşuna basın, şifrenizi tekrar oluşturmak için herhangi bir tuşa basın.");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Q")){
                break;
            }


        }
    }

    private static void savePasswordToFile(String password) {
        try {
            FileWriter fileWriter = new FileWriter("password.txt",true);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            fileWriter.write("Şifre: " + password + " Tarih/Saat: " + now.format(dateTimeFormatter) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generatePassword(int length, boolean includeUpperCase, boolean includeLowerCase,
                                           boolean includeNumber, boolean includeSpecialChars) {

        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$?";

        String password ="";
        String chars = "";

        if (includeLowerCase){
            chars += lowerCaseChars;
        }
        if (includeNumber){
            chars += numberChars;
        }
        if (includeSpecialChars){
            chars += specialChars;
        }
        if (includeUpperCase) {
            chars += upperCaseChars;
        }
        Random random = new Random();
        for (int i = 0; i<length ; i++){
           int randomIndex =random.nextInt(chars.length());
           password += chars.charAt(randomIndex);
        }
        return password;
    }

    private static String generateValidPassword(int length, boolean includeUpperCase, boolean includeLowerCase,
                                                boolean includeNumber, boolean includeSpecialChars){

        String password;
        do {
        password = generatePassword(length, includeUpperCase, includeLowerCase,includeNumber,includeSpecialChars);
        }while (!isValidPassword(password,length, includeUpperCase, includeLowerCase,includeNumber,includeSpecialChars));

        return password;
    }

    private static boolean isValidPassword(String password, int length, boolean includeUpperCase, boolean includeLowerCase,
                                           boolean includeNumber, boolean includeSpecialChars){

        if (length != password.length()){
            return false;
        }

        if (includeUpperCase && !containsUpperCase(password)){
            return false;
        }

        if (includeLowerCase && !containsLowerCase(password)){
            return false;
        }

        if (includeNumber && !containsNumber(password)){
            return false;
        }

        if (includeSpecialChars && !containsSpecialChar(password)){
            return false;
        }

        return true ;
    }

    private static boolean containsSpecialChar(String password) {
        String specialChars = "!@#$?";
        for (char c: password.toCharArray()){
            if (specialChars.indexOf(c) != -1){
                return true;
            }
        }
        return false;
    }

    private static boolean containsNumber(String password) {
        for (char c : password.toCharArray()){
            if (Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }

    private static boolean containsLowerCase(String password) {
        for (char c : password.toCharArray()){
            if (Character.isLowerCase(c)){
                return true;
            }
        }
        return false;
    }

    private static boolean containsUpperCase(String password) {
        for (char c : password.toCharArray()){
            if (Character.isUpperCase(c)){
                return true;
            }
        }
        return false;
    }

}
