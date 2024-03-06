package siheynde.bachelorproefmod.world.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.CustomPortalsMod;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.kyrptonaught.customportalapi.util.SHOULDTP;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.entity.robot.RobotEntity;
import siheynde.bachelorproefmod.mixin.PlayerMixin;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.ClientPlayerMixinInterface;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

import java.awt.*;
import java.util.OptionalLong;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            new Identifier(BachelorProef.MOD_ID, "bachelor_dimension"));
    public static final RegistryKey<World> DIMENSION_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(BachelorProef.MOD_ID, "bachelor_dimension"));
    public static final RegistryKey<DimensionType> DIMENSION_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(BachelorProef.MOD_ID, "bachelor_dimension_type"));

    private static int rangeArea = 32;

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(DIMENSION_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                160, // height
                160, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                DimensionTypes.OVERWORLD_ID, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 0), 0)));
    }

    public static SHOULDTP beforeThroughPortal(Entity entity) {
        //todo: going back to overworld -> delete progress in test dimension & portal
        if (entity.isPlayer()){
            PlayerMixinInterface player = (PlayerMixinInterface) entity;
            RobotEntity robot = player.getRobot();
            if (robot == null) {
                Text text = Text.of("Teleport canceled: No robot assigned");
                entity.sendMessage(text);
                return SHOULDTP.CANCEL_TP;
            }
            //robot.setPosition(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ());
            //BachelorProef.LOGGER.info(rob);
            robot.replace(entity.getBlockPos());
            BachelorProef.LOGGER.info("Player pos: " + entity.getPos().toString());
            BachelorProef.LOGGER.info("Robot: " + robot.toString());
            BachelorProef.LOGGER.info("Robot pos: " + robot.getPos().toString());
        }

        //TODO: teleport robot too new dimension in throughpotyal function
        return SHOULDTP.CONTINUE_TP;
    }

    public static void throughPortal(Entity entity) {
        //todo: when going to portal robot also need to go to new dimension (even if sitted)

        if (entity.isPlayer()) {
            BlockPos pos = entity.getBlockPos();
            PlayerMixinInterface player = (PlayerMixinInterface) entity;

            player.getRobot().replace(pos);
            int x = pos.getX();
            String dimension = entity.getWorld().getRegistryKey().getValue().getPath();
            String overworld = DimensionTypes.OVERWORLD_ID.getPath();

            if (dimension.equalsIgnoreCase(overworld)) {
                BachelorProef.LOGGER.info("in overworld");
            } else {
                BachelorProef.LOGGER.info("in test world");
                BachelorProef.LOGGER.info(entity.getChunkPos().toString());
                BachelorProef.LOGGER.info(areaEmpty(pos, entity.getWorld()).toString());

                if (areaEmpty(pos, entity.getWorld())) {
                    PlayerMixinInterface playerMixin = (PlayerMixinInterface) entity;
                    Shrine shrine = playerMixin.getShrine(pos);
                    shrine.setupUtilTestWorld(entity.getWorld(), pos, rangeArea);
                    BachelorProef.LOGGER.info("Shrine: " + shrine);
                }
                //entity.getWorld().setBlockState(new BlockPos(x + 50, pos.getY(), pos.getZ()), Blocks.ACACIA_FENCE.getDefaultState());

            }

        }
    }

    private static Boolean areaEmpty(BlockPos pos, World world) {
        int x = pos.getX();
        int y = pos.getY(); //TODO: check enkel in de lucht
        int z = pos.getZ();

        for (int i = x - rangeArea; i < x + rangeArea; i++) {
            for (int j = y; j < y + rangeArea; j++) {
                for (int k = z - rangeArea; k < z + rangeArea; k++) {
                    if (! (world.getBlockState(new BlockPos(i, j, k)).isAir()) &&
                            !(world.getBlockState(new BlockPos(i, j, k)).getBlock() == Blocks.IRON_BLOCK) &&
                            !(world.getBlockState(new BlockPos(i, j, k)).getBlock() == CustomPortalsMod.getDefaultPortalBlock()))
                    {
                        world.setBlockState(new BlockPos(i, j, k), Blocks.AIR.getDefaultState());
                        //BachelorProef.LOGGER.info("Area not empty");
                        //return false;
                    }
                }
            }
        }
        BachelorProef.LOGGER.info("Area empty");
        return true;
    }

    public static void newPortal(Entity player, World world, BlockPos portalPos, BlockPos framePos, PortalIgnitionSource portalIgnitionSource) {
        //TODO: register new portal activated in Shrine
        BlockPos pos = player.getBlockPos();
        PlayerMixinInterface playerMixin = (PlayerMixinInterface) player;
        Shrine shrine = playerMixin.getShrine(pos);
        BachelorProef.LOGGER.info("Shrine: " + shrine);
    }
}
