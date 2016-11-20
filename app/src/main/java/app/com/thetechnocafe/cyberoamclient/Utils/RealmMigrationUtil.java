package app.com.thetechnocafe.cyberoamclient.Utils;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by gurleensethi on 14/11/16.
 */

public class RealmMigrationUtil implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        //Get the Realm Schema
        RealmSchema schema = realm.getSchema();

        //If its initial version -> Add field for account validity
        if (oldVersion == 0) {
            //Add isValid field to accounts schema
            schema.get(ValueUtils.REALM_ACCOUNTS_CLASS)
                    .addField(ValueUtils.REALM_ACCOUNTS_VALID, boolean.class, null);

            //Increment the version
            oldVersion++;
        }

        //If version 1 add SessionLogModel
        if (oldVersion == 1) {
            if (!schema.contains(ValueUtils.REALM_SESSION_CLASS)) {
                //Add model
                schema.create(ValueUtils.REALM_SESSION_CLASS)
                        .addField(ValueUtils.REALM_SESSION_USERNAME, String.class)
                        .addField(ValueUtils.REALM_SESSION_DATA_CONSUMED, double.class)
                        .addField(ValueUtils.REALM_SESSION_LOGGED_IN_DURATION, long.class)
                        .addField(ValueUtils.REALM_SESSION_LOGGED_IN_TIME, long.class);
            }

            //Increment the version
            oldVersion++;
        }

        //If version 2, change SessionLogModel
        if (oldVersion == 2) {
            //Add mLoginStatus
            schema.get(ValueUtils.REALM_SESSION_CLASS)
                    .addField(ValueUtils.REALM_SESSION_LOGIN_IN_STATUS, int.class, null);

            //Increment the older version
            oldVersion++;
        }
    }
}
