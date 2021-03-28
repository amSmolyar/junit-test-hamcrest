package ru.netology;

public class TreeSelectedSystem {
    public static void main(String[] args) {
        UserScanner scanner = new UserScanner();
        CandidateStore candidates = new CandidateStore();

        addCandidate(candidates);
        candidates.writeCandidates();

        System.out.println("Привет!");
        while (true) {
            if (!scanner.requestUser(candidates)) {
                candidates.writeCandidates();
                break;
            }
            //candidates.writeCandidates();
        }
        scanner.closeScanner();
    }

    public static void addCandidate(CandidateStore candidates) {
        candidates.addCandidate(
                new Candidate("Иванов Иван Иванович", "муж", "28", 4, 5));
        candidates.addCandidate(
                new Candidate("Алексеев Алексей Алексеевич", "муж", "35", 2, 4));
        candidates.addCandidate(
                new Candidate("Пышечкина Наталья Ивановна", "жен", "23", 3, 5));
        candidates.addCandidate(
                new Candidate("Соколов Павел Викторович", "муж", "38", 5, 5));
        candidates.addCandidate(
                new Candidate("Измайлова Мария Леонидовна", "жен", "40", 4, 4));
        candidates.addCandidate(
                new Candidate("Колокольцев Николай Валентинович", "муж", "32", 1, 4));
        candidates.addCandidate(
                new Candidate("Мезенцев Сергей Павлович", "муж", "22", 2, 5));
        candidates.addCandidate(
                new Candidate("Савина Ольга Валерьевна", "жен", "39", 3, 5));
        candidates.addCandidate(
                new Candidate("Жук Евгений Михайлович", "муж", "30", 4, 5));
        candidates.addCandidate(
                new Candidate("Решетов Михаил Владимирович", "муж", "40", 4, 4));
        candidates.addCandidate(
                new Candidate("Макарова Светлана Константиновна", "жен", "19", 3, 2));
        candidates.addCandidate(
                new Candidate("Ветров Олег Евгеньевич", "муж", "27", 1, 4));
        candidates.addCandidate(
                new Candidate("Лопухов Иннокентий Валерьянович", "муж", "25", 3, 4));
    }
}

