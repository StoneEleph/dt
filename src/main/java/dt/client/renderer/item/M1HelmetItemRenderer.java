/*
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * ============================================================
 * 修改声明:
 *   - 基于 Superb Warfare (Atsuishio, Roki27, Light_Quanta) 修改
 *   - 修改者: Stone_Eleph
 *   - 修改日期: 2026-06
 * ============================================================
 */
package dt.client.renderer.item;

import dt.item.armor.M1HelmetItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class M1HelmetItemRenderer extends GeoItemRenderer<M1HelmetItem> {
    public M1HelmetItemRenderer() {
        super(new GeoModel<>() {
            @Override
            public ResourceLocation getAnimationResource(M1HelmetItem object) {
                return null;
            }

            @Override
            public ResourceLocation getModelResource(M1HelmetItem object) {
                return object.itemGeo();
            }

            @Override
            public ResourceLocation getTextureResource(M1HelmetItem object) {
                return object.texture();
            }
        });
    }

    @Override
    public RenderType getRenderType(M1HelmetItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
