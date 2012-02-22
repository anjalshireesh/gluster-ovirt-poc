package org.ovirt.engine.core.dao;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.Snapshot;
import org.ovirt.engine.core.common.businessentities.Snapshot.SnapshotStatus;
import org.ovirt.engine.core.common.businessentities.Snapshot.SnapshotType;
import org.ovirt.engine.core.compat.Guid;

/**
 * The snapshot DAO is used to retrieve and manipulate {@link Snapshot} instances in the DB.
 */
public interface SnapshotDao extends GenericDao<Snapshot, Guid>, StatusAwareDao<Guid, SnapshotStatus> {

    /**
     * Update the ID of the snapshot given snapshot.
     *
     * @param snapshotId
     *            The (old) snapshot ID to update.
     * @param newSnapshotId
     *            The new ID to set.
     */
    void updateId(Guid snapshotId, Guid newSnapshotId);

    /**
     * Return the {@link Snapshot} <b>first</b> id that matches the given parameters.<br>
     * <b>Note:</b> If more than one snapshot answers to the parameters, only the first will be returned (oldest by
     * creation date).
     *
     * @param vmId
     *            The id of the VM to check for.
     * @param type
     *            The type of snapshot.
     * @return The ID of the snapshot, or <code>null</code> if it doesn't exist.
     */
    Guid getId(Guid vmId, SnapshotType type);

    /**
     * Return the {@link Snapshot} <b>first</b> id that matches the given parameters.<br>
     * <b>Note:</b> If more than one snapshot answers to the parameters, only the first will be returned (oldest by
     * creation date).
     *
     * @param vmId
     *            The id of the VM to check for.
     * @param type
     *            The type of snapshot.
     * @param status
     *            The status of the snapshot.
     * @return The ID of the snapshot, or <code>null</code> if it doesn't exist.
     */
    Guid getId(Guid vmId, SnapshotType type, SnapshotStatus status);

    /**
     * Get all the snapshots of the given VM.
     *
     * @param vmId
     *            The VM id.
     * @return A list of snapshots that exist for the VM, ordered by date (earliest to latest), or empty list if no
     *         snapshots exist.
     */
    List<Snapshot> getForVm(Guid vmId);

    /**
     * Check if there exists a snapshot of the given type for the VM.
     *
     * @param vmId
     *            The id of the VM to check for.
     * @param type
     *            The type to look for.
     * @return Whether a snapshot of the given type exists for the VM or not.
     */
    boolean exists(Guid vmId, SnapshotType type);

    /**
     * Check if there exists a snapshot for the VM with the given status.
     *
     * @param vmId
     *            The id of the VM to check for.
     * @param status
     *            The status to look for.
     * @return Whether a snapshot of the given status exists for the VM or not.
     */
    boolean exists(Guid vmId, SnapshotStatus status);

    /**
     * Check if there exists a snapshot for the VM with the given ID.
     *
     * @param vmId
     *            The ID of the VM to check for.
     * @param snapshotId
     *            The ID of the snapshot to look for.
     * @return Whether a snapshot of the given ID exists for the VM or not.
     */
    boolean exists(Guid vmId, Guid snapshotId);
}