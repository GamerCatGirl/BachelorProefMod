package siheynde.bachelorproefmod.structure;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;

public interface ModStructurePieceType {
    /*
    public static final StructurePieceType SHRINE = ModStructurePieceType.register(ShrineGenerator::new, "Shrine");

    private static StructurePieceType register(Simple type, String id) {
        return Registry.register(Registries.STRUCTURE_PIECE, new Identifier(BachelorProef.MOD_ID, id), (StructurePieceType)type);
    }

    public static interface Simple
            extends ModStructurePieceType {
        public StructurePiece load(NbtCompound var1);

        @Override
        default public StructurePiece load(StructureContext structureContext, NbtCompound nbtCompound) {
            return this.load(nbtCompound);
        }
    }
    */

}
