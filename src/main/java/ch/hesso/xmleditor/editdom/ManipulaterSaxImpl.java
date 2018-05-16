package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.Persister;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.inject.Inject;
import javax.xml.parsers.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ManipulaterSaxImpl implements Manipulater {

    private Persister persister;
    private String idDocument;
    private ElementSaxImpl rootElementSaxImpl;

    //Obligatoire pour l'introspection, afin de determiner le type
    public ManipulaterSaxImpl(){}

    @Inject
    public ManipulaterSaxImpl(Persister persister) {
        this.persister = persister;
    }

    @Override
    public void load(String idDocument) {
        this.idDocument = idDocument;
        rootElementSaxImpl = this.parse(this.persister.load(idDocument));
    }

    @Override
    public List<String> fetchDbList() {
        return persister.fetchDbList();
    }

    @Override
    public String forType() {
        return ManipulaterType.XML.getName();
    }

    @Override
    public Element getRootElement() {
        return rootElementSaxImpl;
    }

    public Element editElement(String id, String newText) {
        ElementSaxImpl element = this.findElement(id);
        element.setText(newText);
        return element;
    }

    @Override
    public Element editElementName(String id, String newName) {
        ElementSaxImpl element = this.findElement(id);
        element.setName(newName);
        return element;
    }

    @Override
    public String addElementToParent(String parentId, String name, String text) {
        ElementSaxImpl parent = this.findElement(parentId);
        int newChildID = parent.getChildren().size();
        parent.getChildren().add(new ElementSaxImpl(name, text));
        return parentId + "-" + newChildID;
    }

    public void saveDocument() {
        String content = createXMLStructure(rootElementSaxImpl);
        try{
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(content)));

            OutputFormat format = new OutputFormat(doc);
            format.setIndenting(true);
            format.setIndent(2);
            format.setOmitXMLDeclaration(false);
            format.setLineWidth(Integer.MAX_VALUE);
            Writer outxml = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(outxml, format);
            serializer.serialize(doc);

            this.persister.save(this.idDocument, outxml.toString());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private ElementSaxImpl findElement(String nodeId) {
        List<Integer> ids = Arrays.stream(nodeId.split("-"))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Element> elements = rootElementSaxImpl.getChildren();
        for (int i = 0; i < (ids.size() - 1); i++) {
            elements = elements.get(ids.get(i)).getChildren();
        }
        return (ElementSaxImpl) elements.get(ids.get(ids.size() - 1));
    }

    private ElementSaxImpl parse(String content){
        InputSource is = new InputSource(new StringReader(content));
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            MySaxParser mySaxParser = new MySaxParser();
            parser.parse(is, mySaxParser);
            return mySaxParser.rootElement;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createXMLStructure(ElementSaxImpl root){
        String xmlElement = "<" + root.getName() + ">";
        if(root.getText().length() > 0)
            xmlElement += "\n" + root.getText() + "\n";
        for(Element e : root.getChildren()){
            xmlElement += createXMLStructure((ElementSaxImpl) e);
        }
        xmlElement += "</" + root.getName() + ">\n";
        return xmlElement;
    }

    private class MySaxParser  extends DefaultHandler {

        private Deque<Pair<Integer, ElementSaxImpl>> myQueue = new LinkedList<Pair<Integer, ElementSaxImpl>>();
        private ElementSaxImpl rootElement;
        private int level = 1;

        //Nous nous servirons de cette variable plus tard
        private String node = null;

        //début du parsing
        public void startDocument() throws SAXException {

        }

        //fin du parsing
        public void endDocument() throws SAXException {
            rootElement = createTree(myQueue);
        }

        // create the tree
        private ElementSaxImpl createTree(Deque<Pair<Integer, ElementSaxImpl>> queue){
            if (queue.isEmpty())
                return null;
            ElementSaxImpl root = queue.poll().getValue();
            createSubTree(root, 2, queue);
            return root;
        }

        // add all children for the current root level
        private void createSubTree(ElementSaxImpl root, int level, Deque<Pair<Integer, ElementSaxImpl>> queue){
            while(!queue.isEmpty() && queue.peek().getKey() >= level){
                if(queue.peek().getKey() == level){
                    root.addChild(queue.poll().getValue());
                }else{
                    ElementSaxImpl lastChild = (ElementSaxImpl) root.getChildren().get(root.getChildren().size() - 1);
                    createSubTree(lastChild, level + 1, queue);
                }
            }
        }

        // add the current element to the queue
        public void startElement(String namespaceURI, String lname, String qname, Attributes attrs) throws SAXException {
            node = qname;
            myQueue.add(new Pair<>(level, new ElementSaxImpl(qname)));
            level++;
        }

        // decrement the level
        public void endElement(String uri, String localName, String qName)
                throws SAXException{
            level--;
        }

        // save the text of the element if there is one
        public void characters(char[] data, int start, int end){
            String str = new String(data, start, end);

            // Test if the first character is NL (ASCII 10)
            if(str.trim().length() > 0){
                myQueue.getLast().getValue().setText(str);
            }
        }
    }
}
