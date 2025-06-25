package org.banreservas.interview.paises;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.lang.reflect.Field;
import java.util.*;

@ApplicationScoped
public class PaisRepository implements PanacheRepository<Pais> {

    public List<Pais> refreshPaisesByCCN3(HashMap<String, Pais> paisesMap) {
        var result = new ArrayList<Pais>();
        var paises = listAll();

        result.addAll(deprecatePaisesByCCN3(paises, paisesMap.keySet()));
        result.addAll(updatePaisesByCCN3(paises, paisesMap));
        result.addAll(AddPaisesByCCN3(paises, paisesMap.values()));

        return result;
    }

    private List<Pais> deprecatePaisesByCCN3(List<Pais> currentPaises, Set<String> ccn3Codes) {
        var result = new ArrayList<Pais>();
        for (var currentPais : currentPaises) {
            if (!ccn3Codes.contains(currentPais.codeCCA3)) {
                currentPais.deprecated = true;
                result.add(currentPais);
            }
        }
        return result;
    }

    private List<Pais> updatePaisesByCCN3(List<Pais> currentPaises, HashMap<String, Pais> newPaises) {
        var result = new ArrayList<Pais>();
        for (var currentPais : currentPaises) {
            if (!newPaises.containsKey(currentPais.codeCCA3)) {
                continue;
            }

            var newPais = newPaises.get(currentPais.codeCCA3);
            if (!areEqual(currentPais, newPais)) {
                currentPais.name = newPais.name;
                currentPais.deprecated = false;
                currentPais.demonymF = newPais.demonymF;
                currentPais.demonymM = newPais.demonymM;
                result.add(currentPais);
            }
        }
        return result;
    }

    private List<Pais> AddPaisesByCCN3(List<Pais> currentPaises, Collection<Pais> newPaises) {
        var currentCCA3Codes = new HashSet<>(currentPaises.stream().map(pais -> pais.codeCCA3).toList());
        var result = new ArrayList<Pais>();
        for (var newPais : newPaises) {
            if (!currentCCA3Codes.contains(newPais.codeCCA3)) {
                persist(newPais);
                result.add(newPais);
            }
        }
        return result;
    }

    private static boolean areEqual(Pais pais1, Pais pais2) {
        for (Field f : Pais.class.getDeclaredFields()) {
            try {
                if (!f.get(pais1).equals(f.get(pais2)))
                    return false;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                Log.warn(e.getMessage());
            }
        }
        return true;
    }
}
