package com.github.utils;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author hangs.zhang
 * @date 2020/06/20 12:07
 * *****************
 * function:
 */
public final class ClassUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String FILE_PROTOCOL = "file";

    private ClassUtils() {
    }

    public static <T> T newInstance(Class<?> clazz, boolean accessible) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            LOGGER.error("newInstance {} error", clazz.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public static void setFieldValue(Field field, Object target, Object fieldValue) {
        field.setAccessible(true);
        try {
            field.set(target, fieldValue);
        } catch (IllegalAccessException e) {
            LOGGER.error("setFieldValue {} error", field.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public static Set<Class<?>> extractPackageClass(String packageName) {
        Objects.requireNonNull(packageName);
        ClassLoader classLoader = getClassLoader();
        String packagePath = packageName.replaceAll("\\.", "/");
        URL url = classLoader.getResource(packagePath);
        if (Objects.isNull(url)) {
            LOGGER.warn("{} has no class", packageName);
            return null;
        }

        Set<Class<?>> result = Sets.newHashSet();
        if (FILE_PROTOCOL.equalsIgnoreCase(url.getProtocol())) {
            File file = new File(url.getPath());
            result.addAll(extractClassFile(file, packageName));
        }
        return result;
    }

    /**
     * 获取目标package下所有class文件
     */
    private static Set<Class<?>> extractClassFile(File rootFile, String packageName) {
        HashSet<Class<?>> result = Sets.newHashSet();
        if (!rootFile.isDirectory()) {
            return result;
        }

        File[] files = rootFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    String absolutePath = file.getAbsolutePath();
                    if (absolutePath.endsWith(".class")) {
                        result.add(getClazz(absolutePath));
                    }
                }
                return false;
            }

            private Class<?> getClazz(final String absolutePath) {
                String path = absolutePath.replace(File.separator, ".");
                String className = path.substring(path.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));
                return loadClass(className);
            }
        });

        if (!Objects.isNull(files)) {
            for (File file : files) {
                // 递归
                result.addAll(extractClassFile(file, packageName));
            }
        }
        return result;
    }

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            LOGGER.error("Class.forName {} error", className, e);
            throw new RuntimeException(e);
        }
    }

    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
