package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.Persister;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class ManipulaterJsonImplTest {

    private static final String exemple = "{\"brand\" : \"Toyota\", \"doors\" : 5, option:{\"transmition\" : \"auto\", \"motor\" : 1.8}}";
    private static final Persister persister = Mockito.mock(Persister.class);
    private static final ManipulaterJsonImpl manipulaterJson = new ManipulaterJsonImpl(persister);

    @Test
    void parseJson() {
        manipulaterJson.parse(exemple);
        Assertions.assertThat(manipulaterJson.getRootElement().getChildren().get(0).getText()).isEqualTo("Toyota");
        Assertions.assertThat(manipulaterJson.getRootElement().getChildren().get(2).getChildren().get(1).getText()).isEqualTo("1.8");
    }

    @Test
    void editElement() {
        manipulaterJson.parse(exemple);
        Assertions.assertThat(manipulaterJson.editElement("2-1", "3").getText()).isEqualTo("3");
    }

    @Test
    void saveDocument() {
        ArgumentCaptor<String> captur = ArgumentCaptor.forClass(String.class);
        manipulaterJson.parse(exemple);
        manipulaterJson.saveDocument();
        Mockito.verify(persister).save(captur.capture(), captur.capture());

        String json = "{\n" +
                "  \"brand\": \"Toyota\",\n" +
                "  \"doors\": 5,\n" +
                "  \"option\": {\n" +
                "    \"transmition\": \"auto\",\n" +
                "    \"motor\": 1.8\n" +
                "  }\n" +
                "}";
        Assertions.assertThat(captur.getAllValues().get(1)).isEqualTo(json);
    }

    @Test
    void load() {
        Mockito.when(persister.load("1")).thenReturn(exemple);
        manipulaterJson.load("1");
        Assertions.assertThat(manipulaterJson.getRootElement().getChildren().get(0).getText()).isEqualTo("Toyota");
    }

}