package app.com.thetechnocafe.cyberoamclient.Utils;

/**
 * Created by gurleensethi on 18/10/16.
 */

public class ValueUtils {
    public static final String BASE_IP_ADDRESS = "172.16.68.6";
    public static final String BASE_PORT = "8090";
    public static final String BASE_HTTP = "http://";
    public static final String BASE_LOGIN_URL = "/login.xml";
    public static final String BASE_CHECK_URL = "/live";
    public static final String BASE_LOGOUT_URL = "/logout.xml";
    public static final String MODE = "mode";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String MODE_LOGIN = "191";
    public static final String MODE_CHECK = "192";
    public static final String MODE_LOGOUT = "193";
    public static final String A = "a";

    public static final int LOGIN_SUCCESS = 100;
    public static final int ERROR_USERNAME_EMPTY = 101;
    public static final int ERROR_PASSWORD_EMPTY = 102;
    public static final int ERROR_USERNAME_PASSWORD = 103;
    public static final int ERROR_MAXIMUM_LOGIN_LIMIT = 104;
    public static final int ERROR_SERVER_ACCOUNT_LOCKED = 105;
    public static final int LOGOUT_SUCCESS = 106;
    public static final int ERROR_LOGOUT = 107;
    public static final int ERROR_LOGIN_AGAIN = 108;
    public static final int ALREADY_LOGGED_IN = 109;
    public static final int ERROR_VOLLEY_ERROR = 110;
    public static final int ERROR_NOT_ALLOWED = 111;

    public static final String STATE_LOGGED_IN = "loggedIn";
    public static final String STATE_LOGGED_OUT = "loggedOut";
    public static final long FIXED_INTERVAL = 120000;

    //Keys for Realm
    public static final String REALM_ACCOUNTS_USERNAME = "mUsername";
    public static final String REALM_ACCOUNTS_PASSWORD = "mPassword";

    public static final String FIRST_RUN = "first_run";
}