package com.fos.game.engine.renderer.materials.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.utils.Array;
import com.fos.game.engine.components.camera.ComponentCamera;
import com.fos.game.engine.components.modelinstance.TexturedModel;
import com.fos.game.engine.renderer.materials.instances.*;

import java.util.HashMap;

// the libgdx Material class has an id. This is how it is loaded into the asset manager.
// the first two characters of the id is used to encode the matching game material.
public final class MaterialInstanceFactory {

    public HashMap<String, Material> createMaterialsMap(final TexturedModel texturedModel,
                                                        final String nodeId) {
        final Model model = texturedModel.model;
        final TextureAtlas[] spriteSheets = texturedModel.spriteSheets;
        final Node node = nodeId == null ? model.nodes.first() : model.getNode(nodeId);
        final Array<NodePart> parts = node.parts;
        final HashMap<String, Material> materialsMap = new HashMap<>();
        for (NodePart part : parts) {
            final com.badlogic.gdx.graphics.g3d.Material gdxMaterial = part.material;
            final Material material = createFOSMaterial(model, gdxMaterial, spriteSheets);
            materialsMap.put(gdxMaterial.id, material);
        }
        return materialsMap;
    }

    private Material createFOSMaterial(final Model model, final com.badlogic.gdx.graphics.g3d.Material gdxMaterial, final TextureAtlas[] textureAtlases) {
        final Class encodedMaterialClass = MaterialUtils.getEncodedMaterialClass(gdxMaterial);
        if (encodedMaterialClass == DiffuseMapMaterialInstance.class) return createDiffuseMapMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == AlbedoNormalMapsMaterialInstance.class) return createAlbedoNormalMapsMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == AlbedoMapMaterialInstance.class) return createAlbedoMapMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == AlbedoNormalMetallicRoughnessAOMapsMaterialInstance.class) return createAlbedoNormalMetallicRoughnessAOMapsMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == DiffuseNormalSpecularRoughnessParallaxMapsMaterialInstance.class) return createDiffuseNormalSpecularRoughnessParallaxMapsMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == PlainColorMaterialInstance.class) return createPlainColorMaterialInstance(gdxMaterial);
        if (encodedMaterialClass == DiffuseNormalMapsMaterialInstance.class) return createDiffuseNormalMapsMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == PlainDiffuseMapMaterialInstance.class) return createPlainDiffuseMapMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == PlainDiffuseBloomMapsMaterialInstance.class) return createPlainDiffuseBloomMapsMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == AnimatedSkyBoxSideMaterialInstance.class) return createSkyBoxSideMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == DiffuseNormalSpecularRoughnessMapsMaterialInstance.class) return createDiffuseNormalSpecularRoughnessMapsMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == GlowingColorMaterialInstance.class) return createGlowingColorMaterialInstance(gdxMaterial);
        if (encodedMaterialClass == AnimatedDiffuseBloomMapsMaterialInstance.class) return createAnimatedDiffuseBloomMapsMaterialsInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == DiffuseNormalParallaxMapsMaterialInstance.class) return createDiffuseNormalParallaxMapsMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == DiffuseNormalBloomMapsMaterialInstance.class) return createDiffuseNormalBloomMapsMaterialInstance(gdxMaterial, textureAtlases[0]);
        if (encodedMaterialClass == UniformColorMaterialInstance.class) return createUniformColorMaterialInstance(gdxMaterial);
        if (encodedMaterialClass == UniformColorBloomMaterialInstance.class) return createUniformColorBloomMaterialInstance(gdxMaterial);
        if (encodedMaterialClass == VolumetricLightSourceMaterialInstance.class) return createVolumetricLightSourceMaterialInstance(gdxMaterial);

        if (encodedMaterialClass == BlendAlbedoMapsMaterialInstance.class) return createBlendAlbedoMapsMaterialInstance(model, textureAtlases);
        // deprecated
        if (encodedMaterialClass == AnimatedDiffuseMapMaterialInstance.class) return createAnimatedDiffuseMapMaterialInstance(gdxMaterial, textureAtlases[0]);
        return null;
    }

    protected static DiffuseNormalBloomMapsMaterialInstance createDiffuseNormalBloomMapsMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseRegionName = source.id.substring(3);
        String normalRegionName = diffuseRegionName + "NM";
        String bloomRegionName = diffuseRegionName + "BM";

        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess);
        ColorAttribute specular = (ColorAttribute) source.get(ColorAttribute.Specular);

        final float shineDamper = 1f / shininess.value;
        final float reflectivity = (specular.color.r + specular.color.g + specular.color.b) / 3f;
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;

        return new DiffuseNormalBloomMapsMaterialInstance(textureAtlas, shineDamper, reflectivity, ambientComponent, diffuseRegionName, normalRegionName, bloomRegionName);
    }

    protected static UniformColorMaterialInstance createUniformColorMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source) {
        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess);
        ColorAttribute specular = (ColorAttribute) source.get(ColorAttribute.Specular);

        final float shineDamper = 1f / shininess.value;
        final float reflectivity = (specular.color.r + specular.color.g + specular.color.b) / 3f;
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;

        ColorAttribute diffuse = (ColorAttribute) source.get(ColorAttribute.Diffuse);
        final Color diffuseColor = diffuse.color;
        return new UniformColorMaterialInstance(diffuseColor.r, diffuseColor.g, diffuseColor.b, shineDamper, reflectivity, ambientComponent);
    }

    protected static UniformColorBloomMaterialInstance createUniformColorBloomMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source) {
        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess);
        ColorAttribute specular = (ColorAttribute) source.get(ColorAttribute.Specular);
        ColorAttribute emissive = (ColorAttribute) source.get(ColorAttribute.Emissive);

        final float shineDamper = 1f / shininess.value;
        final float reflectivity = (specular.color.r + specular.color.g + specular.color.b) / 3f;
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;
        final float intensity = emissive.color.r;

        ColorAttribute diffuse = (ColorAttribute) source.get(ColorAttribute.Diffuse);
        final Color diffuseColor = diffuse.color;
        return new UniformColorBloomMaterialInstance(diffuseColor.r, diffuseColor.g, diffuseColor.b, intensity, shineDamper, reflectivity, ambientComponent);
    }

    protected static DiffuseNormalParallaxMapsMaterialInstance createDiffuseNormalParallaxMapsMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseRegionName = source.id.substring(3);
        String normalRegionName = diffuseRegionName + "NM";
        String parallaxRegionName = diffuseRegionName + "PM";

        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess);
        ColorAttribute specular = (ColorAttribute) source.get(ColorAttribute.Specular);

        final float shineDamper = 1f / shininess.value;
        final float reflectivity = (specular.color.r + specular.color.g + specular.color.b) / 3f;
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;

        return new DiffuseNormalParallaxMapsMaterialInstance(textureAtlas, shineDamper, reflectivity, ambientComponent, diffuseRegionName, normalRegionName, parallaxRegionName);
    }

    protected static AnimatedDiffuseBloomMapsMaterialInstance createAnimatedDiffuseBloomMapsMaterialsInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseRegionName = source.id.substring(3);
        String bloomRegionName = diffuseRegionName + "BM";

        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess); // <- the frame duration is encoded in the shininess value
        final float frameDuration = shininess.value;

        return new AnimatedDiffuseBloomMapsMaterialInstance(textureAtlas, diffuseRegionName, bloomRegionName, frameDuration, Animation.PlayMode.LOOP);
    }

    protected static PlainColorMaterialInstance createPlainColorMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material gdxMaterialSource) {
        ColorAttribute diffuse = (ColorAttribute) gdxMaterialSource.get(ColorAttribute.Diffuse);
        final Color diffuseColor = diffuse.color;
        return new PlainColorMaterialInstance(diffuseColor.r, diffuseColor.g, diffuseColor.b);
    }

    protected static VolumetricLightSourceMaterialInstance createVolumetricLightSourceMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material gdxMaterialSource) {
        ColorAttribute diffuse = (ColorAttribute) gdxMaterialSource.get(ColorAttribute.Diffuse);
        final Color diffuseColor = diffuse.color;
        return new VolumetricLightSourceMaterialInstance(diffuseColor.r, diffuseColor.g, diffuseColor.b);
    }

    protected static DiffuseNormalMapsMaterialInstance createDiffuseNormalMapsMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseRegionName = source.id.substring(3);
        String normalRegionName = diffuseRegionName + "NM";

        ColorAttribute diffuse = (ColorAttribute) source.get(ColorAttribute.Diffuse);
        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess);
        ColorAttribute specular = (ColorAttribute) source.get(ColorAttribute.Specular);

        final float shineDamper = 1f / shininess.value;
        final float reflectivity = (specular.color.r + specular.color.g + specular.color.b) / 3f;
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;

        return new DiffuseNormalMapsMaterialInstance(textureAtlas, shineDamper, reflectivity, ambientComponent, diffuseRegionName, normalRegionName);
    }

    protected static PlainDiffuseMapMaterialInstance createPlainDiffuseMapMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseRegionName = source.id.substring(3);
        return new PlainDiffuseMapMaterialInstance(textureAtlas, diffuseRegionName);
    }

    protected static PlainDiffuseBloomMapsMaterialInstance createPlainDiffuseBloomMapsMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseRegionName = source.id.substring(3);
        String bloomRegionName = diffuseRegionName + "BM";
        ColorAttribute emissive = (ColorAttribute) source.get(ColorAttribute.Emissive);
        final float intensity = emissive.color.r;
        return new PlainDiffuseBloomMapsMaterialInstance(textureAtlas, diffuseRegionName, bloomRegionName, intensity);
    }

    protected static AlbedoNormalMapsMaterialInstance createAlbedoNormalMapsMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String albedoRegionName = source.id.substring(3);
        String normalRegionName = albedoRegionName + "NM";

        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess);
        ColorAttribute specular = (ColorAttribute) source.get(ColorAttribute.Specular);

        final float roughness = 1f / shininess.value;
        final float metallic = (specular.color.r + specular.color.g + specular.color.b) / 3f;
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;

        return new AlbedoNormalMapsMaterialInstance(textureAtlas, roughness, metallic, ambientComponent, albedoRegionName, normalRegionName);
    }

    protected static AlbedoMapMaterialInstance createAlbedoMapMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String albedoRegionName = source.id.substring(3);

        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess);
        ColorAttribute specular = (ColorAttribute) source.get(ColorAttribute.Specular);

        final float roughness = 1f / shininess.value;
        final float metallic = (specular.color.r + specular.color.g + specular.color.b) / 3f;
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;

        return new AlbedoMapMaterialInstance(textureAtlas, roughness, metallic, ambientComponent, albedoRegionName);
    }

    protected static DiffuseMapMaterialInstance createDiffuseMapMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseRegionName = source.id.substring(3);

        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess);
        ColorAttribute specular = (ColorAttribute) source.get(ColorAttribute.Specular);
        final float shineDamper = 1f / shininess.value;
        final float reflectivity = (specular.color.r + specular.color.g + specular.color.b) / 3f;
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;

        return new DiffuseMapMaterialInstance(textureAtlas, diffuseRegionName, shineDamper, reflectivity, ambientComponent);
    }

    protected static AnimatedDiffuseMapMaterialInstance createAnimatedDiffuseMapMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseRegionName = source.id.substring(3);

        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess);
        ColorAttribute specular = (ColorAttribute) source.get(ColorAttribute.Specular);

        final float shineDamper = 1f / shininess.value;
        final float reflectivity = (specular.color.r + specular.color.g + specular.color.b) / 3f;
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;

        return new AnimatedDiffuseMapMaterialInstance(textureAtlas, diffuseRegionName, shineDamper, reflectivity, ambientComponent, 1, Animation.PlayMode.LOOP);
    }

    protected static DiffuseNormalSpecularRoughnessMapsMaterialInstance createDiffuseNormalSpecularRoughnessMapsMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseRegionName = source.id.substring(3);
        String normalRegionName = diffuseRegionName + "NM";
        String specularRegionName = diffuseRegionName + "SM";
        String roughnessRegionName = diffuseRegionName + "RM";
        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;
        return new DiffuseNormalSpecularRoughnessMapsMaterialInstance(textureAtlas, ambientComponent, diffuseRegionName, normalRegionName, specularRegionName, roughnessRegionName);
    }

    protected static DiffuseNormalSpecularRoughnessParallaxMapsMaterialInstance createDiffuseNormalSpecularRoughnessParallaxMapsMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseRegionName = source.id.substring(3);
        String normalRegionName = diffuseRegionName + "NM";
        String specularRegionName = diffuseRegionName + "SM";
        String roughnessRegionName = diffuseRegionName + "RM";
        String parallaxRegionName = diffuseRegionName + "PM";

        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;
        return new DiffuseNormalSpecularRoughnessParallaxMapsMaterialInstance(textureAtlas, ambientComponent, diffuseRegionName, normalRegionName, specularRegionName, roughnessRegionName, parallaxRegionName);
    }

    protected static AlbedoNormalMetallicRoughnessAOMapsMaterialInstance createAlbedoNormalMetallicRoughnessAOMapsMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String albedoRegionName = source.id.substring(3);
        String normalRegionName = albedoRegionName + "NM";
        String metallicRoughnessAORegionName = albedoRegionName + "XM";

        ColorAttribute ambient = (ColorAttribute) source.get(ColorAttribute.Ambient);
        final float ambientComponent = (ambient.color.r + ambient.color.g + ambient.color.b) / 3f;
        return new AlbedoNormalMetallicRoughnessAOMapsMaterialInstance(textureAtlas, ambientComponent, albedoRegionName, normalRegionName, metallicRoughnessAORegionName);
    }

    @Deprecated
    protected static AnimatedSkyBoxSideMaterialInstance createSkyBoxSideMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source, final TextureAtlas textureAtlas) {
        String diffuseAnimationName = source.id.substring(3);

        FloatAttribute shininess = (FloatAttribute) source.get(FloatAttribute.Shininess); // <- the frame duration is encoded in the shininess value
        final float frameDuration = shininess.value;

        return new AnimatedSkyBoxSideMaterialInstance(textureAtlas, diffuseAnimationName, frameDuration);
    }

    protected static GlowingColorMaterialInstance createGlowingColorMaterialInstance(final com.badlogic.gdx.graphics.g3d.Material source) {
        ColorAttribute diffuse = (ColorAttribute) source.get(ColorAttribute.Diffuse);
        final Color diffuseColor = diffuse.color;
        return new GlowingColorMaterialInstance(diffuseColor.r, diffuseColor.g, diffuseColor.b);
    }

    protected static BlendAlbedoMapsMaterialInstance createBlendAlbedoMapsMaterialInstance(final Model model, final TextureAtlas ...atlases) {
        // first texture is the blend map.
        BasicMaterialMap[] basicMaterialMaps = new BasicMaterialMap[atlases.length - 1];
        for (int i = 1; i < atlases.length; i++) {
            TextureAtlas atlas = atlases[i];
            final String materialName = "MF_" + atlas.getRegions().first().name;
            final com.badlogic.gdx.graphics.g3d.Material material = model.getMaterial(materialName);
            ColorAttribute ambientComponent = (ColorAttribute) material.get(ColorAttribute.Ambient);
            FloatAttribute shininess = (FloatAttribute) material.get(FloatAttribute.Shininess);
            ColorAttribute specular = (ColorAttribute) material.get(ColorAttribute.Specular);

            final Texture texture = atlas.getTextures().first();
            final float roughness = 1f / shininess.value;
            final float metallic = specular.color.r;
            final float ambient = ambientComponent.color.r;
            basicMaterialMaps[i-1] = new BasicMaterialMap(texture, roughness, metallic, ambient);
        }
        Texture blendMap = atlases[0].getTextures().first();
        return new BlendAlbedoMapsMaterialInstance(blendMap, basicMaterialMaps);
    }

    public HashMap<String, Material> createMaterialsMap(final ComponentCamera camera) {
        final HashMap<String, Material> materialsMap = new HashMap<>();
        final PlainTextureMaterialInstance plainTextureMaterialInstance = new PlainTextureMaterialInstance(camera.getRenderTargetTexture());
        materialsMap.put(String.valueOf(plainTextureMaterialInstance.hashCode()), plainTextureMaterialInstance);
        return materialsMap;
    }

}
