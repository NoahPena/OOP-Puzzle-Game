package test;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;


/**
 * Created by noah-pena on 4/29/16.
 */
public class Map extends JPanel
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


        public MapLayer(String name, int width, int height, ArrayList<Integer> tiles, ArrayList<TileSet> tilesets)
        {
            this.name = name;
            this.layerWidth = width;
            this.layerHeight = height;

            data = new ArrayList<>();

            convertTilesToData(tiles, tilesets);
        }

        private void convertTilesToData(ArrayList<Integer> tiles, ArrayList<TileSet> tilesets)
        {
            for(int i = 0; i < tiles.size(); i++)
            {
                if(tiles.get(i) != 0)
                {
                    for (int j = 0; j < tilesets.size(); j++)
                    {
                        if (tilesets.get(j).getImageFromGID(tiles.get(i)) != null)
                        {
                            BufferedImage image = tilesets.get(j).getImageFromGID(tiles.get(i));
                            data.add(image);
                            break;
                        }
                    }
                }
                else
                {
                    data.add(null);
                }
            }
        }

        public ArrayList<BufferedImage> getLayerTiles()
        {
            return data;
        }

        public int getLength()
        {
            return data.size();
        }
    }

    private File xmlFile;
    private String path;
    private String fileName;

    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;

    private int drawX;
    private int drawY;

    private ArrayList<TileSet> tilesets;
    private ArrayList<MapLayer> layers;

    private BufferedImage masterImage;


    public Map(String path, String fileName, int drawX, int drawY)
    {
        this.path = path;
        this.fileName = fileName;
        this.drawX = drawX;
        this.drawY = drawY;

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

            for(int i = 0; i < layerList.getLength(); i++)
            {
                Element layerElement = (Element)layerList.item(i);

                String name = layerElement.getAttribute("name");
                int layerWidth = Integer.parseInt(layerElement.getAttribute("width"));
                int layerHeight = Integer.parseInt(layerElement.getAttribute("height"));

                ArrayList<Integer> data = new ArrayList<>();

                NodeList tileList = layerList.item(i).getChildNodes().item(1).getChildNodes();

                //System.out.println(tileList.item(1));

                for(int j = 1; j < tileList.getLength(); j++)
                {
                    if(tileList.item(j).getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element tileElement = (Element)tileList.item(j);
                        String gid = tileElement.getAttribute("gid");

                        data.add(Integer.parseInt(gid));
                    }
                }

                layers.add(new MapLayer(name, layerWidth, layerHeight, data, tilesets));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        createMasterImage();
    }

    private void createMasterImage()
    {
        ArrayList<BufferedImage> masterList = new ArrayList<>();

        for(int k = 0; k < layers.get(0).getLayerTiles().size(); k++)
        {
            masterList.add(null);
        }

        for(int i = 0; i < layers.size(); i++)
        {
            ArrayList<BufferedImage> data = layers.get(i).getLayerTiles();

            for(int j = 0; j < data.size(); j++)
            {
                BufferedImage image = data.get(j);

                if(masterList.get(j) == null)
                {
                    masterList.set(j, image);
                }
                else if(masterList.get(j) != null)
                {
                    masterList.set(j, image);
                }
                else if(image == null)
                {

                }
            }
        }

        masterImage = new BufferedImage(tileWidth * mapWidth, tileHeight * mapHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = masterImage.createGraphics();
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0, tileWidth * mapWidth, tileHeight * mapHeight);
        graphics.setBackground(Color.WHITE);

        for(int s = 0; s < mapHeight; s++)
        {
            for(int l = 0; l < mapWidth; l++)
            {
                graphics.drawImage(masterList.get(l + (s * mapWidth)), l * tileWidth, s * tileHeight, null);
            }
        }

        System.out.println(masterImage);

    }

    public int getDrawX()
    {
        return this.drawX;
    }

    public int getDrawY()
    {
        return this.drawY;
    }

    public void setDrawPosition(int x, int y)
    {
        this.drawX = x;
        this.drawY = y;
    }

    public void setDrawSize(int x, int y)
    {
        this.setPreferredSize(new Dimension(x, y));
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(masterImage, drawX, drawY, this);
    }


}
