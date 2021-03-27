package ru.netology;

import java.util.Scanner;

public class UserScanner {
    private Scanner scan;

    private Candidate newCandidate;

    public UserScanner() {
        scan = new Scanner(System.in);
    }

    public void closeScanner() {
        scan.close();
    }

    public boolean requestUser(CandidateStore allCandidates) {
        System.out.println("\nВведите информацию о кандидате " +
                "(Фамилия Имя Отчество, пол, возраст, релевантность резюме, оценка на собеседовании)." +
                "\nДля завершения введите пустую строку:");

        String scanData = scan.nextLine().trim();
        if (scanData.equals(""))
            return false;

        try {
            newCandidate = detectCandidate(scanData);
            allCandidates.addCandidate(newCandidate);
        } catch (DataFormatException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public Candidate detectCandidate(String scanData) throws DataFormatException {
        String[] arrayIn = scanData.split("[,:;.]");
        String name;
        String sex;
        String age;
        int relevance;
        int rating;

        if (arrayIn.length == 5) {
            name = arrayIn[0].trim();
            sex = arrayIn[1].trim();
            age = arrayIn[2].trim();
            try {
                relevance = Integer.parseInt(arrayIn[3].trim());
                rating = Integer.parseInt(arrayIn[4].trim());
            } catch (NumberFormatException e) {
                throw new DataFormatException("Релевантность и оценка должны быть целыми числами");
            }
        } else {
            throw new DataFormatException("Неправильный формат введенных данных");
        }

        return new Candidate(name, sex, age, relevance, rating);
    }
}
