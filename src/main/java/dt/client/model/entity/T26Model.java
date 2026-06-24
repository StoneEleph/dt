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
package dt.client.model.entity;

import com.atsuishio.superbwarfare.client.model.entity.VehicleModel;
import dt.entity.vehicle.T26Entity;
import net.minecraft.util.Mth;

import java.util.regex.Pattern;

import static dt.DTConstants.Vehicle.T26.Model;

public class T26Model extends VehicleModel<T26Entity> {
    
    private static final class AnimationKeyframes {
        static final float PHASE1_END = 37.6667f;
        static final float PHASE2_END = 38.5833f;
        static final float PHASE3_END = 40.3333f;
        static final float PHASE4_END = 42.9167f;
        static final float PHASE5_END = 44.25f;
        static final float PHASE6_END = 52.4167f;
        static final float PHASE7_END = 84.5833f;
        static final float PHASE8_END = 84.9167f;
        static final float PHASE9_END = 92.5833f;
        static final float PHASE10_END = 93f;
        static final float PHASE11_END = 93.4167f;
        static final float PHASE12_END = 94.25f;
        static final float PHASE13_END = 94.9167f;
        static final float PHASE14_END = 95.25f;
        static final float PHASE15_END = 95.75f;
        static final float PHASE16_END = 96.8333f;
        static final float PHASE17_END = 97.5f;
        static final float PHASE18_END = 97.5833f;
        static final float PHASE19_END = 98.8333f;
        static final float PHASE20_END = 99.25f;
        static final float PHASE21_END = 99.5833f;
        static final float ANIMATION_END = 100f;
        
        static final float ROT_X_MAX_1 = -45f;
        static final float ROT_X_MAX_2 = -90f;
        static final float ROT_X_MAX_3 = -135f;
        static final float ROT_X_MAX_4 = -177.75f;
        static final float ROT_X_MAX_5 = -210f;
        static final float ROT_X_MAX_6 = -220f;
        static final float ROT_X_MAX_7 = -243.33f;
        static final float ROT_X_MAX_8 = -270f;
        static final float ROT_X_MAX_9 = -315f;
        static final float ROT_X_MAX_10 = -360f;
        
        static final float MOVE_Y_MAX_1 = -1.8f;
        static final float MOVE_Y_MAX_2 = -4.1f;
        static final float MOVE_Y_MAX_3 = -10.3f;
        static final float MOVE_Y_MAX_4 = -12.9f;
        static final float MOVE_Y_MAX_5 = -23.96f;
        static final float MOVE_Y_MAX_6 = -12.93f;
        static final float MOVE_Y_MAX_7 = -10.085f;
        static final float MOVE_Y_MAX_8 = -4.585f;
        static final float MOVE_Y_MAX_9 = -1.165f;
        static final float MOVE_Y_MAX_10 = -0.25f;
        
        static final float MOVE_Z_MAX_1 = 111.6f;
        static final float MOVE_Z_MAX_2 = 113.25f;
        static final float MOVE_Z_MAX_3 = 116f;
        static final float MOVE_Z_MAX_4 = 113.5f;
        static final float MOVE_Z_MAX_5 = 96.25f;
        static final float MOVE_Z_MAX_6 = 14.095f;
        static final float MOVE_Z_MAX_7 = -3.565f;
        static final float MOVE_Z_MAX_8 = -6.35f;
        static final float MOVE_Z_MAX_9 = -6.39f;
        static final float MOVE_Z_MAX_10 = -3.03f;
        static final float MOVE_Z_MAX_11 = -1.95f;
        static final float MOVE_Z_OFFSET = 110f;
        
        static final float SCALE_FACTOR = (23.96f - 3f) / 23.96f;
        
        private AnimationKeyframes() {}
    }
    
    private static final Pattern TRACK_PATTERN = Pattern.compile("^track(?<type>Mov|Rot)(?<direction>[LR])(?<id>\\d+)$");
    private static final Pattern WHEEL_PATTERN = Pattern.compile("^wheel(?<direction>[LR]).*$");

