package com.fos.game.engine.ecs.systems.renderer_old.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;

public class Physics2DDebugRenderer extends Box2DDebugRenderer {

    public Physics2DDebugRenderer() {
        super(true, true, false, true, true, true);
    }

    public void begin() {
        super.renderer.begin(ShapeRenderer.ShapeType.Line);
    }

    public void setProjectionMatrix(final Matrix4 matrix) {
        renderer.setProjectionMatrix(matrix);
    }

    public void drawBody(final Body body) {
        super.renderBody(body);
    }

    public void drawJoint(final Joint joint) {
        Body bodyA = joint.getBodyA();
        Body bodyB = joint.getBodyB();
        Transform xf1 = bodyA.getTransform();
        Transform xf2 = bodyB.getTransform();

        Vector2 x1 = xf1.getPosition();
        Vector2 x2 = xf2.getPosition();
        Vector2 p1 = joint.getAnchorA();
        Vector2 p2 = joint.getAnchorB();

        if (joint.getType() == JointDef.JointType.DistanceJoint) {
            drawSegment(p1, p2, JOINT_COLOR);
        } else if (joint.getType() == JointDef.JointType.PulleyJoint) {
            PulleyJoint pulley = (PulleyJoint)joint;
            Vector2 s1 = pulley.getGroundAnchorA();
            Vector2 s2 = pulley.getGroundAnchorB();
            drawSegment(s1, p1, JOINT_COLOR);
            drawSegment(s2, p2, JOINT_COLOR);
            drawSegment(s1, s2, JOINT_COLOR);
        } else if (joint.getType() == JointDef.JointType.MouseJoint) {
            drawSegment(joint.getAnchorA(), joint.getAnchorB(), JOINT_COLOR);
        } else {
            drawSegment(x1, p1, JOINT_COLOR);
            drawSegment(p1, p2, JOINT_COLOR);
            drawSegment(x2, p2, JOINT_COLOR);
        }
    }

    private void drawSegment (Vector2 x1, Vector2 x2, Color color) {
        renderer.setColor(color);
        renderer.line(x1.x, x1.y, x2.x, x2.y);
    }

    public void end() {
        super.renderer.end();
    }

}
