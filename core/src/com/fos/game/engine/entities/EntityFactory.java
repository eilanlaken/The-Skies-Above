package com.fos.game.engine.entities;

public class EntityFactory {

    public static Entity create(final EntityContainer entityContainer, final Enum layer, final Object... components) {
        Entity entity = new Entity();
        entity.attachComponents(components);
        entity.layer = 0b000001 << layer.ordinal();
        entityContainer.addEntity(entity);
        return entity;
    }

}
