package ru.netology;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class UserScannerTest {
    public static UserScanner userScanner = new UserScanner();
    public static CandidateStore candidateStore = new CandidateStore();
    public static Set<Candidate> expectedStore = new TreeSet<>(new CandidateComparator());

    // 1. Проверка методов addCandidate и CandidateStore, и класса CandidateComparator:
    @BeforeAll
    public static void test_addCandidate() {
        System.out.println("test_addCandidate");
        // expected:
        expectedStore.add(new Candidate("Иванов Иван Иванович", "муж", "28", 4, 5));
        expectedStore.add(new Candidate("Алексеев Алексей Алексеевич", "муж", "35", 2, 4));
        expectedStore.add(new Candidate("Пышечкина Наталья Ивановна", "жен", "23", 3, 5));
        expectedStore.add(new Candidate("Соколов Павел Викторович", "муж", "38", 5, 5));
        expectedStore.add(new Candidate("Измайлова Мария Леонидовна", "жен", "40", 4, 4));
        expectedStore.add(new Candidate("Колокольцев Николай Валентинович", "муж", "32", 1, 4));
        expectedStore.add(new Candidate("Мезенцев Сергей Павлович", "муж", "22", 2, 5));
        expectedStore.add(new Candidate("Савина Ольга Валерьевна", "жен", "39", 3, 5));
        expectedStore.add(new Candidate("Жук Евгений Михайлович", "муж", "30", 4, 5));
        expectedStore.add(new Candidate("Решетов Михаил Владимирович", "муж", "40", 4, 4));
        expectedStore.add(new Candidate("Макарова Светлана Константиновна", "жен", "19", 3, 2));
        expectedStore.add(new Candidate("Ветров Олег Евгеньевич", "муж", "27", 1, 4));
        expectedStore.add(new Candidate("Лопухов Иннокентий Валерьянович", "муж", "25", 3, 4));

        // actual:
        TreeSelectedSystem.addCandidate(candidateStore);

        assertThat(candidateStore.getCandidateSet(), hasSize(13));
        assertThat(expectedStore.size(), equalTo(candidateStore.getCandidateSet().size()));

        Iterator<Candidate> expectedIt = expectedStore.iterator();
        Iterator<Candidate> actualIt = candidateStore.getCandidateSet().iterator();
        while (expectedIt.hasNext()) {
            Candidate expectedCandidate = expectedIt.next();
            Candidate actualCandidate = actualIt.next();
            assertThat(actualCandidate.toString(), equalTo(expectedCandidate.toString()));
        }
    }

    // 2. Проверка, что метод detectCandidate не выбросит исключения при корректных
    // входных данных:
    @ParameterizedTest
    @ValueSource(strings = {"Бобровский Вячеслав Николаевич, муж, 32, 4, 3",
            "Колесникова Екатерина Викторовна;жен;28,4,5",
            "Половцев Иван Петрович: муж. 23, 2, 4"})
    public void detectCandidate_rightData(String dataIn) {
        System.out.println("detectCandidate_rightData");
        boolean actual = false;
        try {
            Candidate newCandidate = userScanner.detectCandidate(dataIn);
            candidateStore.addCandidate(newCandidate);
            actual = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertThat(true, equalTo(actual));
    }

    // 3. Проверка, что метод detectCandidate выбросит исключение, если входные данные не
    // будут введены в корректной форме:
    @ParameterizedTest
    @ValueSource(strings = {"Бобровский, Вячеслав Николаевич, муж, 32, 4, 3",
            "Колесникова Екатерина Викторовна;жен;28,четыре,5",
            "Половцев Иван Петрович: муж., 23, 2, 4"})
    public void detectCandidate_wrongData(String dataIn) {
        System.out.println("detectCandidate_wrongData");
        try {
            userScanner.detectCandidate(dataIn);
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(DataFormatException.class));
        }
    }

    // 4. Проверка, что все кандидаты были верно инициализированы и добавлены в CandidateStore:
    @AfterAll
    public static void test_requestUser() {
        System.out.println("test_requestUser");
        expectedStore.add(new Candidate("Бобровский Вячеслав Николаевич", "муж", "32", 4, 3));
        expectedStore.add(new Candidate("Колесникова Екатерина Викторовна","жен","28",4,5));
        expectedStore.add(new Candidate("Половцев Иван Петрович", "муж", "23", 2, 4));
        assertThat(expectedStore.size(), equalTo(candidateStore.getCandidateSet().size()));
        Iterator<Candidate> expectedIt = expectedStore.iterator();
        Iterator<Candidate> actualIt = candidateStore.getCandidateSet().iterator();
        while (expectedIt.hasNext()) {
            Candidate expectedCandidate = expectedIt.next();
            Candidate actualCandidate = actualIt.next();
            assertThat(actualCandidate.toString(), equalTo(expectedCandidate.toString()));
        }
    }


}
