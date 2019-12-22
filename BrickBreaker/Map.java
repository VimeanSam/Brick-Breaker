/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BrickBreaker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author James Andrews
 * @author Vimean Sam
 */
public class Map {
    BufferedReader readmap;
    int worldwidth, worldheight;
    int tiles[][];
    Rectangle rect;

    ArrayList<Rectangle>bricks = new ArrayList<Rectangle>();
    ArrayList<Rectangle>bossRec = new ArrayList<Rectangle>();
    ArrayList<Rectangle>slowblock = new ArrayList<Rectangle>();
    ArrayList<Rectangle>speedblock = new ArrayList<Rectangle>();
    ArrayList<Rectangle>unbreakableblock = new ArrayList<Rectangle>();
    ArrayList<Rectangle>lifeblock = new ArrayList<Rectangle>();
    ArrayList<Rectangle>spikeblock = new ArrayList<Rectangle>();
    ArrayList<Rectangle>questionblock = new ArrayList<Rectangle>();

    boolean brick, isBoss = false;
    public BufferedImage boss;
    BufferedImage redblock = ImageLoader.loadImage("/Assets/slowblock.png");
    BufferedImage greenblock = ImageLoader.loadImage("/Assets/speedboost.png");
    BufferedImage chain = ImageLoader.loadImage("/Assets/chain.png");
    BufferedImage life = ImageLoader.loadImage("/Assets/heartblock.png");
    BufferedImage spike = ImageLoader.loadImage("/Assets/spikeblock.png");
    BufferedImage randomblock = ImageLoader.loadImage("/Assets/random.png");

    public Map(BufferedImage bossImg) {
      this.boss = bossImg;
    }
    
    void removeAllRectangles() {
      this.bricks.clear();
      this.bossRec.clear();
      this.slowblock.clear();
      this.speedblock.clear();
      this.unbreakableblock.clear();
      this.lifeblock.clear();
      this.spikeblock.clear();
      this.questionblock.clear();

      brick = false;
      isBoss = false;
    }

    public void loadMap(String file) {
      try {
        this.readmap = new BufferedReader(new FileReader(file));
        this.worldwidth = Integer.parseInt(readmap.readLine());
        this.worldheight = Integer.parseInt(readmap.readLine());
        this.tiles = new int[worldheight][worldwidth];

        for(int i = 0; i < worldheight; i++) {
          String line = readmap.readLine();
          String[] tokens = line.split(" ");

          for(int j = 0; j < worldwidth; j++) {
            tiles[i][j] = Integer.parseInt(tokens[j]);
            }
          }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    public void renderMap(Graphics2D g) {

        for(int i = 0; i < worldheight; i++){
            for(int j = 0; j < worldwidth; j++){
                int floors = tiles[i][j];
                if(floors == 1) {
                    g.setColor(Color.white);
                    g.fillRect(j*100, i*30, 100, 30);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*100, i*30, 100, 30);
                    if(!brick){
                        bricks.add(new Rectangle(j*100, i*30, 100, 30));
                    }
                }
                if(floors == 2){
                    g.drawImage(boss, j*100, i*30, 300, 180, null);

                    if(!isBoss){
                        bossRec.add(new Rectangle(j*100, i*30, 300, 180));
                    }
                }
                if(floors == 3) {
                    g.drawImage(chain, j*100, i*30, 100, 30, null);
                    if(!brick){
                       unbreakableblock.add(new Rectangle(j*100, i*30, 100, 30));
                    }
                }
                if(floors == 4) {
                    g.drawImage(redblock, j*100, i*30, 100, 30, null);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*100, i*30, 100, 30);
                    if(!brick){
                       slowblock.add(new Rectangle(j*100, i*30, 100, 30));
                    }
                }
                if(floors == 5) {
                    g.drawImage(greenblock, j*100, i*30, 100, 30, null);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*100, i*30, 100, 30);
                    if(!brick){
                       speedblock.add(new Rectangle(j*100, i*30, 100, 30));
                    }
                }
                if(floors == 6) {
                    g.drawImage(life, j*100, i*30, 100, 30, null);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*100, i*30, 100, 30);
                    if(!brick){
                       lifeblock.add(new Rectangle(j*100, i*30, 100, 30));
                    }
                }
                if(floors == 7) {
                    g.drawImage(spike, j*100, i*30, 100, 30, null);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*100, i*30, 100, 30);
                    if(!brick){
                       spikeblock.add(new Rectangle(j*100, i*30, 100, 30));
                    }
                }
                if(floors == 8) {
                    g.drawImage(randomblock, j*100, i*30, 100, 30, null);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*100, i*30, 100, 30);
                    if(!brick){
                       questionblock.add(new Rectangle(j*100, i*30, 100, 30));
                    }
                }
            }
        }
        brick = true;
        isBoss = true;
    }
}
