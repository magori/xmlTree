package ch.hesso.xmleditor.display;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

class RutimeLoaderImplTest {


    @Test
    void load() throws URISyntaxException {
        //   RutimeLoaderImpl rutimeLoader = new RutimeLoaderImpl(Paths.get(getClass().getClassLoader().getResource("").toURI()).toString());
    }

    @Test
    void resolvePackage() {
        Assertions.assertThat(RutimeLoaderImpl.resolvePackage("package ch.hesso;")).isEqualTo("ch.hesso");
    }
}