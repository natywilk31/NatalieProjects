/**
 * This class is not a full application -- it is an
 * extended JPanel.  (You cannot run it.)  When you
 * use this class in a JFrame, the methods here will be
 * automatically called when the GUI needs to be redrawn
 * or resized.
 * 
 * We're gonna draw a soccer ball, which can be moved around, and then a stationary goal. 
 * 
 * @author Peter Jensen and Natalie Wilkins
 * @version Fall 2022
 */
package assignment04;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class SoccerBall extends JPanel
{
	/**
	 * Overrides the default painting in a JPanel.  I choose
	 * below how I want the panel to paint itself.  This method
	 * is automatically called (we don't have to call it)
	 * whenever the panel needs to be drawn on the screen.
	 * 
	 */
    public void paint (Graphics g)
    {
    	//fill background color: a blue sky and a green field.
    	
    	g.setColor(Color.CYAN);
    	g.fillRect (0, 0, 800, 400);
    	
    	g.setColor(Color.GREEN);
    	g.fillRect(0, 400, 800, 800);
    	
    	//draw some funny lookin clouds
    	for (int i = 0; i < 7; i++)
    		drawCloud(g);

    	// Draw one soccer ball.
    	drawSoccerBall(g, 100, 100);
    	
    	
    	// Draw some soccer balls.
    	
    	for (int i = 0; i < 50; i++)
    	    drawRandomSoccerBall(g);
    	// draw our goal and our sun so hopefully people can tell what it's supposed to be
    	drawGoal(g);
    	drawSun(g);

    }
    

    public void drawSoccerBall (Graphics g, int x, int y)
    {
    	g.setColor(Color.WHITE);
    	g.fillOval(x, y, 40, 40);
    	g.setColor(Color.BLACK);
    	g.fillOval(x+5, y+5, 10, 10);
    	g.fillOval(x+15, y , 10, 10);
    	g.fillOval(x+25, y+5, 10, 10);
    	g.fillOval(x+5, y+17, 10, 10);
    	g.fillOval(x+15, y + 12 , 10, 10);
    	g.fillOval(x+25, y+17, 10, 10);
    	g.fillOval(x+15, y + 25 , 10, 10);

    }
    
    /**
     * Draws a soccer ball randomly in an 800x800 range.
     * 
     */
    public void drawRandomSoccerBall (Graphics g)
    {
    	int x = (int) (Math.random() * 800);
		int y = (int) (Math.random() * 800);
		drawSoccerBall(g,x,y);
		if (x >=320 && x<=480) {
			if (y >=320 && y <= 400) {
				g.drawString("GOALLLLL!!!!!!!!", 360, 400); // this will draw the string GOALL if there is an upper left coordinate of any ball inside the goal. 
					
				}
					
			}
		}
    
    // this method constructs our goal.
    public void drawGoal(Graphics g) {
    	g.setColor(Color.BLACK);
    	g.fillRect(320, 320, 5, 80);
    	g.fillRect(480,320 , 5, 80);
    	g.fillRect(320, 320, 160, 5);   	
    }
    
    // lets draw a sun!
    public void drawSun(Graphics g) {
    	g.setColor(Color.YELLOW);
    	g.fillOval(10, 10, 70, 70);
    }
    
    //and some clouds
    public void drawCloud(Graphics g) {
    	int x = (int) (Math.random() * 800);
		int y = (int) (Math.random() * 200);
		g.setColor(Color.GRAY); //we probably could do some loops somehow here but that's difficult and this code isn't that messy, makes sense
		g.fillOval(x, y, 40, 40);
		g.fillOval(x+20, y, 40, 40);
		g.fillOval(x+40, y, 40, 40);
    }
    
    /**
     * Overrides the same-named function in the JPanel
     * so that we can specify our own size (when the
     * GUI system asks for this panel's size).
     */
    public Dimension getMinimumSize()
    {
    	return new Dimension(800, 800);
    }
    
    /**
     * Overrides the same-named function in the JPanel
     * so that we can specify our own size (when the
     * GUI system asks for this panel's size).
     */
    public Dimension getMaximumSize()
    {
    	return new Dimension(800, 800);
    }
    
    /**
     * Overrides the same-named function in the JPanel
     * so that we can specify our own size (when the
     * GUI system asks for this panel's size).
     */
    public Dimension getPreferredSize()
    {
    	return new Dimension(800, 800);
    }
}
