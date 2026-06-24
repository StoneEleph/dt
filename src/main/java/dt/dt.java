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
package dt;

import com.atsuishio.superbwarfare.api.event.RegisterContainersEvent;
import com.atsuishio.superbwarfare.item.container.ContainerBlockItem;
import com.mojang.logging.LogUtils;
import dt.client.renderer.entity.T26Renderer;
import dt.entity.vehicle.T26Entity;
import dt.item.armor.J90TypeHelmetItem;
import dt.item.armor.M1HelmetItem;
import dt.item.armor.M35HelmetItem;
import dt.item.armor.Mk2HelmetItem;
import dt.item.armor.Ssh40HelmetItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import static dt.DTConstants.Armor.Paths;
import static dt.DTConstants.Armor.Tooltips;
import static dt.DTConstants.Vehicle.T26;

@Mod(DTConstants.MODID)
public class dt {

    public static final String MODID = DTConstants.MODID;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<Item> M35_HELMET = ITEMS.register("m35_helmet", M35HelmetItem::new);
    public static final RegistryObject<Item> M35_HELMET_GREEN = ITEMS.register("m35_helmet_green", () -> new M35HelmetItem(Paths.M35_GEO, Paths.M35_ITEM_GEO, Paths.M35_GREEN_TEXTURE));
    public static final RegistryObject<Item> M35_HELMET_2 = ITEMS.register("m35_helmet_2", () -> new M35HelmetItem(Paths.M35_GEO, Paths.M35_ITEM_GEO, Paths.M35_2_TEXTURE, Tooltips.M35_STYLE_2));
    public static final RegistryObject<Item> M1_HELMET = ITEMS.register("m1_helmet", M1HelmetItem::new);
    public static final RegistryObject<Item> MK2_HELMET = ITEMS.register("mk2_helmet", Mk2HelmetItem::new);
    public static final RegistryObject<Item> SSH40_HELMET = ITEMS.register("ssh40_helmet", Ssh40HelmetItem::new);
    public static final RegistryObject<Item> J90TYPE_HELMET = ITEMS.register("j90type_helmet", J90TypeHelmetItem::new);
    public static final RegistryObject<Item> MODEL_AUTHENTICITY_TIP = ITEMS.register("model_authenticity_tip", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VEHICLE_TAB_ICON_ITEM = ITEMS.register("vehicle_tab_icon", () -> new Item(new Item.Properties()));
    public static final RegistryObject<EntityType<T26Entity>> T26_ENTITY = ENTITIES.register("t26", () -> EntityType.Builder.of(T26Entity::new, MobCategory.MISC)
            .setTrackingRange(T26.TRACKING_RANGE)
            .setUpdateInterval(T26.UPDATE_INTERVAL)
            .fireImmune()
            .sized(T26.WIDTH, T26.HEIGHT)
            .build("t26"));

    public static final RegistryObject<CreativeModeTab> ARMOR_TAB = CREATIVE_MODE_TABS.register("armor", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable("itemGroup.dt.armor"))
            .icon(() -> M35_HELMET.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(MODEL_AUTHENTICITY_TIP.get());
                output.accept(M35_HELMET.get());
                output.accept(M35_HELMET_GREEN.get());
                output.accept(M35_HELMET_2.get());
                output.accept(M1_HELMET.get());
                output.accept(MK2_HELMET.get());
                output.accept(SSH40_HELMET.get());
                output.accept(J90TYPE_HELMET.get());
            })
            .build());
    public static final RegistryObject<CreativeModeTab> VEHICLE_TAB = CREATIVE_MODE_TABS.register("vehicle", () -> CreativeModeTab.builder()
            .withTabsBefore(ARMOR_TAB.getKey())
            .title(Component.translatable("itemGroup.dt.vehicle"))
            .icon(() -> VEHICLE_TAB_ICON_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> output.accept(ContainerBlockItem.createInstance(T26_ENTITY.get())))
            .build());

    public dt() {
        GeckoLib.initialize();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        ENTITIES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        if (Config.enableDebugLogging) {
            LOGGER.info("DT Mod Common Setup - Debug logging enabled");
            LOGGER.info("Cache settings: expire={}min, maxSize={}", Config.cacheExpireMinutes, Config.maxCacheSize);
        }
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MODID, path);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        if (Config.enableDebugLogging) {
            LOGGER.info("DT Mod Server Starting");
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            if (Config.enableDebugLogging) {
                LOGGER.info("DT Mod Client Setup");
            }
        }

        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(T26_ENTITY.get(), T26Renderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void onRegisterContainers(RegisterContainersEvent event) {
            event.add(T26_ENTITY.get());
        }
    }
}
