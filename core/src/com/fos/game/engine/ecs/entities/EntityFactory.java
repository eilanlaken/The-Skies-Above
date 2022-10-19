package com.fos.game.engine.ecs.entities;

public class EntityFactory {

    public static Entity create(final EntityManager entityManager, final Enum layer, final Object... components) {
        Entity entity = new Entity();
        entity.attachComponents(components);
        entity.layer = 0b000001 << layer.ordinal();
        entityManager.addEntity(entity);
        return entity;
    }

}
