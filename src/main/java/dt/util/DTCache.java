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
package dt.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import dt.DTConstants;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public final class DTCache<T, R> {
    
    private final LoadingCache<T, R> cache;
    
    private DTCache(CacheBuilder<Object, Object> builder, Function<T, R> loader) {
        this.cache = builder.build(new CacheLoader<>() {
            @Override
            public @NotNull R load(@NotNull T key) {
                return loader.apply(key);
            }
        });
    }
    
    public static <T, R> Builder<T, R> builder(Function<T, R> loader) {
        return new Builder<>(loader);
    }
    
    public R get(T key) {
        return cache.getUnchecked(key);
    }
    
    public void put(T key, R value) {
        cache.put(key, value);
    }
    
    public void invalidate(T key) {
        cache.invalidate(key);
    }
    
    public void invalidateAll() {
        cache.invalidateAll();
    }
    
    public void cleanUp() {
        cache.cleanUp();
    }
    
    public long size() {
        return cache.size();
    }
    
    public static final class Builder<T, R> {
        private final Function<T, R> loader;
        private long expireAfterAccess = DTConstants.Cache.DEFAULT_EXPIRE_MINUTES;
        private TimeUnit expireTimeUnit = TimeUnit.MINUTES;
        private long maximumSize = DTConstants.Cache.MAX_CACHE_SIZE;
        private boolean weakKeys = false;
        private boolean weakValues = false;
        private boolean softValues = false;
        
        private Builder(Function<T, R> loader) {
            this.loader = loader;
        }
        
        public Builder<T, R> expireAfterAccess(long duration, TimeUnit unit) {
            this.expireAfterAccess = duration;
            this.expireTimeUnit = unit;
            return this;
        }
        
        public Builder<T, R> maximumSize(long size) {
            this.maximumSize = size;
            return this;
        }
        
        public Builder<T, R> weakKeys() {
            this.weakKeys = true;
            return this;
        }
        
        public Builder<T, R> weakValues() {
            this.weakValues = true;
            return this;
        }
        
        public Builder<T, R> softValues() {
            this.softValues = true;
            return this;
        }
        
        public DTCache<T, R> build() {
            CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
                    .expireAfterAccess(expireAfterAccess, expireTimeUnit)
                    .maximumSize(maximumSize);
            
            if (weakKeys) builder.weakKeys();
            if (weakValues) builder.weakValues();
            if (softValues) builder.softValues();
            
            return new DTCache<>(builder, loader);
        }
    }
}
