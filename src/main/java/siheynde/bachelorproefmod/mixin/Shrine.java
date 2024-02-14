package siheynde.bachelorproefmod.mixin;

public class Shrine {
    public int x;
    public int y;
    public int level;

    public Shrine(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public String runCode(){
        return "Answer";
    }
}
