package com.example.trabwsmutantes.Model;

import java.util.List;

public class Dashboard {

    private int num;
    private List<String> top3;

    public Dashboard(int num, List<String> top3) {
        this.num = num;
        this.top3 = top3;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<String> getTop3() {
        return top3;
    }

    public void setTop3(List<String> top3) {
        this.top3 = top3;
    }
}
