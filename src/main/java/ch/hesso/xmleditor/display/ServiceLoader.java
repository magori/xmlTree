package ch.hesso.xmleditor.display;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@SuppressWarnings({"unchecked", "SameParameterValue"})
class ServiceLoader {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceLoader.class);

    private final Map<Class, List> classListMap;

    private ServiceLoader(List<Class> classes) {
        classListMap = classes.stream().collect(Collectors.toMap(Function.identity(), this::loadService));
    }

    static ServiceLoader load(Class... classes) {
        return new ServiceLoader(Arrays.asList(classes));
    }

    <T> Class<T> getFirstOrDefault(Class<? super T> aClass, Class<T> defaultValue) {
        List services = this.get(aClass);
        if (services == null || services.isEmpty()) {
            return defaultValue;
        }
        return (Class<T>) services.get(0).getClass();
    }

    <T, R> void apply(Class<T> aClass, List<? super T> list, Consumer<? super T> actions, Function<T, R> identifier) {
        List<T> services = this.get(aClass);
        LOG.info("Find service to load {} ", services.size());
        if (!services.isEmpty()) {
            LOG.info("The service loader found {} service(s)", services.size());
            services.forEach((service) -> {
                LOG.info("The {}, will be use for this {} and this identifier: {}", service.getClass(), aClass, identifier
                        .apply(service));
                actions.accept(service);
            });
        }
        list.stream()
            .filter(l -> services.stream().noneMatch(o -> identifier.apply((T) l).equals(identifier.apply(o))))
            .forEach(l -> actions.accept((T) l));
    }

    private List get(Class aClass) {
        return this.classListMap.get(aClass);
    }

    private List loadService(Class c) {
        List list = new ArrayList<>();
        for (Object o : java.util.ServiceLoader.load(c)) {
            list.add(o);
        }
        return list;
    }
}
