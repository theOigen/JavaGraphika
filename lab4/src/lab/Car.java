package lab;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Car implements ActionListener {
    private float upperEyeLimit = 15.0f;
    private float lowerEyeLimit = 5.0f;
    private float farthestEyeLimit = 28.0f;
    private float nearestEyeLimit = 22.0f;

    private TransformGroup treeTransformGroup;
    private TransformGroup viewingTransformGroup;
    private Transform3D treeTransform3D = new Transform3D();
    private Transform3D viewingTransform = new Transform3D();
    private float angle = 0;
    private float eyeHeight;
    private float eyeDistance;
    private boolean descend = true;
    private boolean approaching = true;

    public static void main(String[] args) {
        new Car();
    }

    private Car() {
        Timer timer = new Timer(50, this);
        SimpleUniverse universe = new SimpleUniverse();

        viewingTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
        universe.addBranchGraph(createSceneGraph());

        eyeHeight = upperEyeLimit;
        eyeDistance = farthestEyeLimit;
        timer.start();
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();

        treeTransformGroup = new TransformGroup();
        treeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        buildCar();
        objRoot.addChild(treeTransformGroup);

        Background background = new Background(new Color3f(0.9f, 0.9f, 0.9f)); // white color
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
        background.setApplicationBounds(sphere);
        objRoot.addChild(background);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        Color3f light1Color = new Color3f(1.0f, 1, 1);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        return objRoot;
    }


    private TransformGroup buildTG() {
        return buildTG(new Vector3f(), new Transform3D());
    }

    private TransformGroup buildTG(Vector3f translation) {
        return buildTG(translation, new Transform3D());
    }

    private TransformGroup buildTG(Vector3f translation, Transform3D rotation){
        Transform3D transform = new Transform3D();
        TransformGroup transformG = new TransformGroup();
        transform.setTranslation(translation);
        transform.mul(rotation, transform);
        transformG.setTransform(transform);
        return transformG;
    }

    private void buildCar() {

        //body
        Box body1 = new Box(4, 3, 3, Utils.getBodyAppearence());
        TransformGroup body1TG = buildTG();
        body1TG.addChild(body1);

        Box body2 = new Box(2, 3, 1.5f, Utils.getBodyAppearence());
        TransformGroup body2TG = buildTG(new Vector3f(6, 0,-1.5f ));
        body2TG.addChild(body2);
        body1TG.addChild(body2TG);

        Box glass = new Box(2, 2.5f, 0.8f, Utils.getGlassAppearence());
        TransformGroup glassTG = buildTG(new Vector3f(2.2f, 0, 1.3f));
        glassTG.addChild(glass);
        body1TG.addChild(glassTG);

        Cylinder wheel1 = new Cylinder(1.5f, 0.8f, Utils.getWheelAppearence());
        TransformGroup wheel1TG = buildTG(new Vector3f(-2.5f, 3, -3));
        wheel1TG.addChild(wheel1);
        body1TG.addChild(wheel1TG);

        Cylinder wheel2 = new Cylinder(1.5f, 0.8f, Utils.getWheelAppearence());
        TransformGroup wheel2TG = buildTG(new Vector3f(-2.5f, -3, -3));
        wheel2TG.addChild(wheel2);
        body1TG.addChild(wheel2TG);

        Cylinder wheel3 = new Cylinder(1.5f, 0.8f, Utils.getWheelAppearence());
        TransformGroup wheel3TG = buildTG(new Vector3f(5, 3, -3));
        wheel3TG.addChild(wheel3);
        body1TG.addChild(wheel3TG);

        Cylinder wheel4 = new Cylinder(1.5f, 0.8f, Utils.getWheelAppearence());
        TransformGroup wheel4TG = buildTG(new Vector3f(5, -3, -3));
        wheel4TG.addChild(wheel4);
        body1TG.addChild(wheel4TG);

        Box radiator = new Box(2, 2.5f, 0.8f, Utils.getRadiatorAppearence());
        TransformGroup radiatorTG = buildTG(new Vector3f(6.1f, 0, -1));
        radiatorTG.addChild(radiator);
        body1TG.addChild(radiatorTG);

        Box windows1 = new Box(1, 3.1f, 1, Utils.getGlassAppearence());
        TransformGroup windows1TG = buildTG(new Vector3f(1.6f, 0, 1.3f));
        windows1TG.addChild(windows1);
        body1TG.addChild(windows1TG);

        Box windows2 = new Box(1, 3.1f, 1, Utils.getGlassAppearence());
        TransformGroup windows2TG = buildTG(new Vector3f(-1.6f, 0, 1.3f));
        windows2TG.addChild(windows2);
        body1TG.addChild(windows2TG);

        treeTransformGroup.addChild(body1TG);

    }

    // ActionListener interface
    @Override
    public void actionPerformed(ActionEvent e) {
        float delta = 0.03f;

        // rotation of the castle
        treeTransform3D.rotZ(angle);
        treeTransformGroup.setTransform(treeTransform3D);
        angle += delta;

        // change of the camera position up and down within defined limits
        if (eyeHeight > upperEyeLimit){
            descend = true;
        }else if(eyeHeight < lowerEyeLimit){
            descend = false;
        }
        if (descend){
            eyeHeight -= delta;
        }else{
            eyeHeight += delta;
        }

        // change camera distance to the scene
        if (eyeDistance > farthestEyeLimit){
            approaching = true;
        }else if(eyeDistance < nearestEyeLimit){
            approaching = false;
        }
        if (approaching){
            eyeDistance -= delta;
        }else{
            eyeDistance += delta;
        }

        Point3d eye = new Point3d(eyeDistance, eyeDistance, eyeHeight); // spectator's eye
        Point3d center = new Point3d(.0f, .0f ,0.1f); // sight target
        Vector3d up = new Vector3d(.0f, .0f, 1.0f);; // the camera frustum
        viewingTransform.lookAt(eye, center, up);
        viewingTransform.invert();
        viewingTransformGroup.setTransform(viewingTransform);
    }
}
