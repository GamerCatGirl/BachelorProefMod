package siheynde.bachelorproefmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.types.Func;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.screen.FunctionScreenHandler;

import java.util.List;

@Environment(value=EnvType.CLIENT)
public class FunctionScreen
        extends HandledScreen<FunctionScreenHandler>
        implements RecipeBookProvider, ScreenHandlerListener {

    protected static final int backgroundWidth = 248;
    private static final Identifier TEXTURE = new Identifier(BachelorProef.MOD_ID, "textures/gui/function_screen.png");
    private static final Identifier TEXT_FIELD_TEXTURE = new Identifier("container/anvil/text_field");
    private TextFieldWidget predictInputField;
    private TextFieldWidget InvestigateInputField;
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    private TextFieldWidget predictField;
    private boolean narrow;

    public FunctionScreen(FunctionScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        handler.addListener(this);


        this.predictField = new TextFieldWidget(this.textRenderer, x + 33, y + 13, 103, 12, Text.translatable("container.repair"));
        this.predictField.setFocusUnlocked(false);
        this.predictField.setEditableColor(-1);
        this.predictField.setUneditableColor(-1);
        this.predictField.setDrawsBackground(false);
        this.predictField.setMaxLength(50);
        this.predictField.setChangedListener(this::onRenamed);
        this.predictField.setText("testInput");
        this.addSelectableChild(this.predictField);
        this.setInitialFocus(this.predictField);
        this.predictField.setEditable(((FunctionScreenHandler)this.handler).getSlot(0).hasStack());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        renderBackground(context, mouseX, mouseY, delta);
        this.renderForeground(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    public void renderForeground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.predictField.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        String string = this.predictField.getText();
        this.init(client, width, height);
        this.predictField.setText(string);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            //this.client.player.closeHandledScreen();
        }
        if (this.predictField.keyPressed(keyCode, scanCode, modifiers) || this.predictField.isActive()) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void onRenamed(String name) {
        Slot slot = ((FunctionScreenHandler)this.handler).getSlot(0);
        if (!slot.hasStack()) {
            return;
        }
        String string = name;
        if (!slot.getStack().hasCustomName() && string.equals(slot.getStack().getName().getString())) {
            string = "";
        }
        if (((FunctionScreenHandler)this.handler).setNewPredict(string)) {
            //this.client.player.networkHandler.sendPacket(new RenameItemC2SPacket(string));
        }
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        //this.recipeBook.update();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        context.drawGuiTexture(TEXT_FIELD_TEXTURE, this.x + 31, this.y + 9, 110, 16);


        renderProgressArrow(context, x, y);
    }

    private void onPredictInput(String name) {
        Slot slot = handler.getSlot(0);
        if (!slot.hasStack()) {
            return;
        }
        String string = name;
        if (!slot.getStack().hasCustomName() && string.equals(slot.getStack().getName().getString())) {
            string = "";
        }
        if (handler.setNewPredict(string)) {
            //this.client.player.networkHandler.sendPacket(new RenameItemC2SPacket(string));

            //Rename item
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY){

    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 85, y + 30, 176, 0, 8, handler.getScaledProgress());
        }
    }

    @Override
    protected boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY) {
        return (!this.narrow || !this.recipeBook.isOpen()) && super.isPointWithinBounds(x, y, width, height, pointX, pointY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.recipeBook);
            return true;
        }
        if (this.narrow && this.recipeBook.isOpen()) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.backgroundWidth) || mouseY >= (double)(top + this.backgroundHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight, button) && bl;
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public void refreshRecipeBook() {
        this.recipeBook.refresh();
    }

    @Override
    public RecipeBookWidget getRecipeBookWidget() {
        return this.recipeBook;
    }

    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {

    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {

    }
}
