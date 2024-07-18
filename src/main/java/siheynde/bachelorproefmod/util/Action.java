package siheynde.bachelorproefmod.util;

import siheynde.bachelorproefmod.BachelorProef;

public class Action {
    public String type;
    public String blockName;
    public int toPosition;
    public String loopName;

    public Action(String blockName, int toPosition) {
        this.type = "setBlock";
        this.blockName = blockName;
        this.toPosition = toPosition;
        BachelorProef.LOGGER.info("New action made of type " + this.type);
    }

    public Action(int toPosition) {
        this.type = "getBlock";
        this.toPosition = toPosition;
        BachelorProef.LOGGER.info("New action made of type " + this.type);
    }

    public Action(String loopName) {
        // TODO: Implement this method
        this.type = "letLoop";
        this.loopName = loopName;
        BachelorProef.LOGGER.info("New action made of type " + this.type);
    }

    public String toString() {
        return this.type;
    }

}
