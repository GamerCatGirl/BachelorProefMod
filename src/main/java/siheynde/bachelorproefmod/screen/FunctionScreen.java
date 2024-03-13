package siheynde.bachelorproefmod.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.lavender.book.Book;
import io.wispforest.lavender.book.BookLoader;
import io.wispforest.lavender.book.Entry;
import io.wispforest.lavender.book.LavenderBookItem;
import io.wispforest.lavender.client.LavenderBookScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.Racket.RacketHandleClasses;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.ClientPlayerMixinInterface;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.*;

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

    private FunctionScreenHandler handler;
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    private final List<FunctionButtonWidget> buttons = Lists.newArrayList();
    private final String shrineName;
    public Shrine shrine;
    //private TextFieldWidget predictField;
    //private TextFieldWidget investigateField;

    private DrawContext context;
    public String answerRun = "";

    private int amountOfRunButtons;
    private boolean narrow;


    public FunctionScreen(FunctionScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.handler = handler;


        //TODO: get the shrine from the player not clientPlayer
        PlayerMixinInterface playerMixin = (PlayerMixinInterface) inventory.player;

        shrine = playerMixin.getShrine(); //TODO: put shrines in server player mixin
        this.shrineName = shrine.getName();

        //this.amountOfRunButtons = shrine.getBlockSetups().size();

        //BachelorProef.LOGGER.info("Amount of run buttons: " + amountOfRunButtons);

        //BachelorProef.LOGGER.info(shrine.predictAnswer());
        //BachelorProef.LOGGER.info(shrine.Modify().toString());
        //BachelorProef.LOGGER.info(shrine.Modify().getClass().toString());

        //RacketHandleClasses.execute(shrine.Modify());

        //jsint.Pair pair = (jsint.Pair) shrine.predictModify();



        //ClientPlayNetworking.send(ModPackets.MOVE_ROBOT,  PacketByteBufs.empty());
        //robot.move(0, 0, 0);

        //TODO : primitive functions to let the robot move !!!!
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
        this.buttons.clear();

        Hashtable<String, Hashtable<String, Hashtable<BlockPos, Block>>> topics =  this.shrine.topic.blocks;
        int[] yText = {this.y + 60};

        //TODO: look at buttons from menu -> better suited
        topics.forEach((key, value) -> {
            this.addButton(new RunButton(this.x + 50, yText[0], key, this.textRenderer));

            yText[0] = yText[0] + 20;
        });

        //TEXTFIELDS
        //predictField = new TextFieldWidget(this.textRenderer, x + 33, y + 14, 102, 12, Text.translatable("container.repair"));
        //investigateField = new TextFieldWidget(this.textRenderer, x + 69, y + 78, 102, 12, Text.translatable("container.repair"));
        //setupField(this.predictField);
        //setupField(this.investigateField);

        //this.setInitialFocus(this.predictField);
        //this.predictField.setEditable(true);
        //this.setFocused(this.predictField);

        //BUTTONS
        //this.buttons.clear();
        //this.addButton(new ConfirmButtonWidget(this.x + 140, this.y + 14));

        //RUN BUTTONS
        //for(int i = 0; i < amountOfRunButtons; i++) {
        //    this.addButton(new RunButton(this.x + 50 + (i * 50), this.y + 50, i, this.textRenderer));
        //}
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        renderBackground(context, mouseX, mouseY, delta);
        this.renderForeground(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    public void renderForeground(DrawContext context, int mouseX, int mouseY, float delta) {
        //this.predictField.render(context, mouseX, mouseY, delta);
        //this.investigateField.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        //String string = this.predictField.getText();
        //String string2 = this.investigateField.getText();
        this.init(client, width, height);
        //this.predictField.setText(string);
        //this.investigateField.setText(string2);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            //this.client.player.closeHandledScreen();
        }
        /*
        if (this.predictField.isActive() && this.predictField.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (this.investigateField.isActive() && this.investigateField.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
         */
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
        //context.drawGuiTexture(getFocused() == predictField ? TEXT_FIELD_TEXTURE : TEXT_FIELD_DISABLED_TEXTURE, this.x + 31, this.y + 9, 110, 16);
        //context.drawGuiTexture(getFocused() == investigateField ? TEXT_FIELD_TEXTURE : TEXT_FIELD_DISABLED_TEXTURE, this.x + 67, this.y + 73, 110, 16);

    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY){
        context.drawCenteredTextWithShadow(this.textRenderer, this.shrineName, x_text, y_predict_text, 0x000000);
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
/*        if(this.predictField.mouseClicked(mouseX, mouseY, button)) {
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

 */
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
    class RunButton extends IconButtonWidget {
        String runID;
        TextRenderer textRenderer;
        int x;
        int y;
        FunctionScreenHandler handler;
        public RunButton(int x, int y, String runID, TextRenderer textRenderer) {
            super(x, y, CONFIRM_TEXTURE, ScreenTexts.DONE);
            this.runID = runID;
            this.textRenderer = textRenderer;
            this.x = x;
            this.y = y;
            //this.handler = handler;
        }

        @Override
        public void onPress() {
            client.player.sendMessage(Text.of("Go through the portal to start lesson of " + runID));

            //TODO: find the lectern and set the book (look range 6 from current player)
            Levels.Topic topic = shrine.topic;
            //TODO: this needs to be replaced when you choose the topic of the shrine
            BookLoader.loadedBooks().forEach((book) -> {
                if (book.id().toString().equals(topic.bookID)){
                    topic.assignBook(book);
                }
            });

            close();

            //PlayerEntity player = client.player;
            Book book = topic.book;

            //BachelorProef.LOGGER.info(book.entries().toString());

            book.entries().forEach((entry) -> {
                if(entry.title().toString().equalsIgnoreCase(runID)) {
                    LavenderBookScreen.pushEntry(book, entry);
                }
            });

            //LavenderBookScreen.pushEntry(book, book.entries().get(0));
            //LavenderBookScreen screen = new LavenderBookScreen(book);
            //screen.push
            //screen

            MinecraftClient.getInstance().setScreen(new LavenderBookScreen(book));
            //LavenderBookItem.
            //PlayerInventory inventory = player.getInventory();

            //BachelorProef.LOGGER.info(player.getInventory());
            //Item giveBook = (Book) topic.book;

            //ItemStack
            //player.giveItemStack(new ItemStack(topic.book));

            //client.player.inven
            //PlayerMixinInterface player = (PlayerMixinInterface) client.player;



            //player.setRunID(runID);
            //PacketByteBuf buf = PacketByteBufs.create();
            //buf.writeVarInt(runID);
            //TODO: send also to server player
            //ClientPlayNetworking.send(ModPackets.SET_RUN_ID,  buf);



        }

        @Override
        public void renderExtra(DrawContext context) {
            //BachelorProef.LOGGER.info("Render x, y: ("  + x + ", " + y + ")");
            context.drawCenteredTextWithShadow(this.textRenderer, runID, x, y, 0x000000);
        }

        @Override
        public void tick(int level) {}
    }

    @Environment(value=EnvType.CLIENT)
    //TODO: problem button is size of ICON
    class ConfirmButtonWidget extends IconButtonWidget {

        public ConfirmButtonWidget(int x, int y) {
            super(x, y, CONFIRM_TEXTURE, ScreenTexts.DONE);
        }

        @Override
        public void onPress() {
            System.out.println("Pressed on confirm button");
            //String predict = predictField.getText();

            answerRun = shrine.predictAnswer();
            ClientPlayerEntity player = client.player;
            ClientPlayerMixinInterface playerMixin = (ClientPlayerMixinInterface)player;
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
