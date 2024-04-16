package siheynde.bachelorproefmod.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.ByteBuf;
import io.wispforest.lavender.book.Book;
import io.wispforest.lavender.book.BookLoader;
import io.wispforest.lavender.book.LavenderBookItem;
import io.wispforest.lavender.client.LavenderBookScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import org.lwjgl.glfw.GLFW;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.BachelorProefClient;
import siheynde.bachelorproefmod.Racket.RacketHandleClasses;
import siheynde.bachelorproefmod.User;
import siheynde.bachelorproefmod.mixin.PlayerMixin;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.ClientPlayerMixinInterface;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;
import siheynde.bachelorproefmod.world.dimension.ModDimensions;

import java.util.*;

import static net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.getReceived;

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
    private PlayerInventory inventory;
    //private TextFieldWidget predictField;
    //private TextFieldWidget investigateField;

    Identifier dimensionOverworld = DimensionTypes.OVERWORLD.getValue();
    Identifier dimensionMod = ModDimensions.DIMENSION_TYPE.getValue();

    private DrawContext context;
    public String answerRun = "";

    private int amountOfRunButtons;
    private boolean narrow;


    public FunctionScreen(FunctionScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.inventory = inventory;
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

    private <T extends ClickableWidget> void addButton(T button) {
        this.addDrawableChild(button);
        this.buttons.add((FunctionButtonWidget)((Object)button));
    }

    @Override
    protected void init() {
        super.init();
        handler.addListener(this);
        this.buttons.clear();

        //TODO: add first test for which dimension
        Identifier dimensionIn = client.world.getRegistryKey().getValue();

        Boolean inOverworld = dimensionIn.equals(dimensionOverworld);
        Boolean inMod = dimensionIn.equals(dimensionMod);
        int[] yText = {this.y + 60};

        if (inOverworld) {
            Hashtable<String, Hashtable<String, Hashtable<BlockPos, Block>>> topics =  this.shrine.topic.blocks;

            //TODO: look at buttons from menu -> better suited
            topics.forEach((key, value) -> {
                this.addButton(new RunButton(this.x + 50, yText[0], key, this.textRenderer));

                yText[0] = yText[0] + 20;
            });

        } else if (inMod) {
            BachelorProef.LOGGER.info("in test world");
            List<String> PRIMM = Arrays.asList("Predict", "Run", "Investigate", "Modify", "Make");

             ClientPlayNetworking.send(
                    ModPackets.GET_RUN_ID,
                    PacketByteBufs.empty());

            ClientPlayerEntity player = client.player;
            PlayerMixinInterface playerMixin = (PlayerMixinInterface) player;



            //TODO: fix this so we don't create inf loop (maybe with a timeout)
             while(playerMixin.getRunID() == null){
                 BachelorProef.LOGGER.info("runID: " + playerMixin.getRunID());
             }

            BachelorProef.LOGGER.info("runID: " + playerMixin.getRunID());

            //PlayerMixinInterface playerMixinInterface =  (PlayerMixinInterface) client.player;
            //BachelorProef.LOGGER.info("runID: "  + playerMixinInterface.getRunID());

            //String name = player.getName().getLiteralString();

            //User user = BachelorProef.

            //BachelorProef.LOGGER.info("Player: " + name);
            //ClientPlayerMixinInterface playerMixin = (ClientPlayerMixinInterface)player;
            //BachelorProef.LOGGER.info("Player: " + playerMixin.hashCode());

            //Levels.Topic topic = playerMixin.getTopic();
            //BachelorProef.LOGGER.info("Topic: " + topic);
            //String subTopic =  playerMixin.getSelectedSubTopic();

            //PlayerMixinInterface playerMixinInterface = (PlayerMixinInterface) client.player;
            //BachelorProef.LOGGER.info("runID: "  + playerMixinInterface.getRunID());
            //BachelorProef.LOGGER.info("Subtopic: " + subTopic);
            //BachelorProef.LOGGER.info("Topic: " + playerMixin.getTopic());

            PRIMM.forEach((key) -> {
                this.addButton(new RunButton(this.x + 50, yText[0], key, this.textRenderer));
                yText[0] = yText[0] + 20;
            });
        }


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
        return super.keyPressed(keyCode, scanCode, modifiers);
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
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY){
        context.drawCenteredTextWithShadow(this.textRenderer, this.shrineName, x_text, y_predict_text, 0x000000);
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
            //TODO: add a make this exercise button (this way we can delete the run button on the text but put it on correct one)
            //TODO: visuale is the exercise is already done
        }

        @Override
        public void onPress() {

            Identifier dimensionIn = client.world.getRegistryKey().getValue();

            Boolean inOverworld = dimensionIn.equals(dimensionOverworld);
            Boolean inMod = dimensionIn.equals(dimensionMod);

            Levels.Topic topic = shrine.topic;

            BookLoader.loadedBooks().forEach((book) -> {
                if (book.id().toString().equals(topic.bookID)){
                    topic.assignBook(book);
                }
            });

            Book book = topic.book;
            book.entries().forEach((entry) -> {
                if(entry.title().toString().equalsIgnoreCase(runID)) {
                    LavenderBookScreen.pushEntry(book, entry);
                }
            });

            if (inOverworld) {
                BachelorProef.LOGGER.info("in overworld");




                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeString(runID);


                ClientPlayNetworking.send(
                        ModPackets.SET_RUN_ID,
                        buf);

                client.player.sendMessage(Text.of("Go through the portal to start lesson of " + runID));
                close();
                MinecraftClient.getInstance().setScreen(new LavenderBookScreen(book));
            } else if (inMod) {
                BachelorProef.LOGGER.info("in test world");
                BachelorProef.LOGGER.info("Run function of: " + runID);
                close();
            }


            //client.debugRenderer

            //TODO: give the player the book if not in inventory

            //TODO test first in which dimension the player is
            ItemStack bookStack = LavenderBookItem.itemOf(book);
            Boolean hasBook = inventory.contains(bookStack);

            if (!hasBook){
                BachelorProef.LOGGER.info("Bookstack: " + hasBook.toString());
                inventory.player.giveItemStack(bookStack);

                //TODO: give player correct blocks or run the code
            }


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
