package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import app.com.thetechnocafe.cyberoamclient.Common.VolleyRequestQueue;

/**
 * Created by gurleensethi on 18/10/16.
 */

public abstract class NetworkUtils {
    private static final String TAG = "NetworkUtils";
    private static String VOLLEY_TAG;

    public NetworkUtils(@Nullable String tag) {
        VOLLEY_TAG = tag;
    }

    public abstract void onResultReceived(boolean success, String message);

    /**
     * Send login POST request to cyberoam ip address specified
     * Notify the result to the presenter
     */
    public void login(final Context context, final String username, final String password) {
        //Create new String request
        StringRequest loginRequest = new StringRequest(Request.Method.POST, SharedPreferenceUtils.getCompleteUrlAddress(context, ValueUtils.BASE_LOGIN_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

                //Get status and message
                String status = getXMLStatus(response);
                String message = getXMLMessage(response);

                //Check the status from the response
                if (status != null) {
                    if (status.toUpperCase().equals(ValueUtils.XML_STATUS_LIVE)) {
                        onResultReceived(true, message);
                    } else {
                        onResultReceived(false, message);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResultReceived(false, ValueUtils.ERROR_VOLLEY_ERROR);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //Return the body
                return getLoginRequestBody(username, password).getBytes();
            }
        };

        //Add tag to volley request
        if (VOLLEY_TAG != null) {
            loginRequest.setTag(VOLLEY_TAG);
        }

        //Add request to queue
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(loginRequest);
    }


    /**
     * Create login body from username and password and current time
     */
    private String getLoginRequestBody(String username, String password) {
        return ValueUtils.MODE + "=" + ValueUtils.MODE_LOGIN + "&" +
                ValueUtils.USERNAME + "=" + username + "&" +
                ValueUtils.PASSWORD + "=" + password + "&" +
                ValueUtils.A + "=" + new Date().getTime();
    }

    /**
     * Check for login status,
     * whether logged in or not
     */
    public void checkLoginStatus(final Context context, final String username, final String password, final int retryNum) {
        //Create new string request to check status
        StringRequest checkRequest = new StringRequest(Request.Method.GET, getLoginCheckUrl(context, username, password), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Get status and message
                String status = getXMLLiveAck(response);
                String message = getXMLLiveMessage(response);

                if(status != null) {
                    if(status.toUpperCase().equals(ValueUtils.XML_ACK.toUpperCase())) {
                        onResultReceived(true, message);
                    } else {
                        onResultReceived(false, message);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (retryNum > ValueUtils.MAX_RETRY_NUMBER) {
                    onResultReceived(false, ValueUtils.ERROR_VOLLEY_ERROR);
                } else {
                    checkLoginStatus(context, username, password, retryNum + 1);
                }
            }
        });

        //Add tag to volley request
        if (VOLLEY_TAG != null) {
            checkRequest.setTag(VOLLEY_TAG);
        }

        //Add request to Volley queue
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(checkRequest);
    }

    private String getLoginCheckUrl(Context context, String username, String password) {
        return SharedPreferenceUtils.getCompleteUrlAddress(context, ValueUtils.BASE_CHECK_URL) + "?" +
                ValueUtils.MODE + "=" + ValueUtils.MODE_CHECK + "&" +
                ValueUtils.USERNAME + "=" + username + "&" +
                ValueUtils.A + "=" + new GregorianCalendar().getTimeInMillis();
    }

    /**
     * Logout from existing logged in account
     * Notify presenter on login
     */
    public void logout(final Context context, final String username, String password) {
        //Create new string request to logout
        StringRequest logoutRequest = new StringRequest(Request.Method.POST, SharedPreferenceUtils.getCompleteUrlAddress(context, ValueUtils.BASE_LOGOUT_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Get status and message
                String status = getXMLStatus(response);
                String message = getXMLMessage(response);

                //Check the status
                if (status != null) {
                    if (status.toUpperCase().equals(ValueUtils.XML_STATUS_LOGIN)) {
                        onResultReceived(true, message);
                    } else {
                        onResultReceived(false, message);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return getLogoutRequestBody(username).getBytes();
            }
        };

        //Add tag to volley request
        if (VOLLEY_TAG != null) {
            logoutRequest.setTag(VOLLEY_TAG);
        }

        //Add request to queue
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(logoutRequest);
    }

    /**
     * Create logout body from username and current time
     */
    private String getLogoutRequestBody(String username) {
        return ValueUtils.MODE + "=" + ValueUtils.MODE_LOGOUT + "&" +
                ValueUtils.USERNAME + "=" + username + "&" +
                ValueUtils.A + "=" + new Date().getTime();
    }

    /**
     * Get the Status code from XML
     */
    private String getXMLStatus(String xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xml));

            Document document = documentBuilder.parse(inputSource);

            Element element = document.getDocumentElement();
            element.normalize();

            NodeList nodeList = document.getElementsByTagName(ValueUtils.XML_REQUEST_RESPONSE);
            Element element1 = (Element) nodeList.item(0);

            NodeList statusList = element1.getElementsByTagName(ValueUtils.XML_STATUS).item(0).getChildNodes();
            Node statusNode = statusList.item(0);
            return statusNode.getNodeValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the Message from XML
     */
    private String getXMLMessage(String xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xml));

            Document document = documentBuilder.parse(inputSource);

            Element element = document.getDocumentElement();
            element.normalize();

            NodeList nodeList = document.getElementsByTagName(ValueUtils.XML_REQUEST_RESPONSE);
            Element element1 = (Element) nodeList.item(0);

            NodeList messageList = element1.getElementsByTagName(ValueUtils.XML_MESSAGE).item(0).getChildNodes();
            Node messageNode = messageList.item(0);
            return messageNode.getNodeValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the status code for live requets
     */
    private String getXMLLiveAck(String xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xml));

            Document document = documentBuilder.parse(inputSource);

            Element element = document.getDocumentElement();
            element.normalize();

            NodeList nodeList = document.getElementsByTagName(ValueUtils.XML_LIVE_REQUEST_RESPONSE);
            Element element1 = (Element) nodeList.item(0);

            NodeList statusList = element1.getElementsByTagName(ValueUtils.XML_ACK).item(0).getChildNodes();
            Node statusNode = statusList.item(0);
            return statusNode.getNodeValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the live message for live request
     */
    private String getXMLLiveMessage(String xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xml));

            Document document = documentBuilder.parse(inputSource);

            Element element = document.getDocumentElement();
            element.normalize();

            NodeList nodeList = document.getElementsByTagName(ValueUtils.XML_LIVE_REQUEST_RESPONSE);
            Element element1 = (Element) nodeList.item(0);

            NodeList messageList = element1.getElementsByTagName(ValueUtils.XML_LIVE_MESSAGE).item(0).getChildNodes();
            Node messageNode = messageList.item(0);
            return messageNode.getNodeValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
