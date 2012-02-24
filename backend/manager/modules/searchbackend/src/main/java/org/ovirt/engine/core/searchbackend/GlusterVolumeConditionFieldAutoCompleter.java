package org.ovirt.engine.core.searchbackend;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.TRANSPORT_TYPE;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_STATUS;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_TYPE;

public class GlusterVolumeConditionFieldAutoCompleter extends BaseConditionFieldAutoCompleter {

    private enum FIELDS {
        NAME,
        TYPE,
        TRANSPORT_TYPE,
        REPLICA_COUNT,
        STRIPE_COUNT,
        STATUS
    };

    public GlusterVolumeConditionFieldAutoCompleter() {
        super();
        // Building the basic verbs Dict
        mVerbs.put(FIELDS.NAME.toString(), "NAME");
        mVerbs.put(FIELDS.TYPE.toString(), "TYPE");
        mVerbs.put(FIELDS.TRANSPORT_TYPE.toString(), "TRANSPORT_TYPE");
        mVerbs.put(FIELDS.REPLICA_COUNT.toString(), "REPLICA_COUNT");
        mVerbs.put(FIELDS.STRIPE_COUNT.toString(), "STRIPE_COUNT");
        mVerbs.put(FIELDS.STATUS.toString(), "STATUS");
        // Building the autoCompletion Dict
        buildCompletions();

        // Building the types dict
        getTypeDictionary().put(FIELDS.NAME.toString(), String.class);
        getTypeDictionary().put(FIELDS.TYPE.toString(), VOLUME_TYPE.class);
        getTypeDictionary().put(FIELDS.TRANSPORT_TYPE.toString(), TRANSPORT_TYPE.class);
        getTypeDictionary().put(FIELDS.REPLICA_COUNT.toString(), Integer.class);
        getTypeDictionary().put(FIELDS.STRIPE_COUNT.toString(), Integer.class);
        getTypeDictionary().put(FIELDS.STATUS.toString(), VOLUME_STATUS.class);

        // building the ColumnName Dict
        mColumnNameDict.put(FIELDS.NAME.toString(), "vol_name");
        mColumnNameDict.put(FIELDS.TYPE.toString(), "vol_type");
        mColumnNameDict.put(FIELDS.TRANSPORT_TYPE.toString(), "transport_type");
        mColumnNameDict.put(FIELDS.REPLICA_COUNT.toString(), "replica_count");
        mColumnNameDict.put(FIELDS.STRIPE_COUNT.toString(), "stripe_count");
        mColumnNameDict.put(FIELDS.STATUS.toString(), "status");

        // Building the validation dict
        buildBasicValidationTable();
    }

    @Override
    public IAutoCompleter getFieldRelationshipAutoCompleter(String fieldName) {
        try {
            switch (FIELDS.valueOf(fieldName)) {
            case NAME:
            case TYPE:
            case TRANSPORT_TYPE:
            case STATUS:
                return new StringConditionRelationAutoCompleter();
            case REPLICA_COUNT:
            case STRIPE_COUNT:
                return new NumericConditionRelationAutoCompleter();
            default:
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public IConditionValueAutoCompleter getFieldValueAutoCompleter(String fieldName) {
        try {
            switch (FIELDS.valueOf(fieldName)) {
            case TYPE:
                return new EnumValueAutoCompleter(VOLUME_TYPE.class);
            case TRANSPORT_TYPE:
                return new EnumValueAutoCompleter(TRANSPORT_TYPE.class);
            case STATUS:
                return new EnumValueAutoCompleter(VOLUME_STATUS.class);
            default:
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
