package ru.netology;

import java.util.Comparator;

public class CandidateComparator implements Comparator<Candidate> {
    @Override
    public int compare(Candidate o1, Candidate o2) {
        int relevanceCompare = o2.getRelevance() - o1.getRelevance();
        if (relevanceCompare == 0)
            relevanceCompare = o2.getRating() - o1.getRating();
        if (relevanceCompare == 0)
            relevanceCompare = o1.getName().compareTo(o2.getName());
        if (relevanceCompare == 0)
            relevanceCompare = o1.getAge().compareTo(o2.getAge());
        if (relevanceCompare == 0)
            relevanceCompare = o1.getSex().compareTo(o2.getSex());

        return relevanceCompare;
    }
}
