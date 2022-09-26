package com.fos.game.engine.components.modelinstance;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.fos.game.engine.components.camera.ComponentCamera;
import com.fos.game.engine.renderer.materials.base.FOSMaterial;
import com.fos.game.engine.renderer.materials.base.MaterialInstanceFactory;

import java.util.HashMap;

public class FactoryModelInstance {

    private static final MaterialInstanceFactory materialInstanceFactory = new MaterialInstanceFactory();

    private static SimpleModelInstance createSimpleModelInstance(final TexturedModel texturedModel, final String nodeId, final ComponentCamera camera) {
        final Model model = texturedModel.model;
        final Node node = model.getNode(nodeId);
        Matrix4 transform = node.localTransform == null ? new Matrix4() : node.localTransform;
        final HashMap<String, FOSMaterial> materialsMap = materialInstanceFactory.createMaterialsMap(camera);
        return new SimpleModelInstance(model, materialsMap, transform, nodeId);
    }

    private static SimpleModelInstance createSimpleModelInstance(final TexturedModel texturedModel) {
        final Model model = texturedModel.model;
        final Node node = model.nodes.first();
        final String nodeId = node.id;
        Matrix4 transform = node.localTransform == null ? new Matrix4() : node.localTransform;
        final HashMap<String, FOSMaterial> materialsMap = materialInstanceFactory.createMaterialsMap(texturedModel, nodeId);
        return new SimpleModelInstance(model, materialsMap, transform, nodeId);
    }

    private static SimpleModelInstance createSimpleModelInstance(final TexturedModel texturedModel, final String nodeId) {
        final Model model = texturedModel.model;
        final Node node = model.getNode(nodeId);
        Matrix4 transform = node.localTransform == null ? new Matrix4() : node.localTransform;
        final HashMap<String, FOSMaterial> materialsMap = materialInstanceFactory.createMaterialsMap(texturedModel, nodeId);
        return new SimpleModelInstance(model, materialsMap, transform, nodeId);
    }

    private static RiggedModelInstance createRiggedModelInstance(final TexturedModel texturedModel, final String riggedModelNodeId, final String rigHierarchyId) {
        final Model model = texturedModel.model;
        final Node riggedModelNode = model.getNode(riggedModelNodeId);
        Matrix4 transform = riggedModelNode.localTransform == null ? new Matrix4() : riggedModelNode.localTransform;
        final HashMap<String, FOSMaterial> materialsMap = materialInstanceFactory.createMaterialsMap(texturedModel, riggedModelNodeId);
        return new RiggedModelInstance(model, materialsMap, transform, riggedModelNodeId, rigHierarchyId);
    }

    public static ComponentModelInstance create(final TexturedModel texturedModel, final String nodeId, final ComponentCamera camera) {
        return new ComponentModelInstance(createSimpleModelInstance(texturedModel, nodeId, camera));
    }

    public static ComponentModelInstance create(final TexturedModel texturedModel, final String riggedModelNodeId, final String rigHierarchyId) {
        return new ComponentModelInstance(createRiggedModelInstance(texturedModel, riggedModelNodeId, rigHierarchyId));
    }

    public static ComponentModelInstance create(final TexturedModel texturedModel, final String nodeId) {
        return new ComponentModelInstance(createSimpleModelInstance(texturedModel, nodeId));
    }

    public static ComponentModelInstance create(final TexturedModel texturedModel) {
        return new ComponentModelInstance(createSimpleModelInstance(texturedModel));
    }
}
