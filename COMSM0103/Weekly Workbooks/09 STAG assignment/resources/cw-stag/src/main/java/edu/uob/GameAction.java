package edu.uob;


import java.util.HashSet;

public class GameAction {
    private final HashSet<String> triggerList;
    private final HashSet<String> subjectsList;
    private final HashSet<String> consumedList;
    private final HashSet<String> producedList;
    private final String narration;

    public GameAction(HashSet<String> triggerList, HashSet<String> subjectsList, HashSet<String> consumedList, HashSet<String> producedList, String narration) {
        this.triggerList = triggerList;
        this.subjectsList = subjectsList;
        this.consumedList = consumedList;
        this.producedList = producedList;
        this.narration = narration;
    }

    public HashSet<String> getTriggerList() {
        return triggerList;
    }

    public HashSet<String> getSubjectsList() {
        return subjectsList;
    }

    public HashSet<String> getConsumedList() {
        return consumedList;
    }

    public HashSet<String> getProducedList() {
        return producedList;
    }

    public String getNarration() {
        return narration;
    }
}
