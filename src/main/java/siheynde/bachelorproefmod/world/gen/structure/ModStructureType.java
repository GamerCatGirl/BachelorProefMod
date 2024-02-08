package siheynde.bachelorproefmod.world.gen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import siheynde.bachelorproefmod.BachelorProef;

public class ModStructureType {
    public static final StructureType<ShrineStructure> SHRINE = ModStructureType.register("shrine", ShrineStructure.CODEC);

    private static <S extends Structure> StructureType<S> register(String id, Codec<S> codec) {
        return Registry.register(Registries.STRUCTURE_TYPE, new Identifier(BachelorProef.MOD_ID, id), () -> codec);
    }


    public static void registerStructures() {
        BachelorProef.LOGGER.info("Registering Mod Structures for " + BachelorProef.MOD_ID);
    }
}
