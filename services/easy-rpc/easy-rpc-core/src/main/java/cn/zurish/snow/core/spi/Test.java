package cn.zurish.snow.core.spi;

import cn.zurish.snow.core.registry.RegistryService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;

import static cn.zurish.snow.core.spi.ExtensionLoader.EXTENSION_LOADER_CLASS_CACHE;

/**
 * 2023/12/31 21:45
 */
@Slf4j
public class Test {
    public static String EXTENSION_LOADER_DIR_PREFIX = "META-INF/easy-rpc/";

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        String servername = "cn.zurish.snow.core.registry.RegistryService";
        loadExtension(RegistryService.class);

    }

    public static void loadExtension(Class<?> clazz) throws IOException, ClassNotFoundException{
        if (clazz == null) {
            throw new IllegalArgumentException("class is null!");
        }
        String spiFilePath = EXTENSION_LOADER_DIR_PREFIX + clazz.getName();
        ClassLoader classLoader = Test.class.getClassLoader();
        //ClassLoader classLoader = Test.this.getClass().getClassLoader();
        log.info(String.valueOf(Test.class.getClassLoader().getResource("META-INF/easy-rpc/cn.zurish.snow.core.registry.RegistryService")));
        Enumeration<URL> enumeration = classLoader.getResources(spiFilePath);
        log.info("===>");
        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();
            log.info(url.toString());
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<>();
            while ((line = bufferedReader.readLine()) != null) {
                //如果配置中加入了#开头则表示忽略该类无需进行加载
                if (line.startsWith("#")) {
                    continue;
                }
                String[] lineArr = line.split("=");
                String implClassName = lineArr[0];
                String interfaceName = lineArr[1];
                classMap.put(implClassName, Class.forName(interfaceName));
            }
            //只会触发class文件的加载，而不会触发对象的实例化
            if (EXTENSION_LOADER_CLASS_CACHE.containsKey(clazz.getName())) {
                //支持开发者自定义配置
                EXTENSION_LOADER_CLASS_CACHE.get(clazz.getName()).putAll(classMap);
            } else {
                EXTENSION_LOADER_CLASS_CACHE.put(clazz.getName(), classMap);
            }
        }
    }
}