    @Override
    public TransformContext<T26Entity> collectTransform(String boneName) {
        if (Model.BARREL_BONE.equals(boneName)) {
            return (bone, vehicle, state) -> {
                float a = getTurretYaw();
                float r = (Mth.abs(a) - Model.ROTATION_DIVISOR) / Model.ROTATION_DIVISOR;

                float r2;

                if (Mth.abs(a) <= Model.ROTATION_DIVISOR) {
                    r2 = a / Model.ROTATION_DIVISOR;
                } else {
                    if (a < 0) {
                        r2 = -(180f + a) / Model.ROTATION_DIVISOR;
                    } else {
                        r2 = (180f - a) / Model.ROTATION_DIVISOR;
                    }
                }

                float base = Mth.clamp(-getTurretXRot() - r * getPitch() - r2 * getRoll(), vehicle.getTurretMinPitch(), vehicle.getTurretMaxPitch());
                bone.setRotX(-base * Mth.DEG_TO_RAD);

                var barrelLaser = getAnimationProcessor().getBone(Model.BARREL_LASER_BONE);
                if (barrelLaser != null) {
                    barrelLaser.setRotX(bone.getRotX());
                }
            };
        }

        var trackMatcher = TRACK_PATTERN.matcher(boneName);
        if (trackMatcher.matches()) {
            return (bone, vehicle, state) -> bone.setHidden(true);
        }

        var wheelMatcher = WHEEL_PATTERN.matcher(boneName);
        if (wheelMatcher.matches()) {
            var isL = wheelMatcher.group("direction").equals("L");

            if (boneName.endsWith("Turn")) {
                return (bone, vehicle, state) -> {
                    bone.setRotZ(Model.WHEEL_ROTATION_MULTIPLIER * (isL ? getLeftWheelRot() : getRightWheelRot()));
                    bone.setRotY(Mth.lerp(state.getPartialTick(), vehicle.getRudderRotO(), vehicle.getRudderRot()));
                };
            } else {
                return (bone, vehicle, state) -> bone.setRotZ(Model.WHEEL_ROTATION_MULTIPLIER * (isL ? getLeftWheelRot() : getRightWheelRot()));
            }
        }

        return super.collectTransform(boneName);
    }

    @Override
    public float getBoneRotX(float t) {
        if (t <= AnimationKeyframes.PHASE1_END) return 0f;
        if (t <= AnimationKeyframes.PHASE2_END) return Mth.lerp((t - AnimationKeyframes.PHASE1_END) / (AnimationKeyframes.PHASE2_END - AnimationKeyframes.PHASE1_END), 0f, AnimationKeyframes.ROT_X_MAX_1);
        if (t <= AnimationKeyframes.PHASE3_END) return Mth.lerp((t - AnimationKeyframes.PHASE2_END) / (AnimationKeyframes.PHASE3_END - AnimationKeyframes.PHASE2_END), AnimationKeyframes.ROT_X_MAX_1, AnimationKeyframes.ROT_X_MAX_2);
        if (t <= AnimationKeyframes.PHASE4_END) return Mth.lerp((t - AnimationKeyframes.PHASE3_END) / (AnimationKeyframes.PHASE4_END - AnimationKeyframes.PHASE3_END), AnimationKeyframes.ROT_X_MAX_2, AnimationKeyframes.ROT_X_MAX_3);
        if (t <= AnimationKeyframes.PHASE5_END) return Mth.lerp((t - AnimationKeyframes.PHASE4_END) / (AnimationKeyframes.PHASE5_END - AnimationKeyframes.PHASE4_END), AnimationKeyframes.ROT_X_MAX_3, AnimationKeyframes.ROT_X_MAX_4);
        if (t <= AnimationKeyframes.PHASE7_END) return AnimationKeyframes.ROT_X_MAX_4;
        if (t <= AnimationKeyframes.PHASE8_END) return Mth.lerp((t - AnimationKeyframes.PHASE7_END) / (AnimationKeyframes.PHASE8_END - AnimationKeyframes.PHASE7_END), AnimationKeyframes.ROT_X_MAX_4, AnimationKeyframes.ROT_X_MAX_5);
        if (t <= AnimationKeyframes.PHASE9_END) return AnimationKeyframes.ROT_X_MAX_5;
        if (t <= AnimationKeyframes.PHASE11_END) return Mth.lerp((t - AnimationKeyframes.PHASE9_END) / (AnimationKeyframes.PHASE11_END - AnimationKeyframes.PHASE9_END), AnimationKeyframes.ROT_X_MAX_5, AnimationKeyframes.ROT_X_MAX_6);
        if (t <= AnimationKeyframes.PHASE12_END) return AnimationKeyframes.ROT_X_MAX_6;
        if (t <= AnimationKeyframes.PHASE13_END) return Mth.lerp((t - AnimationKeyframes.PHASE12_END) / (AnimationKeyframes.PHASE13_END - AnimationKeyframes.PHASE12_END), AnimationKeyframes.ROT_X_MAX_6, AnimationKeyframes.ROT_X_MAX_7);
        if (t <= AnimationKeyframes.PHASE15_END) return Mth.lerp((t - AnimationKeyframes.PHASE13_END) / (AnimationKeyframes.PHASE15_END - AnimationKeyframes.PHASE13_END), AnimationKeyframes.ROT_X_MAX_7, AnimationKeyframes.ROT_X_MAX_8);
        if (t <= AnimationKeyframes.PHASE16_END) return AnimationKeyframes.ROT_X_MAX_8;
        if (t <= AnimationKeyframes.PHASE18_END) return Mth.lerp((t - AnimationKeyframes.PHASE16_END) / (AnimationKeyframes.PHASE18_END - AnimationKeyframes.PHASE16_END), AnimationKeyframes.ROT_X_MAX_8, AnimationKeyframes.ROT_X_MAX_9);
        if (t <= AnimationKeyframes.PHASE19_END) return AnimationKeyframes.ROT_X_MAX_9;
        if (t <= AnimationKeyframes.PHASE21_END) return Mth.lerp((t - AnimationKeyframes.PHASE19_END) / (AnimationKeyframes.PHASE21_END - AnimationKeyframes.PHASE19_END), AnimationKeyframes.ROT_X_MAX_9, AnimationKeyframes.ROT_X_MAX_10);
        return 0f;
    }

