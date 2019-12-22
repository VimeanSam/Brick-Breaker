/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BrickBreaker;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author James Andrews
 * @author Vimean Sam
 */
public class Ball {
    int x;
    int y;
    int ball_angle;
    BufferedImage ball;

    public Ball(){
        this.ball = ImageLoader.loadImage("/Assets/ball.png");
    }

    public void updatePosition(int x, int y, int angle){
        this.x = x;
        this.y = y;
        this.ball_angle = angle;
    }

    public void render(Graphics2D gr){
        AffineTransform transform = AffineTransform.getTranslateInstance(x, y);
        transform.rotate(Math.toRadians(ball_angle), ball.getWidth()/2, ball.getHeight()/2);
        gr.drawImage(ball, transform, null);
    }
}
