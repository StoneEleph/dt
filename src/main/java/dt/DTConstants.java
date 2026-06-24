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

import net.minecraft.resources.ResourceLocation;

public final class DTConstants {
    
    public static final String MODID = "dt";
    
    private DTConstants() {}
    
    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MODID, path);
    }
    
    public static final class Vehicle {
        public static final class T26 {
            public static final float WIDTH = 2.45f;
            public static final float HEIGHT = 1.54f;
            public static final int TRACKING_RANGE = 512;
            public static final int UPDATE_INTERVAL = 1;
            
            public static final class Animation {
                public static final String FIRE = "animation.t26.fire";
                public static final String IDLE = "animation.t26.idle";
                public static final String CONTROLLER_NAME = "cannon";
                public static final int CONTROLLER_TRANSITION_TICKS = 0;
            }
            
            public static final class Model {
                public static final String BARREL_BONE = "barrel";
                public static final String BARREL_LASER_BONE = "barrelLaser";
                public static final float ROTATION_DIVISOR = 90f;
                public static final float WHEEL_ROTATION_MULTIPLIER = 1.5f;
            }
            
            private T26() {}
        }
        
        private Vehicle() {}
    }
    
    public static final class Armor {
        public static final class Paths {
            public static final String M35_GEO = "geo/m35.geo.json";
            public static final String M35_ITEM_GEO = "geo/m35item.geo.json";
            public static final String M35_TEXTURE = "textures/armor/m35.png";
            public static final String M35_GREEN_TEXTURE = "textures/armor/m35_1.png";
            public static final String M35_2_TEXTURE = "textures/armor/m35_2.png";
            
            public static final String M1_GEO = "geo/m1.geo.json";
            public static final String M1_ITEM_GEO = "geo/m1item.geo.json";
            public static final String M1_TEXTURE = "textures/armor/m1.png";
            
            public static final String MK2_GEO = "geo/mk2.geo.json";
            public static final String MK2_ITEM_GEO = "geo/mk2item.geo.json";
            public static final String MK2_TEXTURE = "textures/armor/mk2.png";
            
            public static final String SSH40_GEO = "geo/ssh40.geo.json";
            public static final String SSH40_ITEM_GEO = "geo/ssh40item.geo.json";
            public static final String SSH40_TEXTURE = "textures/armor/ssh40.png";
            
            public static final String J90TYPE_GEO = "geo/j90type.json";
            public static final String J90TYPE_ITEM_GEO = "geo/j90typeitem.json";
            public static final String J90TYPE_TEXTURE = "textures/armor/j90type.png";
        }
        
        public static final class Tooltips {
            public static final String HELMET_STYLE = "tooltip.dt.helmet_style";
            public static final String M35_STYLE = "tooltip.dt.m35_helmet.style";
            public static final String M35_STYLE_2 = "tooltip.dt.m35_helmet_2.style";
        }
        
        private Armor() {}
    }
    
    public static final class Cache {
        public static final int DEFAULT_EXPIRE_MINUTES = 5;
        public static final int MAX_CACHE_SIZE = 1000;
        
        private Cache() {}
    }
}