    @Override
    public float getBoneMoveY(float t) {
        float y;
        if (t <= AnimationKeyframes.PHASE1_END) y = 0f;
        else if (t <= AnimationKeyframes.PHASE2_END) y = Mth.lerp((t - AnimationKeyframes.PHASE1_END) / (AnimationKeyframes.PHASE2_END - AnimationKeyframes.PHASE1_END), 0f, AnimationKeyframes.MOVE_Y_MAX_1);
        else if (t <= AnimationKeyframes.PHASE3_END) y = Mth.lerp((t - AnimationKeyframes.PHASE2_END) / (AnimationKeyframes.PHASE3_END - AnimationKeyframes.PHASE2_END), AnimationKeyframes.MOVE_Y_MAX_1, AnimationKeyframes.MOVE_Y_MAX_2);
        else if (t <= AnimationKeyframes.PHASE4_END) y = Mth.lerp((t - AnimationKeyframes.PHASE3_END) / (AnimationKeyframes.PHASE4_END - AnimationKeyframes.PHASE3_END), AnimationKeyframes.MOVE_Y_MAX_2, AnimationKeyframes.MOVE_Y_MAX_3);
        else if (t <= AnimationKeyframes.PHASE5_END) y = Mth.lerp((t - AnimationKeyframes.PHASE4_END) / (AnimationKeyframes.PHASE5_END - AnimationKeyframes.PHASE4_END), AnimationKeyframes.MOVE_Y_MAX_3, AnimationKeyframes.MOVE_Y_MAX_4);
        else if (t <= AnimationKeyframes.PHASE6_END) y = Mth.lerp((t - AnimationKeyframes.PHASE5_END) / (AnimationKeyframes.PHASE6_END - AnimationKeyframes.PHASE5_END), AnimationKeyframes.MOVE_Y_MAX_4, AnimationKeyframes.MOVE_Y_MAX_5);
        else if (t <= AnimationKeyframes.PHASE7_END) y = AnimationKeyframes.MOVE_Y_MAX_5;
        else if (t <= AnimationKeyframes.PHASE10_END) y = Mth.lerp((t - AnimationKeyframes.PHASE7_END) / (AnimationKeyframes.PHASE10_END - AnimationKeyframes.PHASE7_END), AnimationKeyframes.MOVE_Y_MAX_5, AnimationKeyframes.MOVE_Y_MAX_6);
        else if (t <= AnimationKeyframes.PHASE14_END) y = Mth.lerp((t - AnimationKeyframes.PHASE10_END) / (AnimationKeyframes.PHASE14_END - AnimationKeyframes.PHASE10_END), AnimationKeyframes.MOVE_Y_MAX_6, AnimationKeyframes.MOVE_Y_MAX_7);
        else if (t <= AnimationKeyframes.PHASE17_END) y = Mth.lerp((t - AnimationKeyframes.PHASE14_END) / (AnimationKeyframes.PHASE17_END - AnimationKeyframes.PHASE14_END), AnimationKeyframes.MOVE_Y_MAX_7, AnimationKeyframes.MOVE_Y_MAX_8);
        else if (t <= AnimationKeyframes.PHASE19_END) y = Mth.lerp((t - AnimationKeyframes.PHASE17_END) / (AnimationKeyframes.PHASE19_END - AnimationKeyframes.PHASE17_END), AnimationKeyframes.MOVE_Y_MAX_8, AnimationKeyframes.MOVE_Y_MAX_9);
        else if (t <= AnimationKeyframes.PHASE20_END) y = Mth.lerp((t - AnimationKeyframes.PHASE19_END) / (AnimationKeyframes.PHASE20_END - AnimationKeyframes.PHASE19_END), AnimationKeyframes.MOVE_Y_MAX_9, AnimationKeyframes.MOVE_Y_MAX_10);
        else y = Mth.lerp((t - AnimationKeyframes.PHASE20_END) / (AnimationKeyframes.ANIMATION_END - AnimationKeyframes.PHASE20_END), AnimationKeyframes.MOVE_Y_MAX_10, 0f);

        return y * AnimationKeyframes.SCALE_FACTOR;
    }

