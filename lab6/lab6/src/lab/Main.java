package lab;

import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.swing.JFrame;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;

import java.awt.*;

public class Main extends JFrame
{
    public Canvas3D myCanvas3D;

    public Main() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);

        simpUniv.getViewingPlatform().setNominalViewingTransform();

        createSceneGraph(simpUniv);

        addLight(simpUniv);

        OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE));
        simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);

        setTitle("Sample");
        setSize(1280, 720);
        getContentPane().add("Center", myCanvas3D);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new Main();
    }

    public void createSceneGraph(SimpleUniverse su)
    {

        var f = new ObjectFile(ObjectFile.RESIZE);
        Scene windowScene = null;
        try
        {
            windowScene = f.load("models/scrat.obj");
        }
        catch (Exception e)
        {
            System.out.println("File loading failed:" + e);
            return;
        }

        var scaling = new Transform3D();
        scaling.setScale(1.0/6);
        var tfScrat = new Transform3D();
        tfScrat.rotX(Math.PI/3);
        var tfScrat2 = new Transform3D();
        tfScrat2.rotY(Math.PI);
        tfScrat.mul(tfScrat2);
        tfScrat.mul(scaling);
        var tgScrat = new TransformGroup(tfScrat);
        var sceneGroup = new TransformGroup();


        var widowNamedObjects = windowScene.getNamedObjects();
        var enumer = widowNamedObjects.keys();
        String name;
        while (enumer.hasMoreElements())
        {
            name = (String) enumer.nextElement();
            System.out.println("Name: "+name);
        }

        var left_hand = (Shape3D) widowNamedObjects.get("left_hand");
        var right_hand = (Shape3D) widowNamedObjects.get("right_hand");
        var tale = (Shape3D) widowNamedObjects.get("tale");
        var nut = (Shape3D) widowNamedObjects.get("nut");

        var body = (Shape3D) widowNamedObjects.get("body");

        var texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        var transformGroup = new TransformGroup();
        transformGroup.addChild(body.cloneTree());
        var bodyT = new Transform3D();
        bodyT.rotY(Math.PI/3);
        transformGroup.setTransform(bodyT);


        var left_handGr = new TransformGroup();
        var right_handGr = new TransformGroup();
        var taleGr = new TransformGroup();
        var nutGr = new TransformGroup();

        var leftHandAdjGr = new TransformGroup();
        var rightHandAdjGr = new TransformGroup();

        var nutT = new Transform3D();
        nutT.setTranslation(new Vector3f(-2, 0, 0));
        nutGr.setTransform(nutT);

        var leftHandT = new Transform3D();
        leftHandT.rotX(Math.PI / 4);
        leftHandAdjGr.setTransform(leftHandT);

        leftHandAdjGr.addChild(left_handGr);

        var rightHandT = new Transform3D();
        rightHandT.rotX(-Math.PI / 4);
        rightHandAdjGr.setTransform(rightHandT);

        rightHandAdjGr.addChild(right_handGr);

        var canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);
        var t = new TextureLoader("models/iceage.jpg", canvas);

        var background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere boundsBack = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(boundsBack);
        var back = new BranchGroup();
        back.addChild(background);
        su.addBranchGraph(back);

        left_handGr.addChild(left_hand.cloneTree());
        right_handGr.addChild(right_hand.cloneTree());
        taleGr.addChild(tale.cloneTree());
        nutGr.addChild(nut.cloneTree());


        var bounds = new BoundingSphere(new Point3d(120.0,250.0,100.0),Double.MAX_VALUE);
        var theScene = new BranchGroup();
        var tCrawl = new Transform3D();
        var tCrawl1 = new Transform3D();
        tCrawl.rotY(-90D);
        tCrawl1.rotX(-90D);
        long crawlTime = 15000;
        var crawlAlpha = new Alpha(1,
                Alpha.INCREASING_ENABLE,
                0,
                0, crawlTime,0,0,0,0,0);
        float crawlDistance = 3.0f;
        var posICrawl = new PositionInterpolator(crawlAlpha,
                sceneGroup,tCrawl, -9.0f, crawlDistance);

        long crawlTime1 = 30000;
        var crawlAlpha1 = new Alpha(1,
                Alpha.INCREASING_ENABLE,
                3000,
                0, crawlTime1,0,0,0,0,0);
        float crawlDistance1 = 15.0f;
        var posICrawl1 = new PositionInterpolator(crawlAlpha1,
                sceneGroup,tCrawl1, -9.0f, crawlDistance1);


        var bs = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        posICrawl.setSchedulingBounds(bs);
        posICrawl1.setSchedulingBounds(bs);
        sceneGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        sceneGroup.addChild(posICrawl);


        int timeStart = 500;
        int timeRotationHour = 500;

        var leftHandRotationAxis = new Transform3D();
        //leftHandRotationAxis.rotZ(Math.PI / 2);
        var leftHandRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        var leftHandRotation = new RotationInterpolator(leftHandRotationAlpha, left_handGr,
                leftHandRotationAxis, (float) Math.PI / 4, 0.0f);
        leftHandRotation.setSchedulingBounds(bounds);



        var rightHandRotationAxis = new Transform3D();
        //rightHandRotationAxis.rotZ(Math.PI / 2);
        var rightHandRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        var rightHandRotation = new RotationInterpolator(rightHandRotationAlpha, right_handGr,
                rightHandRotationAxis, -(float) Math.PI / 4, 0.0f);
        rightHandRotation.setSchedulingBounds(bounds);


        var taleRotationAxis = new Transform3D();
        var taleRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        var taleRotation = new RotationInterpolator(taleRotationAlpha, taleGr,
                taleRotationAxis, (float) Math.PI / 4, 0.0f);
        taleRotation.setSchedulingBounds(bounds);





        left_handGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        right_handGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        taleGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        nutGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        left_handGr.addChild(leftHandRotation);
        right_handGr.addChild(rightHandRotation);
        taleGr.addChild(taleRotation);


        sceneGroup.addChild(transformGroup);
        transformGroup.addChild(leftHandAdjGr);
        transformGroup.addChild(rightHandAdjGr);
        transformGroup.addChild(taleGr);
        transformGroup.addChild(nutGr);
        tgScrat.addChild(sceneGroup);
        theScene.addChild(tgScrat);

        var bg = new Background(new Color3f(1f,1f,1f));

        bg.setApplicationBounds(bounds);
        theScene.addChild(bg);
        theScene.compile();

        su.addBranchGraph(theScene);
    }


    public void addLight(SimpleUniverse su)
    {
        var bgLight = new BranchGroup();
        var bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        var lightColour1 = new Color3f(0.5f,1.0f,1.0f);
        var lightDir1 = new Vector3f(-1.0f,0.0f,-0.5f);
        var light1 = new DirectionalLight(lightColour1, lightDir1);
        light1.setInfluencingBounds(bounds);
        bgLight.addChild(light1);
        su.addBranchGraph(bgLight);
    }
}