package siheynde.bachelorproefmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;

public class ModItems {
    //public static final Item USB = registerItem("usb", new Item(new FabricItemSettings()));
    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        //entries.add(USB);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(BachelorProef.MOD_ID, name), item);
    }

    public static void registerItems() {
        BachelorProef.LOGGER.info("Registering Mod Items for " + BachelorProef.MOD_ID);

        // add items to vanille mc  item group
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
