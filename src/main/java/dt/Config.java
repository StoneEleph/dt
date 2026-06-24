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

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = DTConstants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Config {
    
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    
    private static final ForgeConfigSpec.BooleanValue ENABLE_DEBUG_LOGGING = BUILDER
            .comment("Enable debug logging for DT mod")
            .define("enableDebugLogging", false);
    
    private static final ForgeConfigSpec.IntValue CACHE_EXPIRE_MINUTES = BUILDER
            .comment("Cache expire time in minutes")
            .defineInRange("cacheExpireMinutes", DTConstants.Cache.DEFAULT_EXPIRE_MINUTES, 1, 60);
    
    private static final ForgeConfigSpec.IntValue MAX_CACHE_SIZE = BUILDER
            .comment("Maximum cache size")
            .defineInRange("maxCacheSize", DTConstants.Cache.MAX_CACHE_SIZE, 100, 10000);
    
    public static final ForgeConfigSpec SPEC = BUILDER.build();
    
    public static boolean enableDebugLogging;
    public static int cacheExpireMinutes;
    public static int maxCacheSize;
    
    private Config() {}
    
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        enableDebugLogging = ENABLE_DEBUG_LOGGING.get();
        cacheExpireMinutes = CACHE_EXPIRE_MINUTES.get();
        maxCacheSize = MAX_CACHE_SIZE.get();
    }
}
