//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zz.dict.util;

public abstract class AbstractObjectUtil {
    public AbstractObjectUtil() {
    }

    public static boolean allObjectNull(Object... objects) {
        Object[] var1 = objects;
        int var2 = objects.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object o = var1[var3];
            if (o != null) {
                return false;
            }
        }

        return true;
    }

    public static boolean allObjectNotNull(Object... objects) {
        Object[] var1 = objects;
        int var2 = objects.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object o = var1[var3];
            if (o == null) {
                return false;
            }
        }

        return true;
    }

    public static boolean hasObjectNull(Object... objects) {
        return !allObjectNotNull(objects);
    }

    public static boolean hasObjectNotNull(Object... objects) {
        return !allObjectNull(objects);
    }
}
