package siheynde.bachelorproefmod.world.dimension;

import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.CustomPortalsMod;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.mixin.PlayerMixin;
import siheynde.bachelorproefmod.structure.shrine.Shrine;
import siheynde.bachelorproefmod.util.ClientPlayerMixinInterface;
import siheynde.bachelorproefmod.util.PlayerMixinInterface;

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

    public static void beforeThroughPortal(Entity entity) {
        //todo: going back to overworld -> delete progress in test dimension & portal
    }

    public static void throughPortal(Entity entity) {
        BachelorProef.LOGGER.info("Teleported to custom dimension type: " + entity);
        //todo: when in new structure -> check if structure already exists, if take correct level to know what to spawn when travelling
        // if in portal of structure -> check if structure activated and if so, spawn blocks in new dimension + functionblock (linked to function block in shrine)
        // when going to portal robot also need to go to new dimension (even if sitted)

        if (entity.isPlayer()) {
            BlockPos pos = entity.getBlockPos();
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
                        BachelorProef.LOGGER.info("Area not empty");
                        return false;
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
