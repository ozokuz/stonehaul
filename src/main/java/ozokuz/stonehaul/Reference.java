package ozokuz.stonehaul;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class Reference {
    private Reference() {}
    public static final String MOD_ID = "stonehaul";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public static ResourceLocation res(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
