package org.banreservas.interview.paises;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class PaisService {

    @Inject
    @Channel("REFRESH_PAISES")
    Emitter<String> refreshPaisesEmitter;

    @Inject
    @Channel("PAISES_UPDATED")
    Emitter<List<Pais>> paisesUpdatedEmitter;

    @RestClient
    PaisRestClient paisRestClient;

    @Inject
    PaisRepository paisRepository;

    @Startup
    void refreshPaises() {
        refreshPaisesEmitter.send("REFRESH_PAISES");
    }

    @Incoming("REFRESH_PAISES")
    @Transactional
    public void refreshPaisesHandler(String ignored) throws ExecutionException, InterruptedException {
        var paisesMap = paisesToMap(fetchPaises());

        var result = new ArrayList<>(paisRepository.refreshPaisesByCCN3(paisesMap));
        if (!result.isEmpty()) {
            paisesUpdatedEmitter.send(result);
        }
    }

    public Pais getPaisByCCN3(String ccn3) {
        var paises = paisRepository.list("codeCCA3", ccn3);
        if (paises.isEmpty()) {
            return null;
        }
        return paises.getFirst();
    }


    private HashMap<String, Pais> paisesToMap(List<Pais> paises) {
        var result = new HashMap<String, Pais>();
        for (var pais : paises) {
            result.put(pais.codeCCA3, pais);
        }
        return result;
    }

    private List<Pais> fetchPaises() throws ExecutionException, InterruptedException {
        var fetchAllPaisesDtos = paisRestClient.fetchAll("name,cca3,demonyms");
        var result = new ArrayList<Pais>();
        for (var fetchAllPaisesDto : fetchAllPaisesDtos) {
            var pais = new Pais();
            pais.name = fetchAllPaisesDto.name.common;
            pais.codeCCA3 = fetchAllPaisesDto.cca3;
            pais.demonymF = fetchAllPaisesDto.demonyms.eng.f;
            pais.demonymM = fetchAllPaisesDto.demonyms.eng.m;
            result.add(pais);
        }
        return result;
    }

}
