package com.fos.game.engine.renderer.system;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.fos.game.engine.components.modelinstance.ModelInstance;
import com.fos.game.engine.components.modelinstance.RiggedModelInstance;
import com.fos.game.engine.components.modelinstance.SimpleModelInstance;
import com.fos.game.engine.renderer.materials.base.Material;
import com.fos.game.engine.renderer.shaders.base.ShadingMethod;
import com.fos.game.engine.renderer.shaders.pbr.PBRAlbedoMapShader;
import com.fos.game.engine.renderer.shaders.pbr.PBRAlbedoNormalMapsShader;
import com.fos.game.engine.renderer.shaders.pbr.PBRAlbedoNormalMetallicRoughnessAOMapsShader;
import com.fos.game.engine.renderer.shaders.pbr.PBRBlendAlbedoMapsShader;
import com.fos.game.engine.renderer.shaders.plain.*;
import com.fos.game.engine.renderer.shaders.rigged.RiggedUniformColorCombinedShader;
import com.fos.game.engine.renderer.shaders.simple.*;

public class ShaderProvider extends DefaultShaderProvider {

    // simple
    private final DiffuseMapShader diffuseMapShader;
    private final DiffuseNormalMapsShader diffuseNormalMapsShader;
    private final DiffuseNormalSpecularRoughnessMapsShader diffuseNormalSpecularRoughnessMapsShader;
    private final DiffuseNormalSpecularRoughnessParallaxMapsShader diffuseNormalSpecularRoughnessParallaxMapsShader;
    private final DiffuseBloomMapsShader diffuseBloomMapsShader;
    private final DiffuseNormalParallaxMapsShader diffuseNormalParallaxMapsShader;
    private final UniformColorShader uniformColorShader;
    private final VolumetricLightSourceCombinedShader volumetricLightSourceCombinedShader;

    // plain
    private final PlainColorShader plainColorDetailedShader;
    private final PlainColorBloomShader plainColorBloomShader;
    private final PlainSkyBoxCombinedShader plainSkyBoxCombinedShader;
    private final PlainDiffuseMapShader plainDiffuseMapShader;
    private final PlainDiffuseBloomMapsShader plainDiffuseBloomMapsShader;

    // rigged entities
    private final RiggedUniformColorCombinedShader riggedUniformColorCombinedShader;

    // pbr
    private final PBRAlbedoNormalMetallicRoughnessAOMapsShader pbrAlbedoNormalMetallicRoughnessAOMapsShader;
    private final PBRAlbedoNormalMapsShader pbrAlbedoNormalMapsShader;
    private final PBRBlendAlbedoMapsShader pbrBlendAlbedoMapsShader;
    private final PBRAlbedoMapShader pbrAlbedoMapShader;

    public ShaderProvider() {
        // init shaders
        plainColorDetailedShader = new PlainColorShader();
        plainDiffuseMapShader = new PlainDiffuseMapShader();
        plainDiffuseBloomMapsShader = new PlainDiffuseBloomMapsShader();
        diffuseMapShader = new DiffuseMapShader();
        diffuseNormalMapsShader = new DiffuseNormalMapsShader();
        plainSkyBoxCombinedShader = new PlainSkyBoxCombinedShader();
        diffuseNormalSpecularRoughnessMapsShader = new DiffuseNormalSpecularRoughnessMapsShader();
        diffuseNormalSpecularRoughnessParallaxMapsShader = new DiffuseNormalSpecularRoughnessParallaxMapsShader();
        plainColorBloomShader = new PlainColorBloomShader();
        diffuseBloomMapsShader = new DiffuseBloomMapsShader();
        diffuseNormalParallaxMapsShader = new DiffuseNormalParallaxMapsShader();
        uniformColorShader = new UniformColorShader();
        riggedUniformColorCombinedShader = new RiggedUniformColorCombinedShader();
        volumetricLightSourceCombinedShader = null;//new VolumetricLightSourceCombinedShader();
        pbrAlbedoNormalMetallicRoughnessAOMapsShader = new PBRAlbedoNormalMetallicRoughnessAOMapsShader();
        pbrAlbedoNormalMapsShader = new PBRAlbedoNormalMapsShader();
        pbrBlendAlbedoMapsShader = new PBRBlendAlbedoMapsShader();
        pbrAlbedoMapShader = new PBRAlbedoMapShader();
    }

