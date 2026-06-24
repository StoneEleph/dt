package dt.client.renderer.armor;

import dt.client.model.armor.M35HelmetModel;
import dt.item.armor.M35HelmetItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class M35HelmetArmorRenderer extends GeoArmorRenderer<M35HelmetItem> {
    public M35HelmetArmorRenderer() {
        super(new M35HelmetModel());
        this.head = new GeoBone(null, "", false, 0.0, false, false);
    }

    @Override
    public RenderType getRenderType(M35HelmetItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}

