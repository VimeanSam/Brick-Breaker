/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BrickBreaker;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author James Andrews
 * @author Vimean Sam
 */
public class Player {
    BufferedImage bar;
    int lives;
    int player_x, player_y;

    public Player(){
        this.bar = ImageLoader.loadImage("/Assets/bar.png");
    }

    public void updatePosition(int x, int y){
        this.player_x = x;
        this.player_y = y;
    }
    
    public void setLives(int amount){
        this.lives = amount;
    }
    
    public int getLives(){
        return this.lives;
    }
    
    public void render(Graphics2D gr){
        gr.drawImage(bar, player_x, player_y, null);
    }
}
