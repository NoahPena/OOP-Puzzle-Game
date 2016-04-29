package test;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


/**
 * Created by noah-pena on 4/29/16.
 */
public class Map
{

    private File xmlFile;
    private String path;

    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;


    public Map(String path)
    {
        this.path = path;

        try
        {
            xmlFile = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            mapWidth = Integer.parseInt(doc.getDocumentElement().getAttribute("width"));
            mapHeight = Integer.parseInt(doc.getDocumentElement().getAttribute("height"));
            tileWidth = Integer.parseInt(doc.getDocumentElement().getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(doc.getDocumentElement().getAttribute("tileheight"));

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            System.out.println(doc.getDocumentElement().getAttribute("width"));

            //NodeList nList = doc.getElementsByTagName("map");

            //System.out.println(nList.item(0));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
