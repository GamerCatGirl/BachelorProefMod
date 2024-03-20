package siheynde.bachelorproefmod.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

@Environment(value= EnvType.CLIENT)
public class TestScreen
    extends HandledScreen<TestScreenHandler>
    implements ScreenHandlerListener {


    public TestScreen(TestScreenHandler handler, PlayerInventory inventory, Text title){
        super(handler, inventory, title);
        //this.handler = handler;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context,mouseX, mouseY, delta);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        this.init(client, width, height);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        //this.recipeBook.update();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        //this.renderBackground(context);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY){
        //TODO:
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double) left || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth) || mouseY >= (double) (top + this.backgroundHeight);
        return bl;
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
    }

    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {

    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {

    }

}