    @Override
    public float getBoneMoveZ(float t) {
        if (t <= AnimationKeyframes.PHASE1_END) return Mth.lerp(t / (AnimationKeyframes.PHASE1_END - 0f), 0f, AnimationKeyframes.MOVE_Z_MAX_1) - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE2_END) return Mth.lerp((t - AnimationKeyframes.PHASE1_END) / (AnimationKeyframes.PHASE2_END - AnimationKeyframes.PHASE1_END), AnimationKeyframes.MOVE_Z_MAX_1, AnimationKeyframes.MOVE_Z_MAX_2) - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE3_END) return Mth.lerp((t - AnimationKeyframes.PHASE2_END) / (AnimationKeyframes.PHASE3_END - AnimationKeyframes.PHASE2_END), AnimationKeyframes.MOVE_Z_MAX_2, AnimationKeyframes.MOVE_Z_MAX_3) - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE4_END) return AnimationKeyframes.MOVE_Z_MAX_3 - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE5_END) return Mth.lerp((t - AnimationKeyframes.PHASE4_END) / (AnimationKeyframes.PHASE5_END - AnimationKeyframes.PHASE4_END), AnimationKeyframes.MOVE_Z_MAX_3, AnimationKeyframes.MOVE_Z_MAX_4) - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE6_END) return Mth.lerp((t - AnimationKeyframes.PHASE5_END) / (AnimationKeyframes.PHASE6_END - AnimationKeyframes.PHASE5_END), AnimationKeyframes.MOVE_Z_MAX_4, AnimationKeyframes.MOVE_Z_MAX_5) - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE7_END) return Mth.lerp((t - AnimationKeyframes.PHASE6_END) / (AnimationKeyframes.PHASE7_END - AnimationKeyframes.PHASE6_END), AnimationKeyframes.MOVE_Z_MAX_5, AnimationKeyframes.MOVE_Z_MAX_6) - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE10_END) return Mth.lerp((t - AnimationKeyframes.PHASE7_END) / (AnimationKeyframes.PHASE10_END - AnimationKeyframes.PHASE7_END), AnimationKeyframes.MOVE_Z_MAX_6, AnimationKeyframes.MOVE_Z_MAX_7) - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE14_END) return Mth.lerp((t - AnimationKeyframes.PHASE10_END) / (AnimationKeyframes.PHASE14_END - AnimationKeyframes.PHASE10_END), AnimationKeyframes.MOVE_Z_MAX_7, AnimationKeyframes.MOVE_Z_MAX_8) - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE17_END) return Mth.lerp((t - AnimationKeyframes.PHASE14_END) / (AnimationKeyframes.PHASE17_END - AnimationKeyframes.PHASE14_END), AnimationKeyframes.MOVE_Z_MAX_8, AnimationKeyframes.MOVE_Z_MAX_9) - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE19_END) return Mth.lerp((t - AnimationKeyframes.PHASE17_END) / (AnimationKeyframes.PHASE19_END - AnimationKeyframes.PHASE17_END), AnimationKeyframes.MOVE_Z_MAX_9, AnimationKeyframes.MOVE_Z_MAX_10) - AnimationKeyframes.MOVE_Z_OFFSET;
        if (t <= AnimationKeyframes.PHASE20_END) return Mth.lerp((t - AnimationKeyframes.PHASE19_END) / (AnimationKeyframes.PHASE20_END - AnimationKeyframes.PHASE19_END), AnimationKeyframes.MOVE_Z_MAX_10, AnimationKeyframes.MOVE_Z_MAX_11) - AnimationKeyframes.MOVE_Z_OFFSET;
        return Mth.lerp((t - AnimationKeyframes.PHASE20_END) / (AnimationKeyframes.ANIMATION_END - AnimationKeyframes.PHASE20_END), AnimationKeyframes.MOVE_Z_MAX_11, 0f) - AnimationKeyframes.MOVE_Z_OFFSET;
    }
}
