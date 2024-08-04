package siheynde.bachelorproefmod.structure.functions;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.*;

import static java.lang.Thread.sleep;

//This will always be executed server side
public class StrictComparisonBubbleSort implements SubTopic {
    public ArrayList<BlockPos> blocksPredict = new ArrayList<>();
    public List<Block> blocksPredictOrder = Arrays.asList(
            Blocks.BLACK_STAINED_GLASS,
            Blocks.GRAY_STAINED_GLASS,
            Blocks.BLACK_STAINED_GLASS,
            Blocks.WHITE_STAINED_GLASS);

    private final List<String> questionInvestigate =
            List.of("Line number strict comparison: ",
                    "Complexity algorithm: ");
                    //"Where in the algorithm can you see that we need to use strict comparison?",
                    //"What is the time complexity of the algorithm?");
    private final List<String> answersInvestigate =
                            List.of("5", "O(n^2)"); //Dit is try -> niet het juiste antwoord

    private final List<String> explainModify =
            List.of("Make the algorithm work with ",
                    "non-strict comparison",
                    "name function: non-strict-insertion-sort");

    private final List<String> explainMake =
            List.of("Write selection sort", "name function: selection-sort");

    private final String nameFunctionMake = "selection-sort";

    @Override
    public List<String> getExplainModify() {
        return explainModify;
    }

    @Override
    public List<String> getExplainMake() {
        return explainMake;
    }

    @Override
    public List<String> getQuestionsInvestigate() {
        return questionInvestigate;
    }

    @Override
    public List<String> getAnswersInvestigate() {
        return answersInvestigate;
    }


    @Override
    public String runPredict(PlayerEntity player) {
        World world = player.getWorld();
        BachelorProef.LOGGER.info("Running predict");
        BachelorProef.LOGGER.info("Blocks: " + blocksPredict);
        List<Block> blocksPredictCurrentOrder = new ArrayList<>();

        blocksPredict.forEach(blockpos -> {
            BachelorProef.LOGGER.info("Block: " + blockpos);
            Block block = world.getBlockState(blockpos).getBlock();
            BachelorProef.LOGGER.info("Block: " + block);
            blocksPredictCurrentOrder.add(block);
        });

        Collections.reverse(blocksPredictCurrentOrder);


        BachelorProef.LOGGER.info("Blocks current order: " + blocksPredictCurrentOrder);

        if(blocksPredictCurrentOrder.equals(blocksPredictOrder)){
            String prediction = "You predicted the correct outcome! :)";
            Text message = Text.of(prediction);
            player.sendMessage(message);
            return "ok";
        } else {
            String prediction = "You predicted the wrong outcome, but I know you can do better!";
            Text message = Text.of(prediction);
            player.sendMessage(message);
            return "try again";
        }
        //TODO: check if the blocks are in the right order
    }

    @Override
    public void addBlock(String whereToAdd, Block block, BlockPos pos){
        BachelorProef.LOGGER.info("Adding block to " + whereToAdd);
        BachelorProef.LOGGER.info(this.toString());
        if(whereToAdd.equals("Predict")){
            BlockPos newPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()); //needs to be above the other block
            blocksPredict.add(newPos);
        }
    }

    @Override
    public BlockPos getPosition(Integer Position) {
        BachelorProef.LOGGER.info("BlockPositions: " + blocksPredict);
        BachelorProef.LOGGER.info("Blocks: " + blocksPredictOrder);
        Collections.reverse(blocksPredict);
        BlockPos pos =  blocksPredict.get(Position);
        Collections.reverse(blocksPredict);
        return pos;
    }

    @Override
    public void runRun(PlayerEntity player) {
        runCode("insertion-sort", player);
    }

    public void runCode(String nameFunction, PlayerEntity player) {
        ArrayList<Integer> counter = new ArrayList<>();

        // setup blocks to sort in the right order, gotten input vect
        blocksPredictOrder.forEach(block -> {
            BlockPos pos = blocksPredict.get(blocksPredictOrder.size() - 1 - counter.size());
            player.getWorld().setBlockState(pos, block.getDefaultState());
            counter.add(1);
        });

        //packet to client to run scheme code on client side -> file is on client side
        BachelorProef.LOGGER.info("PLAYER WHEN RUNNING RUN: " + player.toString());

        try {
            //send to client to open terminal and save window to control
            ServerPlayNetworking.send((ServerPlayerEntity) player, ModPackets.OPEN_TERMINAL, PacketByteBufs.empty());
            sleep(1000);    //wait for terminal to open
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //send actions back to the server
        PlayerMixinInterface playerInterface = (PlayerMixinInterface) player;
        Shrine shrine = playerInterface.getShrine();

        String answer = shrine.runCode(blocksPredictOrder, nameFunction, player);
        BachelorProef.LOGGER.info("Answer: " + answer);

        BachelorProef.LOGGER.info("Running run");

    }

    @Override
    public void runInvestigate() {

        //TODO: check if answer is correct
        BachelorProef.LOGGER.info("Running investigate");
    }

    @Override
    public void runModify(PlayerEntity player) {
        //Dit moet op client side gerund worden om de edits van de client te kunnen ontvangen
        String nameFunction = "non-strict-insertion-sort";
        runCode(nameFunction, player);
        BachelorProef.LOGGER.info("Running modify");
    }

    @Override
    public void runMake(PlayerEntity player) {
        runCode(nameFunctionMake, player);
        //Dit moet ook op client side gerund worden om de edits van de client te kunnen ontvangen
        BachelorProef.LOGGER.info("Running make");
    }
}
