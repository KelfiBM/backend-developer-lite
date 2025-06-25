package org.banreservas.interview.paises;

import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

@QuarkusComponentTest
public class PaisServiceTest {
    @Inject
    PaisService paisService;

    @InjectMock
    PaisRepository paisRepository;

    @Test
    public void testGetPaisByCCN3_whenExistingCCN3_returnPais() {
        var foundPaises = new ArrayList<Pais>();
        foundPaises.add(new Pais());
        Mockito.when(paisRepository.list(Mockito.anyString(), Mockito.anyString())).thenReturn(foundPaises);

        String ccn3 = "DOM";

        Assertions.assertNotNull(paisService.getPaisByCCN3(ccn3));
    }

    @Test
    public void testGetPaisByCCN3_whenNonExistingCCN3_returnNull() {
        var foundPaises = new ArrayList<Pais>();
        Mockito.when(paisRepository.list(Mockito.anyString(), Mockito.anyString())).thenReturn(foundPaises);

        String ccn3 = "DOM";

        Assertions.assertNull(paisService.getPaisByCCN3(ccn3));
    }
}
