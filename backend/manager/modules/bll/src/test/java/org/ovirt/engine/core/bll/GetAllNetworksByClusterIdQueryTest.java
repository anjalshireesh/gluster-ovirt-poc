package org.ovirt.engine.core.bll;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.ovirt.engine.core.common.businessentities.network;
import org.ovirt.engine.core.common.queries.VdsGroupQueryParamenters;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.dao.NetworkDAO;

public class GetAllNetworksByClusterIdQueryTest extends AbstractUserQueryTest<VdsGroupQueryParamenters, GetAllNetworksByClusterIdQuery<? extends VdsGroupQueryParamenters>> {

    /** Tests that {@link GetAllNetworksByClusterIdQuery#executeQueryCommand()} delegated to the correct DAOs, using mock objects */
    @Test
    public void testExecuteQueryCommand() {
        Guid clusterID = new Guid(UUID.randomUUID());

        network networkMock = mock(network.class);

        NetworkDAO networkDAOMock = mock(NetworkDAO.class);
        when(networkDAOMock.getAllForCluster(clusterID, getUser().getUserId(), getQueryParameters().isFiltered())).thenReturn(Collections.singletonList(networkMock));

        DbFacade dbFacade = getDbFacadeMockInstance();
        when(dbFacade.getNetworkDAO()).thenReturn(networkDAOMock);

        when(getQueryParameters().getVdsGroupId()).thenReturn(clusterID);
        getQuery().executeQueryCommand();

        @SuppressWarnings("unchecked")
        List<network> result = (List<network>) getQuery().getQueryReturnValue().getReturnValue();
        assertEquals("Wrong number of networks in result", 1, result.size());
        assertEquals("Wrong network in result", networkMock, result.get(0));
    }
}