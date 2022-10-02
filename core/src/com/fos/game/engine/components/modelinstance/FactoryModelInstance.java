package com.fos.game.engine.components.modelinstance;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.fos.game.engine.components.base.Factory;
import com.fos.game.engine.components.camera.ComponentCamera;
import com.fos.game.engine.files.assets.GameAssetManager;
import com.fos.game.engine.files.serialization.JsonConverter;
import com.fos.game.engine.systems.renderer.materials.base.Material;
import com.fos.game.engine.systems.renderer.materials.base.MaterialInstanceFactory;

import java.util.HashMap;

public class FactoryModelInstance extends Factory {

    private final MaterialInstanceFactory materialInstanceFactory;

    public FactoryModelInstance(final GameAssetManager assetManager, final JsonConverter jsonConverter) {
        super(assetManager, jsonConverter);
        this.materialInstanceFactory = new MaterialInstanceFactory();
    }

    // TODO: implement. For now - support single nodeId model instance
    public ComponentModelInstance create(final String modelFilePath, final String nodeId, final String[] textureAtlasNames) {
        final Model model = assetManager.get(modelFilePath, Model.class);
        final Node node = model.getNode(nodeId);
        TextureAtlas[] atlases = new TextureAtlas[textureAtlasNames.length];
        for (int i = 0; i < atlases.length; i++) {
            atlases[i] = assetManager.get(textureAtlasNames[i], TextureAtlas.class);
        }
        Matrix4 transform = node.localTransform == null ? new Matrix4() : node.localTransform;
        final HashMap<String, Material> materialsMap = materialInstanceFactory.createMaterialsMap(model, nodeId, atlases);
        return null;
    }

    @Deprecated
    private static final MaterialInstanceFactory materialInstanceFactory_dep = new MaterialInstanceFactory();

    @Deprecated
    private static SimpleModelInstance createSimpleModelInstance(final TexturedModel_old texturedModelOld, final String nodeId, final ComponentCamera camera) {
        final Model model = texturedModelOld.model;
        final Node node = model.getNode(nodeId);
        Matrix4 transform = node.localTransform == null ? new Matrix4() : node.localTransform;
        final HashMap<String, Material> materialsMap = materialInstanceFactory_dep.createMaterialsMap(camera);
        return new SimpleModelInstance(model, materialsMap, transform, nodeId);
    }

    @Deprecated
    private static SimpleModelInstance createSimpleModelInstance(final TexturedModel_old texturedModelOld) {
        final Model model = texturedModelOld.model;
        final Node node = model.nodes.first();
        final String nodeId = node.id;
        Matrix4 transform = node.localTransform == null ? new Matrix4() : node.localTransform;
        final HashMap<String, Material> materialsMap = materialInstanceFactory_dep.createMaterialsMap(texturedModelOld, nodeId);
        return new SimpleModelInstance(model, materialsMap, transform, nodeId);
    }

    @Deprecated
    private static SimpleModelInstance createSimpleModelInstance(final TexturedModel_old texturedModelOld, final String nodeId) {
        final Model model = texturedModelOld.model;
        final Node node = model.getNode(nodeId);
        Matrix4 transform = node.localTransform == null ? new Matrix4() : node.localTransform;
        final HashMap<String, Material> materialsMap = materialInstanceFactory_dep.createMaterialsMap(texturedModelOld, nodeId);
        return new SimpleModelInstance(model, materialsMap, transform, nodeId);
    }

    @Deprecated
    private static RiggedModelInstance createRiggedModelInstance(final TexturedModel_old texturedModelOld, final String riggedModelNodeId, final String rigHierarchyId) {
        final Model model = texturedModelOld.model;
        final Node riggedModelNode = model.getNode(riggedModelNodeId);
        Matrix4 transform = riggedModelNode.localTransform == null ? new Matrix4() : riggedModelNode.localTransform;
        final HashMap<String, Material> materialsMap = materialInstanceFactory_dep.createMaterialsMap(texturedModelOld, riggedModelNodeId);
        return new RiggedModelInstance(model, materialsMap, transform, riggedModelNodeId, rigHierarchyId);
    }

    @Deprecated
    public static ComponentModelInstance create(final TexturedModel_old texturedModelOld, final String nodeId, final ComponentCamera camera) {
        return new ComponentModelInstance(createSimpleModelInstance(texturedModelOld, nodeId, camera));
    }

    @Deprecated
    public static ComponentModelInstance create(final TexturedModel_old texturedModelOld, final String riggedModelNodeId, final String rigHierarchyId) {
        return new ComponentModelInstance(createRiggedModelInstance(texturedModelOld, riggedModelNodeId, rigHierarchyId));
    }

    @Deprecated
    public static ComponentModelInstance create(final TexturedModel_old texturedModelOld, final String nodeId) {
        return new ComponentModelInstance(createSimpleModelInstance(texturedModelOld, nodeId));
    }

    @Deprecated
    public static ComponentModelInstance create(final TexturedModel_old texturedModelOld) {
        return new ComponentModelInstance(createSimpleModelInstance(texturedModelOld));
    }
}
