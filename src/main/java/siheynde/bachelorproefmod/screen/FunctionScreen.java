package siheynde.bachelorproefmod.screen;

import ca.weblite.objc.Client;
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
import net.minecraft.client.gui.widget.ButtonWidget;
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
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import org.lwjgl.glfw.GLFW;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.structure.functions.SubTopic;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;
import siheynde.bachelorproefmod.world.dimension.ModDimensions;

import java.util.*;

import static java.lang.Thread.sleep;
import static net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.getReceived;

@Environment(value=EnvType.CLIENT)
public class FunctionScreen
        extends HandledScreen<FunctionScreenHandler>
        implements ScreenHandlerListener {
    protected static final int x_text = 2;
    protected static final int y_predict_text = 15;

    protected static final int backgroundWidth = 248;
    private static final Identifier TEXTURE = new Identifier(BachelorProef.MOD_ID, "textures/gui/function_screen.png");
    static final Identifier CONFIRM_TEXTURE = new Identifier("container/beacon/confirm");
    static final Identifier BUTTON_DISABLED_TEXTURE = new Identifier("container/beacon/button_disabled");
    static final Identifier BUTTON_SELECTED_TEXTURE = new Identifier("container/beacon/button_selected");
    static final Identifier BUTTON_HIGHLIGHTED_TEXTURE = new Identifier("container/beacon/button_highlighted");
    static final Identifier BUTTON_TEXTURE = new Identifier("container/beacon/button");

    private FunctionScreenHandler handler;
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    private final List<FunctionButtonWidget> buttons = Lists.newArrayList();
    private String shrineName;
    public Shrine shrine;
    private PlayerInventory inventory;

    private String selectedTab = "";
    //private TextFieldWidget predictField;
    //private TextFieldWidget investigateField;

    String Text_Line1 = "";
    String Text_Line2 = "";
    String Text_Line3 = "";

    Identifier dimensionOverworld = DimensionTypes.OVERWORLD.getValue();
    Identifier dimensionMod = ModDimensions.DIMENSION_TYPE.getValue();

    private DrawContext context;
    public String answerRun = "";
    private boolean narrow;
    private PlayerEntity player;
    private Text ERROR_TEXT;


    public FunctionScreen(FunctionScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.inventory = inventory;
        this.handler = handler;
        this.player = inventory.player;

        //TODO: get the shrine from the player not clientPlayer
        //PlayerEntity clientPlayer = inventory.player;
        ClientPlayNetworking.send(
                ModPackets.GET_NAME_SHRINE,
                PacketByteBufs.empty());

        ClientPlayNetworking.send(
                ModPackets.GET_TOPIC_NAMES,
                PacketByteBufs.empty());

        ClientPlayNetworking.send(
                ModPackets.GET_BOOK_ID,
                PacketByteBufs.empty());



        //BachelorProef.LOGGER.info("Shrine: " + shrine.toString());
        //this.shrineName = shrine.getName();

        //this.amountOfRunButtons = shrine.getBlockSetups().size();

        //BachelorProef.LOGGER.info("Amount of run buttons: " + amountOfRunButtons);

        //BachelorProef.LOGGER.info(shrine.predictAnswer());
        //BachelorProef.LOGGER.info(shrine.Modify().toString());
        //BachelorProef.LOGGER.info(shrine.Modify().getClass().toString());




        //ClientPlayNetworking.send(ModPackets.MOVE_ROBOT,  PacketByteBufs.empty());
        //robot.move(0, 0, 0);

        //TODO : primitive functions to let the robot move !!!!
    }

    @Override
    protected void init() {
        super.init();
        handler.addListener(this);
        this.buttons.clear();

        //this.buttonTopicA = this.addDrawableChild(ButtonWidget.builder(Text.literal("Topic 1"), button -> {
            //this.structureBlock.setRotation(BlockRotation.NONE);
        //    BachelorProef.LOGGER.info("Button pressed");
            //this.updateRotationButton();
        //}).dimensions(this.width / 2 - 1 - 40 - 1 - 40 - 20, 200, 60, 20).build());

        PlayerMixinInterface playerMixin = (PlayerMixinInterface) client.player;
        /*
        if(!playerMixin.hasRobot()){
            Text text = Text.of("Please assign a robot first!");
            player.sendMessage(text);
            ERROR_TEXT = text;
            //context.drawText(this.textRenderer, Text.of("Please assign a robot first!"), x_text - 10, y_predict_text + 30, 0xff0000, false);
            return;
        }
         */

        String nameShrine = playerMixin.getNameShrine();

        while (nameShrine == null) {
            nameShrine = playerMixin.getNameShrine();
            BachelorProef.LOGGER.info("Name shrine is null");
        }
        this.shrineName = nameShrine;

        Identifier dimensionIn = client.world.getRegistryKey().getValue();

        Boolean inOverworld = dimensionIn.equals(dimensionOverworld);
        Boolean inMod = dimensionIn.equals(dimensionMod);
        int[] yText = {this.y + 30};

        if (inOverworld) {
            //REPLACE WITH GET TOPIC NAMES FROM SHRINE
            //Hashtable<String, Hashtable<String, Hashtable<BlockPos, Block>>> topics =  this.shrine.topic.blocks;
            ArrayList<String> topics = playerMixin.getTopicNames();

            //TODO: look at buttons from menu -> better suited
            //TODO: for loop so the result is not null!!!!
            if (topics == null){
                    Text text = Text.of("Try to load the block again!");
                    player.sendMessage(text);
                    ERROR_TEXT = text;
                    return;
            }

            topics.forEach((key) -> {
                //this.addButton(new RunButton(this.x + 50, yText[0], key, this.textRenderer));
                this.addDrawableChild(ButtonWidget.builder(Text.literal(key), button -> {
                    //this.structureBlock.setRotation(BlockRotation.NONE);
                    BachelorProef.LOGGER.info("Button pressed");
                    runButton(key);
                    //this.updateRotationButton();
                }).dimensions(this.x - 10, yText[0], 200, 20).build());

                yText[0] = yText[0] + 25;
            });

        } else if (inMod) {
            BachelorProef.LOGGER.info("in test world");
            List<String> PRIMM = Arrays.asList("Predict", "Run", "Investigate", "Modify", "Make");

             ClientPlayNetworking.send(
                    ModPackets.GET_RUN_ID,
                    PacketByteBufs.empty());
            final int[] xText = {this.x - 35};

            PRIMM.forEach((key) -> {
                //this.addButton(new RunButton(this.x + 50, yText[0], key, this.textRenderer));
                this.addDrawableChild(ButtonWidget.builder(Text.literal(key), button -> {
                    //this.structureBlock.setRotation(BlockRotation.NONE);
                    BachelorProef.LOGGER.info("Button pressed");
                    runButton(key + " Tab");
                    //this.updateRotationButton();
                }).dimensions(xText[0], y - 15, (backgroundWidth) / 5, 20).build());

                xText[0] = xText[0] + (backgroundWidth) / 5;
            });

            selectedTab = "Predict";

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Check Result"), button -> {
                //this.structureBlock.setRotation(BlockRotation.NONE);
                BachelorProef.LOGGER.info("Button pressed");
                runButton(selectedTab);
                //this.updateRotationButton();
            }).dimensions(this.x - 10, yText[0] + 80, 100, 20).build());
        }
    }

    public void runButton(String runID){
        Text_Line1 = "";
        Text_Line2 = "";
        Text_Line3 = "";

        if (runID.equals("Run Tab")){
            selectedTab = "Run";
            //TODO:
            BachelorProef.LOGGER.info("Run Tab");
            return;}
        if(runID.equals("Predict Tab")){
            selectedTab = "Predict";
            //TODO:
            BachelorProef.LOGGER.info("Predict Tab");
            return;}
        if(runID.equals("Investigate Tab")){
            selectedTab = "Investigate";
            //TODO: get the questions from the shrine
            PlayerMixinInterface playerInterface = (PlayerMixinInterface) client.player;
            Shrine shrine = playerInterface.getShrine();
            Levels.Topic topic = shrine.topic;
            String subTopicID = playerInterface.getRunID();
            SubTopic subTopic = topic.getSubTopic(subTopicID);
            List<String> questions = subTopic.getQuestionsInvestigate();
            BachelorProef.LOGGER.info("Questions: " + questions);

            questions.forEach((String textToDisplay) -> {

                if (Text_Line1.equals("")){
                    Text_Line1 = textToDisplay;
                    return;
                }

                if (Text_Line2.equals("")){
                    Text_Line2 = textToDisplay;
                    return;
                }

                if (Text_Line3.equals("")){
                    Text_Line3 = textToDisplay;
                    return;
                }
            });

            //TODO: place the answer input

            //TODO: make sure investigate checks the answers


            BachelorProef.LOGGER.info("Investigate Tab");
            return;}
        if(runID.equals("Modify Tab")){
            selectedTab = "Modify";
            //TODO:
            BachelorProef.LOGGER.info("Modify Tab");
            return;}
        if(runID.equals("Make Tab")){
            selectedTab = "Make";
            //TODO:
            BachelorProef.LOGGER.info("Make Tab");
            return;}

        Identifier dimensionIn = client.world.getRegistryKey().getValue();
        ClientPlayerEntity player = client.player;
        PlayerMixinInterface playerMixin = (PlayerMixinInterface) player;

        Boolean inOverworld = dimensionIn.equals(dimensionOverworld);
        Boolean inMod = dimensionIn.equals(dimensionMod);

        //TODO: get topic from shrine

        String bookID = playerMixin.getBookID();
        //Book book = null;
        ArrayList<Book> books = new ArrayList<>();

        BookLoader.loadedBooks().forEach((bookIter) -> {
            if (bookIter.id().toString().equals(bookID)){
                books.add(bookIter);
                //book = bookIter;
            }
        });

        Book book = books.get(0);

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

            PlayerMixinInterface playerInterface = (PlayerMixinInterface) client.player; //saving in client to
            playerInterface.setRunID(runID); //saving in client to
            playerInterface.setNameShrine(shrineName); //saving in client toJN£

            client.player.sendMessage(Text.of("Go through the portal to start lesson of " + runID));
            close();
            MinecraftClient.getInstance().setScreen(new LavenderBookScreen(book));

        } else if (inMod) {
            BachelorProef.LOGGER.info("in test world");
            BachelorProef.LOGGER.info("Run function of: " + runID);

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(runID);

            ClientPlayNetworking.send(
                    ModPackets.EXECUTE_FUNCTION,
                    buf);
            //TODO: mag ook client side runnen ipv server side!!!!

            playerMixin.setRunID(null);
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
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        //renderBackground(context, mouseX, mouseY, delta);
        //this.renderForeground(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
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
        context.drawText(this.textRenderer, Text.of(this.shrineName), x_text - 20, y_predict_text, 0x000000, false);
        if (ERROR_TEXT != null){
            context.drawText(this.textRenderer, ERROR_TEXT, x_text - 10, y_predict_text + 30, 0x990000, false);
        }

        context.drawText(this.textRenderer, Text_Line1, x_text - 10, y_predict_text + 30, 0x000000, false);
        context.drawText(this.textRenderer, Text_Line2, x_text - 10, y_predict_text + 50, 0x000000, false);
        //context.drawCenteredTextWithShadow(this.textRenderer, this.shrineName, x_text, y_predict_text, 0x000000);

        if (selectedTab.equals("Investigate")){
            BachelorProef.LOGGER.info("add input text");
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
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {

    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {

    }



    @Environment(value=EnvType.CLIENT)
    static interface FunctionButtonWidget {
        public void tick(int var1);
    }

}
