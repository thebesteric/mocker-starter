package com.sourceflag.framework.mocker.core.scanner;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * MockerScanner 扫描接口
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 17:15
 * @since 1.0
 */
public interface MockerScanner {

    /**
     * 扫描
     *
     * @param projectPath  项目路径
     * @param compilePaths 编译路径
     * @author Eric
     * @date 2021/5/25 17:18
     */
    default void scan(String projectPath, String[] compilePaths) {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        if (url != null) {
            if ("file".equals(url.getProtocol())) {
                doScan(new File(projectPath + "/"), compilePaths);
            } else if ("jar".equals(url.getProtocol())) {
                try {
                    JarURLConnection connection = (JarURLConnection) url.openConnection();
                    JarFile jarFile = connection.getJarFile();
                    Enumeration<JarEntry> jarEntries = jarFile.entries();
                    while (jarEntries.hasMoreElements()) {
                        JarEntry jar = jarEntries.nextElement();
                        if (jar.isDirectory() || !jar.getName().endsWith(".class")) {
                            continue;
                        }
                        String jarName = jar.getName();
                        String classPath = jarName.replaceAll("/", ".");
                        String className = classPath.substring(0, classPath.lastIndexOf("."));
                        processClassFile(className);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 扫描
     *
     * @param targetFile   目标文件
     * @param compilePaths 编译路径
     * @author Eric
     * @date 2021/5/25 17:22
     */
    default void doScan(File targetFile, String[] compilePaths) {
        if (targetFile.isDirectory()) {
            for (File file : Objects.requireNonNull(targetFile.listFiles())) {
                doScan(file, compilePaths);
            }
        } else {
            String filePath = targetFile.getPath();
            int index = filePath.lastIndexOf(".");
            if (index != -1 && ".class".equals(filePath.substring(index))) {
                filePath = extractLegalFilePath(filePath, compilePaths);
                if (filePath != null) {
                    String classPath = filePath.replaceAll("\\\\", ".");
                    String className = classPath.substring(0, classPath.lastIndexOf("."));
                    processClassFile(className);
                }
            }
        }
    }

    /**
     * 提取合法的文件路径
     *
     * @param filePath     文件路径
     * @param compilePaths 编译路径
     * @return java.lang.String
     * @author Eric
     * @date 2021/5/25 17:23
     */
    default String extractLegalFilePath(String filePath, String[] compilePaths) {
        for (String compilePath : compilePaths) {
            int index = filePath.indexOf(compilePath);
            if (index != -1) {
                return filePath.substring(index + compilePath.length() + 1);
            }
        }
        return null;
    }

    /**
     * 处理类文件
     *
     * @param className 类的全限定名
     * @author Eric
     * @date 2021/5/25 17:24
     */
    void processClassFile(String className);

}
