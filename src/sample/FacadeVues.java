package sample;

public class FacadeVues {
    public GameVue gv;
    public MenuVue mv;

    public FacadeVues(GameVue a, MenuVue menu){
        this.gv = a;
        this.mv = menu;
    }
}
