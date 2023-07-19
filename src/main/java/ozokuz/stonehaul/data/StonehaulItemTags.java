package ozokuz.stonehaul.data;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.world.item.Item;
import ozokuz.stonehaul.common.content.Content;

public class StonehaulItemTags {
    public static void addTags(RegistrateTagsProvider<Item> provider) {
        provider.tag(Content.WOODCUTTERS_TAG).addTag(Content.FORGE_TOOLS_AXES);
    }
}
