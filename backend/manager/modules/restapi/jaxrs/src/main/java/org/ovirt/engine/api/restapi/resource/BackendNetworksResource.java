package org.ovirt.engine.api.restapi.resource;


import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.Network;
import org.ovirt.engine.api.model.Networks;
import org.ovirt.engine.api.resource.NetworkResource;
import org.ovirt.engine.api.resource.NetworksResource;

import org.ovirt.engine.core.common.action.AddNetworkStoragePoolParameters;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.businessentities.network;
import org.ovirt.engine.core.common.businessentities.storage_pool;
import org.ovirt.engine.core.common.interfaces.SearchType;
import org.ovirt.engine.core.common.queries.GetAllNetworkQueryParamenters;
import org.ovirt.engine.core.common.queries.VdcQueryParametersBase;
import org.ovirt.engine.core.common.queries.VdcQueryType;
import org.ovirt.engine.core.compat.Guid;

public class BackendNetworksResource
    extends AbstractBackendNetworksResource
    implements NetworksResource {

    public BackendNetworksResource() {
        super(VdcQueryType.GetAllNetworks,
              VdcActionType.AddNetwork,
              VdcActionType.RemoveNetwork);
    }

    @Override
    public Response add(Network network) {
        validateParameters(network, getRequiredAddFields());
        network entity = map(network);
        AddNetworkStoragePoolParameters params = getActionParameters(network, entity);
        return performCreation(addAction,
                               params,
                               new DataCenterNetworkIdResolver(network.getName(), params.getStoragePoolId().toString()));
    }

    @Override
    public Networks list() {
        Networks networks = mapCollection(getBackendCollection(queryType, getQueryParameters()));
        for (Network network : networks.getNetworks()) {
            network.setDisplay(null);
        }
        return networks;
    }

    @Override
    protected VdcQueryParametersBase getQueryParameters() {
        return new GetAllNetworkQueryParamenters(Guid.Empty);
    }

    @Override
    protected AddNetworkStoragePoolParameters getActionParameters(Network network, network entity) {
        if (namedDataCenter(network)) {
            entity.setstorage_pool_id(getDataCenterId(network));
        }
        return new AddNetworkStoragePoolParameters(entity.getstorage_pool_id().getValue(), entity);
    }

    protected String[] getRequiredAddFields() {
        return new String[] { "name", "dataCenter.name|id" };
    }

    @Override
    @SingleEntityResource
    public NetworkResource getNetworkSubResource(String id) {
        return inject(new BackendNetworkResource(id, this));
    }

    protected boolean namedDataCenter(Network network) {
        return network != null && network.isSetDataCenter() && network.getDataCenter().isSetName() && !network.getDataCenter().isSetId();
    }

    protected Guid getDataCenterId(Network network) {
        return getEntity(storage_pool.class,
                         SearchType.StoragePool,
                         "Datacenter: name=" + network.getDataCenter().getName()).getId();
    }
}
