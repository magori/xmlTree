package ch.hesso.xmleditor;

import ch.hesso.xmleditor.persistence.Persister;
import ch.hesso.xmleditor.persistence.PersisterFileImpl;
import com.github.javafaker.Faker;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XmlGenerator {
    private static final Locale LOCAL_DE_CH = new Locale("de_CH");

    private static final Faker FAKER = Faker.instance(LOCAL_DE_CH);

    private static <R> List<R> list(Supplier<? extends R> supplier, int size) {
        return IntStream.range(0, size).mapToObj(i -> supplier.get()).collect(Collectors.toList());
    }

    @Test
    public void generateXml() {
        Document document = this.createBaseElement();
        document.getRootElement().addContent(list(() -> {
            Element element = new Element("company");
            element.setText(FAKER.company().name());
            element.setContent(list(this::createStaffElement, FAKER.number().numberBetween(10, 100)));
            return element;
        }, FAKER.number().numberBetween(20, 100)));
        Persister persister = new PersisterFileImpl();
        persister.save("src/test/resources/gen.xml", new XMLOutputter(Format.getPrettyFormat()).outputString(document));

    }

    private Element createStaffElement() {
        return new Element("staff").setContent(Arrays.asList(
                new Element("firstName").setText(FAKER.pokemon().name()),
                new Element("lastName").setText(FAKER.artist().name()),
                new Element("age").setText(String.valueOf(FAKER.number().numberBetween(20, 65)))
        ));
    }

    private Document createBaseElement() {
        SAXBuilder builder = new SAXBuilder();
        try {
            return builder.build(new StringReader("<root></root>"));
        } catch (JDOMException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
