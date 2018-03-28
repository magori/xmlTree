package ch.hesso.xmltre;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PourTest {
    static org.jdom2.Document document;

    static org.jdom2.Document read(String pathName) throws JDOMException, IOException {
        //On crée une instance de SAXBuilder
        SAXBuilder sxb = new SAXBuilder();
        //On crée un nouveau document JDOM avec en argument le fichier XML
        //Le parsing est terminé ;)
        document = sxb.build(new File(pathName));
        return sxb.build(new File(pathName));
    }

    public static void parseFile() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = generateBuilder();
        File file = new File("test.xml");
        Document doc = builder.parse(file);
    }

    public static void parseString(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = generateBuilder();
        Document doc = builder.parse(xml);
    }

    //Ajouter ces deux méthodes à notre classe JDOM1
    static void affiche() {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, System.out);
        } catch (java.io.IOException e) {
        }
    }

    static void save(String fichier) {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
            //avec en argument le nom du fichier pour effectuer la sérialisation.
            sortie.output(document, new FileOutputStream(fichier));
        } catch (java.io.IOException e) {
        }
    }

    private static DocumentBuilder generateBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        return factory.newDocumentBuilder();
    }
}
