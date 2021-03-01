package com.example.myapplication;

public class EnemyTeam {
    int score = 0;
    boolean hasAd = false;
    boolean hasAp = false;
    boolean hasT = false;
    void addAd(){
        if(!this.hasAd){
            this.hasAd = true;
            this.score += 1;
        }
    }
    void addAp(){
        if(!this.hasAp){
            this.hasAp = true;
            this.score += 2;
        }
    }
    void addT(){
        if(!this.hasT){
            this.hasT = true;
            this.score += 4;
        }
    }
}
