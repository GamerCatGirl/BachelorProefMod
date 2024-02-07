package siheynde.bachelorproefmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void configure(RegistryWrapper.WrapperLookup arg) {
        //example
        //getOrCreateTagBuilder(ModTags.Blocks.NAME_DETECTOR) //self made tool
        //        .add(ModBlocks.NAME_BLOCK1); //ModBlocks -> from self made blocks
        //        .forceAddTag(BlockTags.GOLD_ORES); //BlockTags -> from vanilla

        //getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE) //existing tool add mineables
        //        .add(ModBlocks.NAME_BLOCK1); //ModBlocks -> from self made blocks

        //getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL) //tools that are only mineable with diamons
        //        .add(ModBlocks.NAME_BLOCK1); //ModBlocks -> from self made blocks

        //needs level netherite -> see Video 12 Kaupenjoe
    }
}
