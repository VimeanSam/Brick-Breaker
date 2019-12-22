/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BrickBreaker;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author James Andrews
 * @author Vimean Sam
 */

public class Boss {
    int HP;
    public int i = 0;

    public BufferedImage getBoss() {
      ArrayList<String> bosses = new ArrayList<String>();

      bosses.add("lebron");
      bosses.add("curry");
      bosses.add("mj");

      String boss = "/Assets/" + bosses.get(i) + ".png";
      return ImageLoader.loadImage(boss);
    }

    void newBoss() {
      i++;
    }

    void resetHP() {
      if(i == 1){
        this.HP = 150;
      }else if(i == 2){
        this.HP = 500;
      }
    }

    public void setHP(int value) {
      this.HP = value;
    }
    public int getHP() {
      return this.HP;
    }
}
