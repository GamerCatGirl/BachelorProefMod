package siheynde.bachelorproefmod.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public record BlockIDs() {
    public static final Block black_stained_glass = Blocks.BLACK_STAINED_GLASS;
    public static final Block white_stained_glass = Blocks.WHITE_STAINED_GLASS;
    public static final Block gray_stained_glass = Blocks.GRAY_STAINED_GLASS;

    public static Block getBlockID(String blockName) {
        switch (blockName) {
            case "black_stained_glass" -> {
                return Blocks.BLACK_STAINED_GLASS;
            }
            case "white_stained_glass" -> {return white_stained_glass;}
            case "gray_stained_glass" -> {return gray_stained_glass;}
            default -> {return null;}
        }
    }

}
