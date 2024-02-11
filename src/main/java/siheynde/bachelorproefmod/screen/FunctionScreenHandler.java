package siheynde.bachelorproefmod.screen;

import net.minecraft.SharedConstants;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import siheynde.bachelorproefmod.block.ModBlocks;

import java.util.Map;
import java.util.Optional;

public class FunctionScreenHandler extends ScreenHandler {
    private String newPredict;
    //private final RecipeInputInventory input = new CraftingInventory(this, 3, 3);
    //private final CraftingResultInventory result = new CraftingResultInventory();

    public FunctionScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
    }

    public boolean isCrafting() {
        return true;
    }

    public int getScaledProgress() {
        return 0;
    };

    public FunctionScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(ModScreenHandlers.FUNCTION_SCREEN, syncId);
    }

    public void updateResult() {
        //ItemStack itemStack = this.input.getStack(0);
        //this.levelCost.set(1);
        int i = 0;
        int j = 0;
        int k = 0;
        //if (itemStack.isEmpty()) {
        //    this.output.setStack(0, ItemStack.EMPTY);
        //    this.levelCost.set(0);
        //    return;
        //}
        //ItemStack itemStack2 = itemStack.copy();
        //ItemStack itemStack3 = this.input.getStack(1);
        //Map<Enchantment, Integer> map = EnchantmentHelper.get(itemStack2);
        //j += itemStack.getRepairCost() + (itemStack3.isEmpty() ? 0 : itemStack3.getRepairCost());
        //this.repairItemUsage = 0;
        if (this.newPredict == null || Util.isBlank(this.newPredict)) {
            // predictInputField is empty
        } else if (!this.newPredict.equals("0")) {

        }
        //this.levelCost.set(j + i);
        //this.sendContentUpdates();
    }

    public boolean setNewPredict(String newItemName) {
        String string = FunctionScreenHandler.sanitize(newItemName);
        if (string == null || string.equals(this.newPredict)) {
            return false;
        }
        this.newPredict = string;
        if (this.getSlot(2).hasStack()) {
            ItemStack itemStack = this.getSlot(2).getStack();
            if (Util.isBlank(string)) {
                itemStack.removeCustomName();
            } else {
                itemStack.setCustomName(Text.literal(string));
            }
        }
        this.updateResult();
        return true;
    }

    private static String sanitize(String name) {
        String string = SharedConstants.stripInvalidChars(name);
        if (string.length() <= 50) {
            return string;
        }
        return null;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

}
