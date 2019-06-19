package com.infy.model;

import java.util.ArrayList;

public class State {

    private ArrayList<Nugget> nuggets;
    private String stateName;
    private int stateId;
    private static int stateCount = 0;

    public ArrayList<Nugget> getNuggets() {
        return nuggets;
    }

    public void setNuggets(ArrayList<Nugget> nuggets) {
        this.nuggets = nuggets;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public State(String stateName) {
        this.stateName = stateName;
        this.stateId = stateCount++;
        this.nuggets = new ArrayList<>();
    }
}
