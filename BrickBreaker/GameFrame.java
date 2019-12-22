package BrickBreaker;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author James Andrews
 * @author Vimean Sam
 */
public class GameFrame extends JFrame {
    private int width, height;
    private int highscore;
    Canvas canvas;

    public GameFrame(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    public void setScore(int score){
         setTitle("Brick Breaker: "+"Score: "+Integer.toString(score));
    }
    
    public int getScore(){
        return this.highscore;
    }
    
    public void createWindow(){
      setSize(width, height);
      setResizable(false);
      setVisible(true);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      this.canvas = new Canvas();

      this.canvas.setMinimumSize(new Dimension(width, height));
      this.canvas.setPreferredSize(new Dimension(width, height));
      this.canvas.setMaximumSize(new Dimension(width, height));
      this.canvas.setFocusable(false);
      add(this.canvas);

      pack();
    }

    public Canvas returnCanvas(){
      return this.canvas;
    }
}
