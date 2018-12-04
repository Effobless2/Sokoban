package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Modele {

    public ArrayList<Observer> subscribers;
    public int nbCoups;
    public String mapName = "Ta mère";
    public String authorName = "En chemise";
    public int score = 12;


    public List<Integer> animationMenu;
    public String title = "SOKOBAN";
    public String buttonText = "JOUER !";
    public ObservableList<String> mapPool;
    public String labelChoix = "Choisir un tableau : ";
    public ArrayList<char[]> mapFile = new ArrayList<>();

    public Modele(){
        subscribers = new ArrayList<Observer>();
        nbCoups = 0;
        mapPool = FXCollections.observableArrayList();
    }

    public void subscribe(Observer ob){
        subscribers.add(ob);
    }

    public void notifier(){
        for (Observer ob : subscribers){
            ob.actualiser();
        }

    }

}
