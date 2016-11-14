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
    }
}
