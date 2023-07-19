package ozokuz.stonehaul;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.slf4j.Logger;
import ozokuz.stonehaul.common.content.Content;
import ozokuz.stonehaul.common.content.StonehaulCreativeModeTab;
import ozokuz.stonehaul.data.StonehaulItemTags;
import ozokuz.stonehaul.data.StonehaulRecipeProvider;
import ozokuz.stonehaul.data.VanillaRecipeProvider;
import ozokuz.stonehaul.integration.IntegrationTranslations;

import java.util.Random;

@Mod(Stonehaul.MOD_ID)
public class Stonehaul {
    public static final String MOD_ID = "stonehaul";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);
    public static final Random RANDOM = new Random();

    public static ResourceLocation res(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public Stonehaul() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        StonehaulCreativeModeTab.init();
        IntegrationTranslations.init();
        Content.register(modEventBus);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::gatherData);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Hello from Stonehaul Setup!");
    }

    private void gatherData(final GatherDataEvent event) {
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, StonehaulItemTags::addTags);

        DataGenerator gen = event.getGenerator();

        if (event.includeClient()) {}

        if (event.includeServer()) {
            gen.addProvider(new VanillaRecipeProvider(gen));
            gen.addProvider(new StonehaulRecipeProvider(gen));
        }
    }
}
