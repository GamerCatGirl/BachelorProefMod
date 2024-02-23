package siheynde.bachelorproefmod.Racket;

import jsint.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import siheynde.bachelorproefmod.BachelorProef;
import siheynde.bachelorproefmod.networking.ModPackets;

@Environment(value= EnvType.CLIENT)
public class RacketHandleClasses {

    public static void execute(Object o) {
        BachelorProef.LOGGER.info("Executing Racket code");
        final Class<?> objectClass = o.getClass();

        if (objectClass.equals(Pair.class)) {
            executePair((Pair) o);
        } else if (objectClass.equals(int.class)) {
            BachelorProef.LOGGER.info("Executing Racket code");
        } else {
            throw new IllegalStateException("Unexpected value: " + objectClass);
        }
    }

    private static void executePair(Pair pair) {
        if (pair.isEmpty()) {return;}
        if (pair.first() instanceof String) {
            String first = (String) pair.first();
            Pair arguments = (Pair) pair.rest();
            int x = (int) arguments.first();
            int y = (int) arguments.second();
            int z = (int) arguments.third();

            BachelorProef.LOGGER.info("x: " + x + " y: " + y + " z: " + z);

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeVarInt(x);
            buf.writeVarInt(y);
            buf.writeVarInt(z);

            if (first.equals("move")) {
                ClientPlayNetworking.send(ModPackets.MOVE_ROBOT,  buf);
            }
        }
    }

}