    @Override
    public Shader getShader(Renderable renderable) {
        final ModelInstance modelInstance = (ModelInstance) renderable.userData;
        final Material material = modelInstance.getMaterial(renderable);

        Shader shader = null;

        if (modelInstance instanceof SimpleModelInstance) {
            shader = getSolidBodyShader(material);
        }
        else if (modelInstance instanceof RiggedModelInstance) {
            shader = getRiggedBodyShader(material);
        }

        return (shader != null) ? shader : super.getShader(renderable);
    }

    private Shader getSolidBodyShader(final Material material) {
        if (material.getShadingMethod() == ShadingMethod.PBRAlbedoMap) return pbrAlbedoMapShader;
        if (material.getShadingMethod() == ShadingMethod.PBRAlbedoNormalMaps) return pbrAlbedoNormalMapsShader;
        if (material.getShadingMethod() == ShadingMethod.PBRBlendAlbedoMaps) return pbrBlendAlbedoMapsShader;
        if (material.getShadingMethod() == ShadingMethod.PBRAlbedoNormalMetallicRoughnessAOMaps) return pbrAlbedoNormalMetallicRoughnessAOMapsShader;
        if (material.getShadingMethod() == ShadingMethod.PlainColor) return plainColorDetailedShader;
        if (material.getShadingMethod() == ShadingMethod.PlainDiffuseMap) return plainDiffuseMapShader;
        if (material.getShadingMethod() == ShadingMethod.PlainDiffuseBloomMaps) return plainDiffuseBloomMapsShader;
        if (material.getShadingMethod() == ShadingMethod.AnimatedDiffuseMap) return diffuseMapShader;
        if (material.getShadingMethod() == ShadingMethod.DiffuseMap) return diffuseMapShader;
        if (material.getShadingMethod() == ShadingMethod.DiffuseNormalMaps) return diffuseNormalMapsShader;
        if (material.getShadingMethod() == ShadingMethod.DiffuseNormalSpecularRoughnessMaps) return diffuseNormalSpecularRoughnessMapsShader;
        if (material.getShadingMethod() == ShadingMethod.DiffuseNormalSpecularRoughnessParallaxMaps) return diffuseNormalSpecularRoughnessParallaxMapsShader;
        if (material.getShadingMethod() == ShadingMethod.UniformColorGlow) return plainColorBloomShader;
        if (material.getShadingMethod() == ShadingMethod.DiffuseBloomMaps) return diffuseBloomMapsShader;
        if (material.getShadingMethod() == ShadingMethod.DiffuseNormalParallaxMaps) return diffuseNormalParallaxMapsShader;
        if (material.getShadingMethod() == ShadingMethod.UniformColor) return uniformColorShader;
        if (material.getShadingMethod() == ShadingMethod.PlainSkyBox) return plainSkyBoxCombinedShader;
        if (material.getShadingMethod() == ShadingMethod.VolumetricLightSourceColor) return volumetricLightSourceCombinedShader;
        return null;
    }

    private Shader getRiggedBodyShader(final Material material) {
        if (material.getShadingMethod() == ShadingMethod.UniformColor) return riggedUniformColorCombinedShader;
        return null;
    }

    @Override
    public void dispose() {
        super.dispose();
        // dispose of simple shaders
        plainColorDetailedShader.dispose();
        diffuseMapShader.dispose();
        diffuseNormalMapsShader.dispose();
        plainSkyBoxCombinedShader.dispose();
        diffuseNormalSpecularRoughnessMapsShader.dispose();
        diffuseNormalSpecularRoughnessParallaxMapsShader.dispose();
        plainColorBloomShader.dispose();
        diffuseBloomMapsShader.dispose();
        diffuseNormalParallaxMapsShader.dispose();
        uniformColorShader.dispose();
        volumetricLightSourceCombinedShader.dispose();
        // dispose of rigged shaders
        // dispose of pbr shaders
        pbrAlbedoNormalMetallicRoughnessAOMapsShader.dispose();
        pbrAlbedoNormalMapsShader.dispose();
    }

}
