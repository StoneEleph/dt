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
package dt.client.model.armor;

import dt.item.armor.Ssh40HelmetItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Ssh40HelmetModel extends GeoModel<Ssh40HelmetItem> {
    @Override
    public ResourceLocation getAnimationResource(Ssh40HelmetItem object) {
        return null;
    }

    @Override
    public ResourceLocation getModelResource(Ssh40HelmetItem object) {
        return object.armorGeo();
    }

    @Override
    public ResourceLocation getTextureResource(Ssh40HelmetItem object) {
        return object.texture();
    }
}
