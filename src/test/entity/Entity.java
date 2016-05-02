package test.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Saphix on 4/30/2016.
 */
public abstract class Entity
{
    //position on map
    private double x;
    private double y;

    //How much the entity will move next update
    private double velX;
    private double velY;

    //current image of entity
    private BufferedImage img;

    //height and width of that image
    private int width;
    private int height;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Point getPosition(){
        return new Point((int)this.x,(int)this.y);
    }

    public void setPosition(Point p){
        this.setX(p.x);
        this.setY(p.y);
    }

    public abstract void draw(Graphics g);
}
