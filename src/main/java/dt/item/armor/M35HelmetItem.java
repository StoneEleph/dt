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
package dt.item.armor;

import dt.client.renderer.armor.M35HelmetArmorRenderer;
import dt.client.renderer.item.M35HelmetItemRenderer;
import dt.dt;
import dt.tiers.DtArmorMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

import static dt.DTConstants.Armor.Paths;
import static dt.DTConstants.Armor.Tooltips;

public class M35HelmetItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final String armorGeoPath;
    private final String itemGeoPath;
    private final String texturePath;
    private final String tooltipKey;

    public M35HelmetItem() {
        super(DtArmorMaterial.M35, Type.HELMET, new Item.Properties());
        this.armorGeoPath = Paths.M35_GEO;
        this.itemGeoPath = Paths.M35_ITEM_GEO;
        this.texturePath = Paths.M35_TEXTURE;
        this.tooltipKey = Tooltips.M35_STYLE;
    }

    public M35HelmetItem(String armorGeoPath, String itemGeoPath, String texturePath) {
        super(DtArmorMaterial.M35, Type.HELMET, new Item.Properties());
        this.armorGeoPath = armorGeoPath;
        this.itemGeoPath = itemGeoPath;
        this.texturePath = texturePath;
        this.tooltipKey = Tooltips.M35_STYLE;
    }

    public M35HelmetItem(String armorGeoPath, String itemGeoPath, String texturePath, String tooltipKey) {
        super(DtArmorMaterial.M35, Type.HELMET, new Item.Properties());
        this.armorGeoPath = armorGeoPath;
        this.itemGeoPath = itemGeoPath;
        this.texturePath = texturePath;
        this.tooltipKey = tooltipKey;
    }

    public ResourceLocation armorGeo() {
        return dt.loc(this.armorGeoPath);
    }

    public ResourceLocation itemGeo() {
        return dt.loc(this.itemGeoPath);
    }

    public ResourceLocation texture() {
        return dt.loc(this.texturePath);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(Tooltips.HELMET_STYLE).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable(this.tooltipKey).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private M35HelmetArmorRenderer armorRenderer;
            private M35HelmetItemRenderer itemRenderer;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.armorRenderer == null) this.armorRenderer = new M35HelmetArmorRenderer();
                this.armorRenderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.armorRenderer;
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.itemRenderer == null) this.itemRenderer = new M35HelmetItemRenderer();
                return this.itemRenderer;
            }
        });
    }
}
