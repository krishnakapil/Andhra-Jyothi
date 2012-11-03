

package com.abn.jyothi.parser;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.graphics.Bitmap;

public class MemoryCache {
    private Map<String, SoftReference<Bitmap>> cache=Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());
    private Map<String, SoftReference<List<String>>> cache1=Collections.synchronizedMap(new HashMap<String, SoftReference<List<String>>>());
    
    public Bitmap get(String id){
        if(!cache.containsKey(id))
            return null;
        SoftReference<Bitmap> ref=cache.get(id);
        return ref.get();
    }
    
    public void put(String id, Bitmap bitmap){
        cache.put(id, new SoftReference<Bitmap>(bitmap));
    }
    
    public void put(String id, List<String> bitmap){
        cache1.put(id, new SoftReference<List<String>>(bitmap));
    }
    public List<String> getTxt(String id){
        if(!cache.containsKey(id))
            return null;
        SoftReference<List<String>> ref=cache1.get(id);
        return ref.get();
    }

    public void clear() {
        cache.clear();
    }
}