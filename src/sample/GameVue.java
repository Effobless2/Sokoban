package sample;

import Game.Caisse;
import Game.CaseArrive;
import Game.Map;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * Vue of the Game part of the app. Where the user can play to the game.
 */
public class GameVue implements Observer {

    Sujet modele;

    GridPane map;

    Label nbCoups;
    Label nom;
    Label gameCommandLabel;
    Label navigationCommandLabel;
    Button btnRep;
    Button btnRes;
    Button btnUnd;
    Button btnRed;
    Button back;
    Button nextMap;
    Button previousMap;
    BackgroundImage BGForMap;
    BackgroundImage BGForPanel;
    boolean isVisible;

    public GameVue(Sujet modele){
        this.modele = modele;
        modele.subscribe(this);
        nbCoups = new Label(modele.getnbCoups()+"");
        nbCoups.setFont(Font.font("Webdings", 60));
        nom = new Label("Map " + modele.getMapName() + " de " + modele.getAuthorName());
        gameCommandLabel = new Label("Commandes du jeu :");
        gameCommandLabel.setFont(Font.font("Webdings", 15));
        navigationCommandLabel = new Label("Navigation :");
        navigationCommandLabel.setFont(Font.font("Webdings", 15));
        btnRep = new Button("Replay");
        btnRep.setGraphic(new ImageView(new Image("PNG/replay.png", 20,20,true,true)));
        btnRes = new Button("Reset");
        btnRes.setGraphic(new ImageView(new Image("PNG/reset.png", 20,20,true,true)));
        btnUnd = new Button("Undo (z)");
        btnUnd.setGraphic(new ImageView(new Image("PNG/undo.png", 20,20,true,true)));
        btnUnd.setContentDisplay(ContentDisplay.RIGHT);
        btnRed = new Button("Redo (y)");
        btnRed.setGraphic(new ImageView(new Image("PNG/redo.png", 20,20,true,true)));
        back = new Button("Back");
        back.setGraphic(new ImageView(new Image("PNG/back.png", 20,20,true,true)));
        previousMap = new Button("Previous");
        previousMap.setGraphic(new ImageView(new Image("PNG/previous.png", 20,20,true,true)));
        nextMap = new Button("Next");
        nextMap.setGraphic(new ImageView(new Image("PNG/next.png", 20,20,true,true)));
        nextMap.setContentDisplay(ContentDisplay.RIGHT);
        map = new GridPane();
        map.setAlignment(Pos.CENTER);
        map.setMinWidth(1200);
        BGForMap = new BackgroundImage(
                new Image("PNG/GroundGravel_Grass.png",50,50,true,true),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        //setting the background
        BGForPanel = new BackgroundImage(
                new Image("PNG/GroundGravel_Sand.png",50,50,true,true),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }


    @Override
    public void actualiser() {
        if (isVisible){
            nbCoups.setText(modele.getnbCoups()+"");
            nom.setText("Map " + modele.getMapName() + " de " + modele.getAuthorName());
            createMap();
            if (modele.getMapSelectedIndex() == 0) {
                previousMap.setDisable(true);
                nextMap.setDisable(false);
            }
            else if (modele.getMapSelectedIndex() == modele.getMapFiles().size() - 1){
                nextMap.setDisable(true);
                previousMap.setDisable(false);
            } else {
                previousMap.setDisable(false);
                nextMap.setDisable(false);

            }
        }

    }


    /**
     * Creates the map's view to show from the current map on the modele
     */
    public void createMap(){
        Map map = modele.getMap();

        this.map.getChildren().clear();
        int height = map.map.size();
        int width = 0;
        for (int y = 0; y < map.map.size(); y++) {
            if (width == 0 || map.map.get(y).size()>width){
                width = map.map.get(y).size();
            }
        }

        for (int y = 0; y < map.map.size(); y++){
            for (int x = 0; x < map.map.get(y).size(); x++){

                int heightBlock = 700/height;
                int widthBlock = 1200/width;
                int sizeBlock = Math.min(heightBlock, widthBlock) > 100 ? 100 : Math.min(heightBlock, widthBlock);

                if (!map.map.get(y).get(x).isFree()){
                    if (map.map.get(y).get(x).content instanceof Caisse){
                        sizeBlock *= 0.9;
                        if (map.map.get(y).get(x) instanceof CaseArrive) {
                            MenuVue.addImageGridpane(this.map, "PNG/Crate_Beige.png", sizeBlock, x, y);
                        }
                        else {
                            MenuVue.addImageGridpane(this.map, "PNG/" + map.map.get(y).get(x).content.img(), sizeBlock, x, y);
                        }
                    }
                    else {
                        MenuVue.addImageGridpane(this.map, "PNG/" + map.map.get(y).get(x).content.img(), sizeBlock, x, y);
                    }
                }else if (map.map.get(y).get(x) instanceof CaseArrive){
                    MenuVue.addImageGridpane(this.map, "PNG/" + map.map.get(y).get(x).img(), sizeBlock/3, x, y);
                }
            }
        }

    }
}
