/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.noah.participantB.facade;

import io.smallrye.mutiny.Uni;
import it.noah.common.ObjectB;
import it.noah.participantB.dao.DaoB;
import it.noah.participantB.entity.EntityB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.OffsetDateTime;
import java.util.List;

/**
 *
 * @author NATCRI
 */
@ApplicationScoped
public class FacadeB {

    @Inject
    DaoB dao;

    public Uni<EntityB> createB(String uuid, OffsetDateTime expire, Long entityAId) {
        EntityB b = new EntityB();
        b.setEntityA(entityAId);
        b.setTitle("#" + uuid.substring(0, 8) + " B");
        b.setWeight(3);
        b.setEnabled(true);
        b.setCreatetime(OffsetDateTime.now());
        return dao.createB(uuid, expire, b);
    }

    public Uni<List<EntityB>> deleteB(String uuid, OffsetDateTime expire, Long entityAId) {
        return dao.deleteB(uuid, expire, entityAId);
    }

    public Uni<List<EntityB>> updateB(String uuid, OffsetDateTime expire, Long entityAId, String titlePrefix) {
        return dao.updateB(uuid, expire, entityAId, titlePrefix);
    }

    public Uni<List<ObjectB>> getAll(List<Long> ids) {
        return dao.getAll(ids);
    }

    public Uni<List<ObjectB>> getByAId(Long id) {
        return dao.getByAId(id);
    }
}
