package sample;

import Game.Map;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Class observed by views
 * Presents all datas wich views needs
 */
public class ModeleSujet extends Sujet implements Modele {
    ModeleConcret modeleConcret;
    public ObservableList<String> mapPool;
    public List<Integer> animationMenu;
    public ArrayList<char[]> mapFile = new ArrayList<>();
    public int curSelectedMap;
    public Thread rep;
    public boolean disponible = true;

    public ModeleSujet(){
        this.modeleConcret = new ModeleConcret();
        mapPool = FXCollections.observableArrayList();
    }

    @Override
    public void createMap(String url) throws Exception {
        modeleConcret.createMap(url);
    }

    @Override
    public int getnbCoups() {
        return modeleConcret.getnbCoups();
    }

    @Override
    public String getMapName() {
        return modeleConcret.getMapName();
    }

    @Override
    public String getAuthorName() {
        return modeleConcret.getAuthorName();
    }

    @Override
    public void changeCoups(int i) {
        modeleConcret.changeCoups(i);
    }

    @Override
    public void startParty() {
        modeleConcret.startParty();
    }

    @Override
    public ArrayList<Map> getMaps() {
        return modeleConcret.getMaps();
    }

    @Override
    public void resetMaps() { modeleConcret.resetMaps(); }

    @Override
    public int getIndexCurMap() { return modeleConcret.getIndexCurMap(); }

    public void setIndexCurMap(int i) { modeleConcret.setIndexCurMap(i); }

    @Override
    public void resetIndexCurMap() { modeleConcret.resetIndexCurMap(); }

    @Override
    public Map getMap(){ return getMaps().get(getIndexCurMap()); }

    @Override
    public void setMapName(String newName) {
        modeleConcret.setMapName(newName);
    }

    @Override
    public void setAuthorName(String newName) {
        modeleConcret.setAuthorName(newName);
    }

    @Override
    public boolean PlayerMoves(int x, int y) {
        if (this.disponible) {
            Map map = getMap();
            Map prec = new Map(map);

            if (modeleConcret.PlayerMoves(x, y)) {
                while (getMaps().size() > getIndexCurMap() + 1) {
                    getMaps().remove(getMaps().size() - 1);
                }
                getMaps().add(prec);
                if (getMaps().size() > 1) {
                    Collections.swap(getMaps(), getMaps().size() - 1, getMaps().size() - 2);
                }
                setIndexCurMap(1);

                notifier();
                return true;
            }
        }
            return false;
    }

    @Override
    public boolean checkVictory() {
        return modeleConcret.checkVictory();
    }

    public void runReplay(){
        this.disponible = false;

        this.rep = new Thread(new Replay());
        this.rep.setDaemon(true);

        this.rep.start();

    }

    public void stopReplay(){
        this.rep.stop();
        this.disponible = true;
        modeleConcret.changeCoups(-modeleConcret.getnbCoups()+modeleConcret.getMaps().size()-1);
        modeleConcret.resetIndexCurMap();
        modeleConcret.setIndexCurMap(modeleConcret.getMaps().size()-1);
        notifier();
    }

    class Replay implements Runnable{
        int index = 0;

        @Override
        public void run() {
            index = 0;
            while(index < getMaps().size()){
                setIndexCurMap(-getIndexCurMap()+index);
                modeleConcret.changeCoups(-modeleConcret.getnbCoups()+index);
                Platform.runLater(() -> {
                    notifier();
                    index+= 1;

                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            modeleConcret.changeCoups(-modeleConcret.getnbCoups()+modeleConcret.getMaps().size()-1);
            modeleConcret.resetIndexCurMap();
            modeleConcret.setIndexCurMap(modeleConcret.getMaps().size()-1);
        }
    }

}
