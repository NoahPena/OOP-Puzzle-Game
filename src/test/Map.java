package test;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;


/**
 * Created by noah-pena on 4/29/16.
 */
public class Map
{

    class TileSet
    {
        private String name;
        private int firstGID;
        private int tileWidth;
        private int tileHeight;

        private String path;
        private BufferedImage mainImage;
        private int imageWidth;
        private int imageHeight;

        private ArrayList<BufferedImage> tiles;
        private int amountOfTiles;

        public TileSet(String path, int firstGID, int tileWidth, int tileHeight, int imageWidth, int imageHeight, String name)
        {
            this.path = path;
            this.firstGID = firstGID;
            this.tileWidth = tileWidth;
            this.tileHeight = tileHeight;
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
            this.name = name;

            try
            {
                mainImage = ImageIO.read(new File(path));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            tiles = new ArrayList<BufferedImage>();

            int widthTiles = imageWidth / tileWidth;
            int heightTiles = imageHeight / tileHeight;

            amountOfTiles = widthTiles + heightTiles;

            for(int i = 0; i < heightTiles; i++)
            {
                for(int j = 0; j < widthTiles; j++)
                {
                    tiles.add(mainImage.getSubimage(j * tileWidth, i * tileHeight, tileWidth, tileHeight));
                }
            }
        }

        public BufferedImage getImageFromGID(int gid)
        {
            if(gid >= this.firstGID && gid < this.firstGID + amountOfTiles)
            {
                return tiles.get(gid - 1);
            }

            return null;
        }

        public ArrayList<BufferedImage> getTiles()
        {
            return tiles;
        }

        public int getTileWidth()
        {
            return tileWidth;
        }

        public int getTileHeight()
        {
            return tileHeight;
        }

    }

    class MapLayer
    {

        private String name;
        private int layerWidth;
        private int layerHeight;

        private ArrayList<BufferedImage> data;

        public MapLayer(String name, int width, int height)
        {

        }
    }

    private File xmlFile;
    private String path;
    private String fileName;

    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;

    private ArrayList<TileSet> tilesets;
    private ArrayList<MapLayer> layers;


    public Map(String path, String fileName)
    {
        this.path = path;
        this.fileName = fileName;

        tilesets = new ArrayList<>();
        layers = new ArrayList<>();

        try
        {
            xmlFile = new File(path + "//" + fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            mapWidth = Integer.parseInt(doc.getDocumentElement().getAttribute("width"));
            mapHeight = Integer.parseInt(doc.getDocumentElement().getAttribute("height"));
            tileWidth = Integer.parseInt(doc.getDocumentElement().getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(doc.getDocumentElement().getAttribute("tileheight"));


            //Get TileSets
            NodeList tilesetList = doc.getElementsByTagName("tileset");
            NodeList imageList = doc.getElementsByTagName("image");

            for(int i = 0; i < tilesetList.getLength(); i++)
            {
                Element tilesetElement = (Element)tilesetList.item(i);
                Element imageElement = (Element)imageList.item(i);
                int firstGID = Integer.parseInt(tilesetElement.getAttribute("firstgid"));
                String name = tilesetElement.getAttribute("name");
                int widthTile = Integer.parseInt(tilesetElement.getAttribute("tilewidth"));
                int heightTile = Integer.parseInt(tilesetElement.getAttribute("tileheight"));
                String source = imageElement.getAttribute("source");
                int imageWidth = Integer.parseInt(imageElement.getAttribute("width"));
                int imageHeight = Integer.parseInt(imageElement.getAttribute("height"));

                tilesets.add(new TileSet(path + "//" + source, firstGID, widthTile, heightTile, imageWidth, imageHeight, name));
            }

            //Get Layers
            NodeList layerList = doc.getElementsByTagName("layer");
            NodeList tileList = doc.getElementsByTagName("data");

            for(int i = 0; i < layerList.getLength(); i++)
            {
                Element layerElement = (Element)layerList.item(i);
                Element tileElement = (Element)tileList.item(i);
                System.out.println(tileElement);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
