package siheynde.bachelorproefmod.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.structure.shrine.Levels;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.Action;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin implements PlayerMixinInterface {
    @Shadow protected abstract void takeShieldHit(LivingEntity attacker);

    private List<Action> actions = new ArrayList<>();

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }

    private BlockPos robotMoveTo = null;
    private boolean robotArrived = false;

    private boolean HAS_ROBOT_OVERWORLD = false;
    private boolean HAS_ROBOT_TEST_WORLD = false;

    private BlockPos holdBlock = null;

    private Boolean previousActionDone = false;

    @Unique
    RobotEntity robot = null;
    RobotEntity robotTestWorld = null;

    Boolean robotSit = false;

    @Override
    public void setPreviousActionDone(boolean done) {
        previousActionDone = done;
    }

    @Override
    public Boolean getPreviousActionDone() {
        return previousActionDone;
    }

    @Override
    public void makeRobotSit() {
        robotSit = true;
    }

    @Override
    public void makeRobotStand() {
        robotSit = false;
    }

    @Override
    public Boolean isRobotSitting() {
        return robotSit;
    }

    Levels levels = new Levels();
    private ArrayList<Levels.Topic> topics = levels.getTopics();
    public ArrayList<Shrine> visitedShrines = new ArrayList<>();
    int amountShrinesUnlocked = -1;
    String runID;
    String nameShrine;
    ArrayList<String> topicNames;
    String bookID;

    @Override
    public void robotAssignedTestWorld(){
        BachelorProef.LOGGER.info("Robot assigned (test world) to player " + this);
        HAS_ROBOT_TEST_WORLD = true;
    };
    @Override
    public void robotAssigned(){
        BachelorProef.LOGGER.info("Robot assigned (overworld) to player " + this);
        HAS_ROBOT_OVERWORLD = true;
    };

    @Override
    public Boolean hasRobot(){
        return HAS_ROBOT_OVERWORLD;
    };
    @Override
    public Boolean hasRobotTestWorld(){
        return HAS_ROBOT_TEST_WORLD;
    };

    @Override
    public void setRobotMoveTo(BlockPos pos){
        robotMoveTo = pos;
    };

    @Override
    public void setRobotArrtived(boolean arrived){
        robotArrived = arrived;
    };



    @Override
    public BlockPos getRobotMoveTo(){
        return robotMoveTo;
    };

    @Override
    public boolean getRobotArrived(){
        return robotArrived;
    };

    @Override
    public void setRobotHoldBlock(BlockPos pos) {
        holdBlock = pos;
    }

    @Override
    public BlockPos getRobotHoldBlock() {
        return holdBlock;
    }

    @Override
    public void setRobotDropBlock() {
        holdBlock = null;
    }

    @Override
    public Shrine getShrine() {
        int x = (int) ((PlayerEntity) (Object) this).getX();
        int y = (int) ((PlayerEntity) (Object) this).getY();
        int z = (int) ((PlayerEntity) (Object) this).getZ();
        BlockPos pos = new BlockPos(x, y, z);
        return  getShrine(pos);
    }

    @Override
    public void setNameShrine(String name) {
        nameShrine = name;
    }

    @Override
    public String getNameShrine() {
        return nameShrine;
    }

    @Override
    public void setRunID(String runID) {
        this.runID = runID;
    }

    @Override
    public String getRunID() {
        return runID;
    }

    @Override
    public Levels.Topic getTopic() {
        return topics.get(amountShrinesUnlocked);
    }

    //TODO: add shrine completed function to increase amountShrinesUnlocked

    public Shrine getShrine(int level) {
        return visitedShrines.get(level);
    }

    public ArrayList<Shrine> getVisitedShrines() {
        return visitedShrines;
    }

    public void setTopicNames(ArrayList<String> topicNames){this.topicNames = topicNames;};
    public ArrayList<String> getTopicNames(){return this.topicNames;};

    public void setBookID(String bookID){this.bookID = bookID;};
    public String getBookID(){return this.bookID;};

    @Override
    public Shrine getShrine(BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();


        for (Shrine shrine : this.visitedShrines) {
            if (shrine.isInRange(x, y, z)) {
                return shrine;
            }
        }

        amountShrinesUnlocked = amountShrinesUnlocked + 1;
        Levels.Topic topic = topics.get(amountShrinesUnlocked);


        Shrine newShrine = new Shrine(x, y, z, topic);
        visitedShrines.add(newShrine);

        return newShrine;
    }

    @Override
    public List<Action> getActions() {
        return actions;
    }
}
