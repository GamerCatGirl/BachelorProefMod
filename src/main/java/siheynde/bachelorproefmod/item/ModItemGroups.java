package siheynde.bachelorproefmod.item;


import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup ROBOT_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(BachelorProef.MOD_ID, "robot"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.robot"))
                    .icon(() -> new ItemStack(Items.AMETHYST_BLOCK)).entries(((displayContext, entries) -> {
                        entries.add(ModBlocks.FUNCTION_BLOCK);
                    })).build());

    public static void registerItemGroups() {
        BachelorProef.LOGGER.info("Registering Mod Item Groups for " + BachelorProef.MOD_ID);
    }

}
