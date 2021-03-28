package ru.netology;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class CandidateStore {
    private Set<Candidate> candidateSet;

    public CandidateStore() {
        candidateSet = new TreeSet<>(new CandidateComparator());
    }

    public Set<Candidate> getCandidateSet() {
        return candidateSet;
    }

    public void addCandidate(Candidate newCandidate) {
        candidateSet.add(newCandidate);
    }



    public void writeCandidates() {
        Iterator<Candidate> it = candidateSet.iterator();
        int cnt = 1;
        System.out.printf("%6s  %40s %25s %25s\n", "№", "ФИО", "Релевантность резюме", "Оценка на собеседовании");
        while (it.hasNext()) {
            Candidate candidate = it.next();
            System.out.printf("%6s. %s\n", cnt, candidate.toString());
            cnt++;
        }
    }
}
