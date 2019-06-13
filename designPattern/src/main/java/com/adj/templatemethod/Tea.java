package com.adj.templatemethod;

public class Tea extends CaffeineBeverage {

    @Override
    void brew() {
        System.out.println("Steeping the tea.");
    }

    @Override
    void addCondiments() {
        System.out.println("Adding lemon.");
    }

    public void boilWater() {
        System.out.println("Boiling water.");
    }


    public void pourInCup() {
        System.out.println("pouring into a cup.");
    }

}
