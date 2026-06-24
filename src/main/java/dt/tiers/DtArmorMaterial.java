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
package dt.tiers;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public enum DtArmorMaterial implements ArmorMaterial {
    M35("dt:m35", 2000, 3, 2f),
    M1("dt:m1", 2000, 2, 3f),
    MK2("dt:mk2", 2000, 2, 3f),
    SSH40("dt:ssh40", 2000, 2, 1f),
    J90TYPE("dt:j90type", 2000, 1, 0.5f);

    private final String name;
    private final int durability;
    private final int defense;
    private final float toughness;

    DtArmorMaterial(String name, int durability, int defense, float toughness) {
        this.name = name;
        this.durability = durability;
        this.defense = defense;
        this.toughness = toughness;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return this.durability;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return type == ArmorItem.Type.HELMET ? this.defense : 0;
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return 0f;
    }
}
