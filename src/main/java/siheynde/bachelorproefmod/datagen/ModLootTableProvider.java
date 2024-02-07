package siheynde.bachelorproefmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
   //handles wich block should drop wich item

    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        //addDrop(ModBlocks.BLOCK1); //drops itself

        //addDrop(ModBlocks.BLOCK2, oreDrops(ModBlocks.BLOCK2, ModItems.BLOCK3)); //drops block2 with silk touch other block3

        //click on oreDrops to see different kind of drops implemented by vanilla minecraft

    }
}
