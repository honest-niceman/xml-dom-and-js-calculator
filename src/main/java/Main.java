import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Must be 2 input file names.");
            return;
        }
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(args[0]);

            NodeList studentNodeList = document.getElementsByTagName("student");

            double realAverage = 0;
            int mark;

            NodeList subjectsList = ((Element) studentNodeList.item(0)).getElementsByTagName("subject");

            for (int j = 0; j < subjectsList.getLength(); j++) {
                mark = Integer.parseInt(((Element) subjectsList.item(j)).getAttribute("mark"));
                realAverage += mark;
            }
            realAverage = realAverage / subjectsList.getLength();

            Node averageNode = ((Element) studentNodeList.item(0)).getElementsByTagName("average").item(0);

            double average = Double.parseDouble((averageNode).getTextContent());

            if (average != realAverage) {
                averageNode.setTextContent(String.valueOf(BigDecimal.valueOf(realAverage)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue()));
            }

            Document outputDoc = builder.newDocument();
            createNewDocument(args[1], studentNodeList, outputDoc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void createNewDocument(String output, NodeList students, Document outputDoc) {

        for (int i = 0; i < students.getLength(); i++) {
            Node student = outputDoc.importNode(students.item(i), true);
            outputDoc.appendChild(student);
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(outputDoc);

            FileWriter writer = new FileWriter(output);
            StreamResult result = new StreamResult(writer);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace();
        }
    }
}
