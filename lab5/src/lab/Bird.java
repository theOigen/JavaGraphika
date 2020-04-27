package lab;

import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;

public class  Bird extends JFrame {
	private static Canvas3D canvas;
	private static SimpleUniverse universe;
	private static BranchGroup root;
	
	private static TransformGroup bird;
	
    public Bird() throws IOException {
    	configureWindow();
        configureCanvas();
        configureUniverse();
        
        root = new BranchGroup();

        addImageBackground();
        
        addDirectionalLightToUniverse();
        addAmbientLightToUniverse();
        
        ChangeViewAngle();
        

        
        bird = getBirdGroup();
        root.addChild(bird);
        
        root.compile();
        universe.addBranchGraph(root);
    }
    
    private void configureWindow() {
        setTitle("Mini Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void configureCanvas() {
    	canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);
    }
    
    private void configureUniverse() {
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }
    
    private void addImageBackground() {
        var t = new TextureLoader("source_folder/winodws_xp.jpg", canvas);
        var background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(bounds);
        root.addChild(background);
    }
    
    private void addDirectionalLightToUniverse() {
	    var bounds = new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 1000000.0);



	    var light = new DirectionalLight(new Color3f(1, 1, 1), new Vector3f(-1, -1, -1));
	    light.setInfluencingBounds(bounds);

	    root.addChild(light);
	}
    
    private void addAmbientLightToUniverse() {
        var light = new AmbientLight(new Color3f(1, 1, 1));
        light.setInfluencingBounds(new BoundingSphere());
        root.addChild(light);
    }
    
    private void ChangeViewAngle() {
        var vp = universe.getViewingPlatform();
        var vpGroup = vp.getMultiTransformGroup().getTransformGroup(0);
        var vpTranslation = new Transform3D();
        vpTranslation.setTranslation(new Vector3f(0, 0, 6));
        vpGroup.setTransform(vpTranslation);
    }

    private TransformGroup getBirdGroup() throws IOException {
    	var shape = getModelShape3D("bird", "source_folder/bird/bird.obj");

    	var transform3D = new Transform3D();
    	transform3D.setScale(new Vector3d(0.4, 0.4, 0.4));
        var transform3D2 = new Transform3D();
        transform3D2.rotY(Math.PI/2);
        var group = getModelGroup(shape);
        group.removeAllChildren();
        var tg = new TransformGroup();
        tg.setTransform(transform3D2);
        group.setTransform(transform3D);
        group.addChild(tg);
        tg.addChild(shape);
        
        return group;
    }
    
    private TransformGroup getModelGroup(Shape3D shape) {
    	var group = new TransformGroup();
    	group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    	group.addChild(shape);
    	return group;
    }
    
	private Shape3D getModelShape3D(String name, String path) throws IOException {
    	var scene = getSceneFromFile(path);
    	Map<String, Shape3D> map = scene.getNamedObjects();
    	printModelElementsList(map);
    	var shape = map.get(name);
    	scene.getSceneGroup().removeChild(shape);
    	return shape;
    }
    
    private Scene getSceneFromFile(String path) throws IOException {
        var file = new ObjectFile(ObjectFile.RESIZE);

        return file.load(new FileReader(path));
    }
    
    private void printModelElementsList(Map<String, Shape3D> map) {
    	for (String name : map.keySet()) {
    		System.out.println("Name: " + name);
    	}
    }

    public static void main(String[] args) {
        try {
            var window = new Bird();
            var birdMovement = new BirdAnimation(bird);
            canvas.addKeyListener(birdMovement);
            window.setVisible(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
