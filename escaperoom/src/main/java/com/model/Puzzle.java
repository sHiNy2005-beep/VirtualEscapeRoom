package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ItemPuzzle.class, name = "item"),
    @JsonSubTypes.Type(value = CodePuzzle.class, name = "code"),
    @JsonSubTypes.Type(value = RiddlePuzzle.class, name = "riddle")
})
public abstract class Puzzle {
    @JsonProperty("title")
    protected String title;
    
    @JsonProperty("description")
    protected String description;
    
    @JsonProperty("solution")
    protected String solution;
    
    @JsonProperty("hints")
    protected List<String> hints;
    
    @JsonProperty("isSolved")
    protected boolean isSolved;

    public Puzzle() {
        this.hints = new ArrayList<>();
        this.isSolved = false;
    }

    public Puzzle(String title, String description, String solution) {
        this.title = title;
        this.description = description;
        this.solution = solution;
        this.hints = new ArrayList<>();
        this.isSolved = false;
    }

    public boolean checkAnswer(String answer) {
        if (answer == null || solution == null) return false;
        boolean ok = solution.trim().equalsIgnoreCase(answer.trim());
        if (ok) isSolved = true;
        return ok;
    }

    public void addHint(String hint) {
        if (hint != null && !hint.trim().isEmpty()) {
            hints.add(hint);
        }
    }

    public List<String> getHints() {
        return new ArrayList<>(hints);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSolved() {
        return isSolved;
    }
    
    public String getSolution() {
        return solution;
    }
    
    public void setSolved(boolean solved) {
        this.isSolved = solved;
    }

    public String getSolution() { 
        return solution; 
    }

    public void setSolved(boolean solved) { 
        isSolved = solved; 
    }
}