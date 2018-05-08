//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zz.dict.util;

import org.springframework.util.StringUtils;

public abstract class AbstractMyStringUtils extends StringUtils {
    public AbstractMyStringUtils() {
    }

    public static boolean allEmpty(Object... objects) {
        if (objects.length == 0) {
            return true;
        } else {
            Object[] var1 = objects;
            int var2 = objects.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Object o = var1[var3];
                if (!isEmpty(o)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean allHasText(CharSequence... strings) {
        if (strings.length == 0) {
            return false;
        } else {
            CharSequence[] var1 = strings;
            int var2 = strings.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CharSequence o = var1[var3];
                if (!hasText(o)) {
                    return false;
                }
            }

            return true;
        }
    }
}
