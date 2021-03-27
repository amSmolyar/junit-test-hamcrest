package ru.netology;

public class Candidate {
    private String name;
    private String sex;
    private String age;
    private int relevance;
    private int rating;

    public Candidate(String name, String sex, String age, int relevance, int rating) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.relevance = relevance;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }

    public int getRelevance() {
        return relevance;
    }

    public int getRating() {
        return rating;
    }

    public String toString() {
        return String.format("%40s %25s %25s", name, relevance, rating);
    }
}

