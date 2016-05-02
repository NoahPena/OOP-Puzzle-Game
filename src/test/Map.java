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

        private ArrayList<Duple<String, Integer>> properties;

        public TileSet(String path, int firstGID, int tileWidth, int tileHeight, int imageWidth, int imageHeight, String name, ArrayList<Duple<String, Integer>> properties)
        {
            this.path = path;
            this.firstGID = firstGID;
            this.tileWidth = tileWidth;
            this.tileHeight = tileHeight;
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
            this.name = name;
            this.properties = properties;

            try
            {
                mainImage = ImageIO.read(TileSet.class.getResourceAsStream(path));
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

        public int getPropertyValueByName(String name)
        {
            if(properties == null)
            {
                return -1;
            }

            for(int i = 0; i < properties.size(); i++)
            {
                if(properties.get(i).getName().equalsIgnoreCase(name))
                {
                    return properties.get(i).getValue();
                }
            }

            return -1;
        }

        public BufferedImage getImageFromGID(int gid)
        {
            if(gid >= this.firstGID && gid < this.firstGID + amountOfTiles)
            {
                return tiles.get(gid - this.firstGID);
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

    class Duple<T, K>
    {
        private T name;
        private K value;

        public Duple(T name, K value)
        {
            this.name = name;
            this.value = value;
        }

        public T getName()
        {
            return name;
        }

        public K getValue()
        {
            return value;
        }


    }

    class CollisionLayer
    {
        private String name;
        private int layerWidth;
        private int layerHeight;

        private ArrayList<Boolean> collidable;
        private ArrayList<Rectangle> rectangles;

        public CollisionLayer(String name, int width, int height, ArrayList<Integer> tiles, ArrayList<TileSet> tilesets)
        {
            this.name = name;
            this.layerWidth = width;
            this.layerHeight = height;

            collidable = new ArrayList<>();
            rectangles = new ArrayList<>();

            for(int i = 0; i < tiles.size(); i++)
            {
                for(int j = 0; j < tilesets.size(); j++)
                {
                    if (tilesets.get(j).getImageFromGID(tiles.get(i)) != null)
                    {
                        boolean temp = (tilesets.get(j).getPropertyValueByName("passable") == 1) ? true : false;
                        collidable.add(temp);

                        if(temp)
                        {
                           rectangles.add(null);
                        }
                        else
                        {
                            int pointX = -1;
                            int pointY = -1;

                            for(int t = 1; t < height; t++)
                            {
                                if(width * t > i)
                                {
                                    pointX = (i) - ((t - 1) * width);
                                    pointX *= tilesets.get(j).tileWidth;
                                    pointY = (t - 1) * tilesets.get(j).tileHeight;
                                    break;
                                }
                            }

                            if(pointX == -1)
                            {
                                continue;
                            }
                            else
                            {
                                System.out.println("PointX: " + pointX + "\nPointY: " + pointY);

                                rectangles.add(new Rectangle(pointX, pointY, tilesets.get(j).tileWidth, tilesets.get(j).tileHeight));

                            }
                        }
                    }
                }
            }
        }

        public boolean canPass(Rectangle rectangle)
        {
            for(int i = 0; i < rectangles.size(); i++)
            {
                if(rectangles.get(i) != null)
                {
                    if(rectangles.get(i).intersects(rectangle))
                    {
                        return false;
                    }
                }
            }

            return true;
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
    private CollisionLayer collisionLayer;

    private BufferedImage masterImage;


    public Map(String path, String fileName, int drawX, int drawY)
    {
        this.path = path;
        this.fileName = fileName;
        this.drawX = drawX;
        this.drawY = drawY;

        this.setIgnoreRepaint(true);
        this.setDoubleBuffered(true);

        tilesets = new ArrayList<>();
        layers = new ArrayList<>();

        try
        {
            xmlFile = new File(path + File.separator + fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(Map.class.getResourceAsStream(path + File.separator + fileName));

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

                NodeList propertyList = tilesetList.item(i).getChildNodes().item(1).getChildNodes();

                ArrayList<Duple<String, Integer>> properties = new ArrayList<>();

                for(int n = 1; n < propertyList.getLength(); n += 2)
                {
                    Element propertyElement = (Element)propertyList.item(n);
                    String name = propertyElement.getAttribute("name");
                    int value = Integer.parseInt(propertyElement.getAttribute("value"));
                    properties.add(new Duple<>(name, value));
                }

                int firstGID = Integer.parseInt(tilesetElement.getAttribute("firstgid"));
                String name = tilesetElement.getAttribute("name");


                int widthTile = Integer.parseInt(tilesetElement.getAttribute("tilewidth"));
                int heightTile = Integer.parseInt(tilesetElement.getAttribute("tileheight"));
                String source = imageElement.getAttribute("source");
                int imageWidth = Integer.parseInt(imageElement.getAttribute("width"));
                int imageHeight = Integer.parseInt(imageElement.getAttribute("height"));

                System.out.println(path + "/" + source);

                tilesets.add(new TileSet(path + File.separator + source, firstGID, widthTile, heightTile, imageWidth, imageHeight, name, properties));
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

                if(name.equalsIgnoreCase("Collision"))
                {
                    collisionLayer = new CollisionLayer(name, layerWidth, layerHeight, data, tilesets);
                }
                else
                {
                    layers.add(new MapLayer(name, layerWidth, layerHeight, data, tilesets));
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        createMasterImage();
    }

    public boolean testCollision(Rectangle rectangle)
    {
        return collisionLayer.canPass(rectangle);
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

    public void draw(Graphics g)
    {
        g.drawImage(masterImage, drawX, drawY, this);

        if(Main.DEBUG)
        {
            for(int i = 0; i < collisionLayer.rectangles.size(); i++)
            {
                Rectangle rectangle = collisionLayer.rectangles.get(i);
                if(rectangle != null)
                {
                    g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                }
            }
        }
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

//    @Override
//    protected void paintComponent(Graphics g)
//    {
//        super.paintComponent(g);
//        g.drawImage(masterImage, drawX, drawY, this);
//    }




}
