/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.noah.participantB;

import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import it.noah.common.ABCEventDetails;
import it.noah.common.ABEventDetails;
import it.noah.common.ObjectB;
import it.noah.common.SingleABCEventDetails;
import it.noah.sagacqrs.json.Jsoner;
import it.noah.sagacqrs.participant.ParticipantConfigurator;
import it.noah.sagacqrs.participant.interfaces.IParticipantServer;
import it.noah.participantB.entity.EntityB;
import it.noah.participantB.facade.FacadeB;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jboss.logging.Logger;

/**
 *
 * @author NATCRI
 */
@Path("/b")
@Startup
@ApplicationScoped
public class ResourceB implements IParticipantServer {

    @Inject
    ParticipantConfigurator configurator;

    @Inject
    Logger log;

    @Inject
    PgPool dbPool;

    @Inject
    Jsoner jsoner;

    @Inject
    FacadeB facade;

    @PostConstruct
    void init() throws Throwable {
        configurator.init(log, dbPool, EntityB.class);
    }

    @Override
    public ParticipantConfigurator getConfigurator() {
        return configurator;
    }

    @Override
    public Uni<Object> execute(@QueryParam(value = "uuid") String uuid, @QueryParam(value = "expire") OffsetDateTime expire,
            @QueryParam(value = "operation") String operation, Object data) {
        switch (operation) {
            case "CREATE_ABC":
                ABCEventDetails details = jsoner.getObject(data, ABCEventDetails.class);
                return facade.createB(uuid, expire, details.getEntityAId()).replaceWith(details);
            case "DELETE_ABC":
                ABCEventDetails details2 = jsoner.getObject(data, ABCEventDetails.class);
                return facade.deleteB(uuid, expire, details2.getEntityAId()).replaceWith(details2);
            case "UPDATE_ABC":
                ABCEventDetails details3 = jsoner.getObject(data, ABCEventDetails.class);
                return facade.updateB(uuid, expire, details3.getEntityAId(), details3.getTitlePrefix()).replaceWith(details3);
            case "READ_ALL_AB":
                ABEventDetails details4 = jsoner.getObject(data, ABEventDetails.class);
                List<Long> ids = details4.getItems().stream().map(c -> c.getA().getId()).collect(Collectors.toList());
                return facade.getAll(ids).map(objects -> {
                    Map<Long, List<ObjectB>> entitiesB = new HashMap<>();
                    objects.stream().forEach(e -> {
                        if (!entitiesB.containsKey(e.getEntityA())) {
                            entitiesB.put(e.getEntityA(), new ArrayList<>());
                        }
                        entitiesB.get(e.getEntityA()).add(e);
                    });
                    details4.getItems().stream().filter(e -> entitiesB.containsKey(e.getA().getId()))
                            .forEach(e -> e.setBItems((List<ObjectB>) entitiesB.get(e.getA().getId())));
                    return details4;
                });
            case "READ_ONE_ABC":
                SingleABCEventDetails details5 = jsoner.getObject(data, SingleABCEventDetails.class);
                return facade.getByAId(details5.getEntityAId()).map(objects -> {
                    details5.setBItems(objects);
                    return details5;
                });
            default:
                return throwNoOperationFound(operation);
        }
    }
}
