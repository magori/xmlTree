package ch.hesso.xmleditor.display;

import net.openhft.compiler.CompilerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RutimeLoaderImpl {
    private static final Logger LOG = LoggerFactory.getLogger(RutimeLoaderImpl.class);


    private final Map<Class, Class> map;

    private RutimeLoaderImpl(String pathDirectory) {
        this.map = this.load(pathDirectory);
    }

    static String resolvePackage(String ligne) {
        return ligne.split(" ")[1].replace(";", "");
    }

    private Class getClazz(Class clazz) {
        return this.map.get(clazz);
    }

    public Class getClazz(Class clazz, Class original) {
        return this.map.containsKey(clazz) ? this.getClazz(clazz) : original;
    }

    private Map<Class, Class> load(String pathDirectory) {
        try {
            if (Files.exists(Paths.get(pathDirectory))) {
                List<Class> list = Files.list(Paths.get(pathDirectory)).filter(path -> path.toString().endsWith(".java"))
                                        .map((this::toClass)).collect(Collectors.toList());
                list.forEach(clazz -> Arrays.stream(clazz.getInterfaces()).forEach(iClass -> map.put(iClass, clazz)));
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class toClass(Path path) {
        try {
            List<String> lines = Files.readAllLines(path);
            String className = resolvePackage(lines.get(0)) + "." + path.getFileName().toString().split("\\.")[0];
            String javaCode = lines.stream().collect(Collectors.joining());
            return CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
