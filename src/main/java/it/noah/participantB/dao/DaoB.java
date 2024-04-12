/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.noah.participantB.dao;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import it.noah.common.ObjectB;
import it.noah.sagacqrs.dao.DaoUtils;
import it.noah.participantB.entity.EntityB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jboss.logging.Logger;

/**
 *
 * @author NATCRI
 */
@ApplicationScoped
public class DaoB {

    @Inject
    Logger log;

    @Inject
    PgPool client;

    @Inject
    DaoUtils daoUtils;

    public Uni<EntityB> createB(String uuid, OffsetDateTime expire, EntityB b) {
        return client.withTransaction(conn -> {
            return daoUtils.persist(log, conn, uuid, expire, b);
        });
    }

    public Uni<List<EntityB>> deleteB(String uuid, OffsetDateTime expire, Long entityAId) {
        return client.withTransaction(conn -> daoUtils.getResultList(log, conn, EntityB.FIND_ALL_BY_ENTITY_A,
                new Object[]{entityAId}, new EntityB())
                .chain(items -> daoUtils.remove(log, conn, uuid, expire, items))
        );
    }

    public Uni<List<EntityB>> updateB(String uuid, OffsetDateTime expire, Long entityAId, String titlePrefix) {
        return client.withTransaction(conn -> daoUtils.getResultList(log, conn, EntityB.FIND_ALL_BY_ENTITY_A,
                new Object[]{entityAId}, new EntityB())
                .chain(olds -> {
                    Map<Long, EntityB> news = new HashMap<>();
                    for (EntityB oldB : olds) {
                        EntityB newB = oldB.clone();
                        newB.setTitle(titlePrefix + newB.getTitle());
                        newB.setCreatetime(OffsetDateTime.now());
                        news.put(oldB.getId(), newB);
                    }
                    return daoUtils.merge(log, conn, uuid, expire, olds, news);
                }));
    }

    public Uni<List<ObjectB>> getAll(List<Long> ids) {
        return client.withConnection(conn -> daoUtils.getResultList(log, conn, EntityB.FIND_ALL_BY_ENTITIES_A,
                new Object[]{ids.toArray(new Long[ids.size()])}, new EntityB())
                .map(items -> items.stream().map(item -> item.toObjectB()).collect(Collectors.toList())));
    }

    public Uni<List<ObjectB>> getByAId(Long id) {
        return client.withConnection(conn -> daoUtils.getResultList(log, conn, EntityB.FIND_ALL_BY_ENTITY_A,
                new Object[]{id}, new EntityB())
                .map(items -> items.stream().map(item -> item.toObjectB()).collect(Collectors.toList())));
    }

}
