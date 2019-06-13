package com.adj.templatemethod;

public class Coffee extends CaffeineBeverage {


    @Override
    void brew() {
        System.out.println("Dripping coffee through filter.");
    }

    @Override
    void addCondiments() {
        System.out.println("Adding sugar and milk.");
    }

    public void boilWater() {
        System.out.println("Boiling water.");
    }


    public void pourInCup() {
        System.out.println("pouring into a cup.");
    }

}
