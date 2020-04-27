package lab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

public class BirdAnimation implements ActionListener, KeyListener {
    private TransformGroup bird;
    private Transform3D transform3D = new Transform3D();

    private float x = 0;
    private float y = 0;
    
    private boolean arUp = false;
    private boolean arDown = false;
    private boolean arLeft = false;
    private boolean arRight = false;

    public BirdAnimation(TransformGroup boat) {
        this.bird = boat;
        this.bird.getTransform(this.transform3D);
                
        Timer timer = new Timer(20, this);
        timer.start();
    }

    private void Move() {
  	  	if (arUp) {
  	  		y += 0.02f;
  	  	}
  	  	if (arDown) {
			y -= 0.02f;
	  	}
        if (arLeft) {
			x -= 0.02f;
        }
        if (arRight) {
        	x += 0.02f;
        }
		transform3D.setTranslation(new Vector3f(x, y, 0));


        bird.setTransform(transform3D);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	Move();
    }
    
    @Override
    public void keyPressed(KeyEvent ev) {
    	switch (ev.getKeyCode()) {
	    	case 38: arUp = true; break;
	    	case 40: arDown = true; break;
	    	case 37: arLeft = true; break;
	    	case 39: arRight = true; break;

		}
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    	
    }

    @Override
    public void keyReleased(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case 38: arUp = false; break;
            case 40: arDown = false; break;
            case 37: arLeft = false; break;
            case 39: arRight = false; break;

        }
    }
}
