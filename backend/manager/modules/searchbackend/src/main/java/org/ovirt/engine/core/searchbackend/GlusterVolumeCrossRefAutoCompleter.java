package org.ovirt.engine.core.searchbackend;

public class GlusterVolumeCrossRefAutoCompleter extends SearchObjectsBaseAutoCompleter {
    public GlusterVolumeCrossRefAutoCompleter() {
        mVerbs.put(SearchObjects.VDC_CLUSTER_OBJ_NAME, SearchObjects.VDC_CLUSTER_OBJ_NAME);
        buildCompletions();
    }
}
