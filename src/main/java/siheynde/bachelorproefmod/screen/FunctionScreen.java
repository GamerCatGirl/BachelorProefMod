package siheynde.bachelorproefmod.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;

import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.w3c.dom.events.EventException;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import jscheme.JS;

@Environment(value=EnvType.CLIENT)
public class FunctionScreen
        extends HandledScreen<FunctionScreenHandler>
        implements ScreenHandlerListener {
    protected static final int x_text = 2;
    protected static final int y_predict_text = 15;
    protected static final int y_run_text = 50;
    protected static final int y_investigate_text = 85;
    protected static final int y_modify_text = 120;
    protected static final int y_create_text = 155;

    protected static final int backgroundWidth = 248;
    private static final Identifier TEXTURE = new Identifier(BachelorProef.MOD_ID, "textures/gui/function_screen.png");
    static final Identifier CONFIRM_TEXTURE = new Identifier("container/beacon/confirm");
    static final Identifier BUTTON_DISABLED_TEXTURE = new Identifier("container/beacon/button_disabled");
    static final Identifier BUTTON_SELECTED_TEXTURE = new Identifier("container/beacon/button_selected");
    static final Identifier BUTTON_HIGHLIGHTED_TEXTURE = new Identifier("container/beacon/button_highlighted");
    static final Identifier BUTTON_TEXTURE = new Identifier("container/beacon/button");
    static final Identifier CANCEL_TEXTURE = new Identifier("container/beacon/cancel");
    private static final Identifier TEXT_FIELD_TEXTURE = new Identifier("container/anvil/text_field");
    private static final Identifier TEXT_FIELD_DISABLED_TEXTURE = new Identifier("container/anvil/text_field_disabled");
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    private final List<FunctionButtonWidget> buttons = Lists.newArrayList();
    private final String shrineName;
    private TextFieldWidget predictField;
    private TextFieldWidget investigateField;

    private DrawContext context;
    public String answerRun = "";
    private boolean narrow;


    public FunctionScreen(FunctionScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        PlayerMixinInterface playerMixin = (PlayerMixinInterface) inventory.player;
        Shrine shrine = playerMixin.getShrine();

        this.shrineName = shrine.getName();

        //example running scheme code
        try {

            InputStream classLoader = getClass().getClassLoader().getResourceAsStream("assets/bachelorproef/racket/introduction/predict.rkt");
            URL resource = getClass().getClassLoader().getResource("assets/bachelorproef/racket/introduction/predict.rkt");
            BachelorProef.LOGGER.info(classLoader.toString());

            //URI  uri = new URI("file:///src/main/resources/assests/bachelorproef/racket/introduction/predict.rkt");
            //File file = new File("main/resources/assests/bachelorproef/racket/introduction/predict.rkt");
            //JS.load();
            Object object = JS.load(new java.io.FileReader(resource.getFile()));
            BachelorProef.LOGGER.info(object.toString());
            System.out.println(JS.call("predict"));
            BachelorProef.LOGGER.info(JS.eval(object).toString());
            BachelorProef.LOGGER.info(object.toString());
            //JS.load(new java.io.FileReader("src/main/resources/assests/bachelorproef/racket/introduction/predict.rkt"));
        } catch (EventException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        //TODO : get file(s) for shrine from player

        //TODO : get result from function (dr Racket)

        //TODO : ....
    }

    private void setupField(TextFieldWidget field) {
        field.setFocusUnlocked(false);
        field.setEditableColor(-1);
        field.setUneditableColor(-1);
        field.setDrawsBackground(false);
        field.setMaxLength(50);
        field.setChangedListener(this::onRenamed);
        field.setText("testInput1");
        this.addSelectableChild(field);
        field.setEditable(true);
    }

    private <T extends ClickableWidget> void addButton(T button) {
        this.addDrawableChild(button);
        this.buttons.add((FunctionButtonWidget)((Object)button));
    }

    @Override
    protected void init() {
        super.init();
        handler.addListener(this);

        //TEXTFIELDS
        predictField = new TextFieldWidget(this.textRenderer, x + 33, y + 14, 102, 12, Text.translatable("container.repair"));
        investigateField = new TextFieldWidget(this.textRenderer, x + 69, y + 78, 102, 12, Text.translatable("container.repair"));
        setupField(this.predictField);
        setupField(this.investigateField);

        this.setInitialFocus(this.predictField);
        this.predictField.setEditable(true);
        this.setFocused(this.predictField);

        //BUTTONS
        this.buttons.clear();
        this.addButton(new ConfirmButtonWidget(this.x + 140, this.y + 14));
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
        this.investigateField.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        String string = this.predictField.getText();
        String string2 = this.investigateField.getText();
        this.init(client, width, height);
        this.predictField.setText(string);
        this.investigateField.setText(string2);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            //this.client.player.closeHandledScreen();
        }
        if (this.predictField.isActive() && this.predictField.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (this.investigateField.isActive() && this.investigateField.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void onRenamed(String name) {
        //Slot slot = ((FunctionScreenHandler)this.handler).getSlot(0);
        //if (!slot.hasStack()) {
        //    return;
        //}
        //String string = name;
        //if (!slot.getStack().hasCustomName() && string.equals(slot.getStack().getName().getString())) {
        //    string = "";
        //}
        //if (((FunctionScreenHandler)this.handler).setNewPredict(string)) {
            //this.client.player.networkHandler.sendPacket(new RenameItemC2SPacket(string));
        //}
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        //this.recipeBook.update();
    }



    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        this.context = context;
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        context.drawGuiTexture(getFocused() == predictField ? TEXT_FIELD_TEXTURE : TEXT_FIELD_DISABLED_TEXTURE, this.x + 31, this.y + 9, 110, 16);
        context.drawGuiTexture(getFocused() == investigateField ? TEXT_FIELD_TEXTURE : TEXT_FIELD_DISABLED_TEXTURE, this.x + 67, this.y + 73, 110, 16);

    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY){
        context.drawCenteredTextWithShadow(this.textRenderer, "Predict", x_text, y_predict_text, 0x000000);
        context.drawCenteredTextWithShadow(this.textRenderer, "Run", x_text, y_run_text, 0x000000);
        context.drawCenteredTextWithShadow(this.textRenderer, "Investigate", x_text, y_investigate_text, 0x000000);
        context.drawCenteredTextWithShadow(this.textRenderer, "Modify", x_text, y_modify_text, 0x000000);
        context.drawCenteredTextWithShadow(this.textRenderer, "Create", x_text, y_create_text, 0x000000);

        context.drawCenteredTextWithShadow(this.textRenderer, this.answerRun, 120, y_run_text, 0x000000);

    }

    public void drawText(String text, int x, int y) {
        context.drawCenteredTextWithShadow(this.textRenderer, text, x, y, 0x000000);
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
        if(this.predictField.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.predictField);
            investigateField.setFocused(false);
            investigateField.setEditable(false);
            //TODO zet cursor uit
            predictField.setFocused(true);
            predictField.setEditable(true);
            return true;
        }
        if(this.investigateField.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.investigateField);
            predictField.setFocused(false);
            predictField.setEditable(false);
            //predictField.setCursor(-1, false);
            investigateField.setFocused(true);
            investigateField.setEditable(true);
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
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {

    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {

    }



    @Environment(value=EnvType.CLIENT)
    class ConfirmButtonWidget extends IconButtonWidget {

        public ConfirmButtonWidget(int x, int y) {
            super(x, y, CONFIRM_TEXTURE, ScreenTexts.DONE);
            //this.visible = true;
        }

        @Override
        public void onPress() {
            System.out.println("Pressed on confirm button");
            String predict = predictField.getText();
            answerRun = "You pressed the confirm button";
            ClientPlayerEntity player = client.player;
            PlayerMixinInterface playerMixin = (PlayerMixinInterface)player;
            //player.getFunction();
            //player.getVisitedShrines();
            System.out.println("Player" + player);


        }

        @Override
        public void tick(int level) {}
    }

    @Environment(value=EnvType.CLIENT)
    static abstract class IconButtonWidget
            extends BaseButtonWidget {
        private final Identifier texture;

        protected IconButtonWidget(int x, int y, Identifier texture, Text message) {
            super(x, y, message);
            this.texture = texture;
        }

        @Override
        protected void renderExtra(DrawContext context) {
            context.drawGuiTexture(this.texture, this.getX() + 2, this.getY() + 2, 18, 18);
        }

        public void drawText(Text text, int x, int y){
            //super.drawText(context, text, x, y);
        }
    }

    @Environment(value=EnvType.CLIENT)
    static interface FunctionButtonWidget {
        public void tick(int var1);
    }

    @Environment(value=EnvType.CLIENT)
    static abstract class BaseButtonWidget
            extends PressableWidget
            implements FunctionButtonWidget {
        private boolean disabled;

        protected BaseButtonWidget(int x, int y) {
            super(x, y, 22, 22, ScreenTexts.EMPTY);
        }

        protected BaseButtonWidget(int x, int y, Text message) {
            super(x, y, 22, 22, message);
        }

        @Override
        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            Identifier identifier = !this.active ? BUTTON_DISABLED_TEXTURE : (this.disabled ? BUTTON_SELECTED_TEXTURE : (this.isSelected() ? BUTTON_HIGHLIGHTED_TEXTURE : BUTTON_TEXTURE));
            context.drawGuiTexture(identifier, this.getX(), this.getY(), this.width, this.height);
            this.renderExtra(context);
        }

        protected abstract void renderExtra(DrawContext var1);

        public boolean isDisabled() {
            return this.disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        @Override
        public void appendClickableNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }
    }
}
