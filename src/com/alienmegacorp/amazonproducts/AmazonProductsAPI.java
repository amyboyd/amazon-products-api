package com.alienmegacorp.amazonproducts;

import com.alienmegacorp.amazonproducts.internals.Item;
import com.alienmegacorp.amazonproducts.internals.ItemLookupResponse;
import com.alienmegacorp.amazonproducts.internals.ItemSearchResponse;
import com.alienmegacorp.amazonproducts.internals.Price;
import java.io.*;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import play.Logger;
import play.Play;

/**
 * Multiton -- one instance per country endpoint.
 */
public class AmazonProductsAPI {
    /*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    final private String AWS_ACCESS_KEY;

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS Your Account page.
     */
    final private String AWS_SECRET_KEY;

    /*
     * US: ecs.amazonaws.com
     * CA: ecs.amazonaws.ca
     * GB: ecs.amazonaws.co.uk
     * DE: ecs.amazonaws.de
     * FR: ecs.amazonaws.fr
     * JP: ecs.amazonaws.jp
     */
    private final String API_ENDPOINT;

    private SignedRequestsHelper helper;

    private static Unmarshaller unmarshaller;

    /**
     * Multiton holder -- one instance for each Amazon site.
     */
    final private static Map<String, AmazonProductsAPI> INSTANCES = new HashMap<String, AmazonProductsAPI>(8);

    static {
        try {
            unmarshaller = JAXBContext.newInstance("com.lights51.amazon.internals").createUnmarshaller();
        } catch (final JAXBException ex) {
            Logger.error(ex, "Unmarshalling exception");
        }
    }

    /**
     * Construct the ProductApi on first call. All subsequent calls return the already-created
     * instance.
     * @param endpoint If null, the return value will also be null.
     * @return May be null.
     */
    public static AmazonProductsAPI getInstance(final IEndpoint endpoint) {
        if (endpoint == null) {
            return null;
        }
        final String apiEndpoint = endpoint.getApiEndpoint();

        if (!INSTANCES.containsKey(apiEndpoint)) {
            final Properties props = Play.configuration;
            final AmazonProductsAPI inst = new AmazonProductsAPI(props.getProperty("aws.access_key"), props.getProperty("aws.secret_key"), apiEndpoint);

            INSTANCES.put(apiEndpoint, inst);
        }

        return INSTANCES.get(apiEndpoint);
    }

    private AmazonProductsAPI(final String awsAccessKey, final String awsSecretKey, final String endpoint) {
        if (awsAccessKey.length() != 20) {
            throw new IllegalArgumentException("AWS access key length must be 20, got: " + awsAccessKey);
        }
        if (awsSecretKey.length() != 40) {
            throw new IllegalArgumentException("AWS secret key length must be 40, got: " + awsSecretKey);
        }
        if (endpoint.matches("^ecs\\.amazonaws\\.[a-z\\.]+$") == false) {
            throw new IllegalArgumentException("ECS endpoint invalid: " + endpoint);
        }

        Logger.info("Creating ProductsApi instance for endpoint: " + endpoint);
        Logger.info("Access key: " + awsAccessKey + ", secret key: " + awsSecretKey);

        AWS_ACCESS_KEY = awsAccessKey;
        AWS_SECRET_KEY = awsSecretKey;
        API_ENDPOINT = endpoint;

        try {
            helper = SignedRequestsHelper.getInstance(API_ENDPOINT, AWS_ACCESS_KEY, AWS_SECRET_KEY);
        } catch (final Exception ex) {
            Logger.error(ex, "Amazon request sign error");
        }
    }

    /**
     * Do an ItemLookup request.
     *
     * @see http://docs.amazonwebservices.com/AWSECommerceService/2010-09-01/DG/index.html?ItemLookup.html
     *
     * @param responseGroups Comma-seperated response groups.
     */
    public Item itemLookup(final String asin, final String responseGroups) {
        final Map<String, String> params = new HashMap<String, String>(5);
        params.put("Operation", "ItemLookup");
        params.put("ItemId", asin);
        params.put("ResponseGroup", responseGroups);

        try {
            final File file = getCacheFile("ItemLookup", asin);
            if (!file.exists()) {
                Utils.copy(new URL(signUrl(params)), file, false);
                Utils.replaceLiteralInFile(file, " xmlns=\"http://webservices.amazon.com/AWSECommerceService/2009-11-01\"", "");
            }

            final InputStream is = new FileInputStream(file);
            final ItemLookupResponse irl = (ItemLookupResponse) unmarshaller.unmarshal(is);
            is.close();

            return irl.getItems().get(0).getItem().get(0);
        } catch (final java.io.IOException ex) {
            Logger.error("ItemLookup IOException: " + ex.getMessage());
        } catch (final javax.xml.bind.JAXBException ex) {
            Logger.error("ItemLookup JAXBException: " + ex.getMessage());
        }

        return null;
    }

    /**
     * Do an ItemLookup request with customizable ResponseGroup.
     *
     * @see http://docs.amazonwebservices.com/AWSECommerceService/2010-09-01/DG/index.html?ItemSearch.html
     *
     * @param searchIndex "All" or one of the following:
     *      'All','Apparel','Automotive','Baby','Beauty','Blended','Books','Classical','DVD',
     *      'DigitalMusic','Electronics','GourmetFood','Grocery','HealthPersonalCare','HomeGarden',
     *      'Industrial','Jewelry','KindleStore','Kitchen','MP3Downloads','Magazines','Merchants',
     *      'Miscellaneous','Music','MusicTracks','MusicalInstruments','OfficeProducts',
     *      'OutdoorLiving','PCHardware','PetSupplies','Photo','Shoes','SilverMerchants',
     *      'Software','SportingGoods','Tools','Toys','UnboxVideo','VHS','Video','VideoGames',
     *      'Watches','Wireless','WirelessAccessories'.
     */
    public List<Item> itemSearch(final String query, final String responseGroup, final String searchIndex) {
        final Map<String, String> params = new HashMap<String, String>(6);
        params.put("SearchIndex", searchIndex);
        params.put("Operation", "ItemSearch");
        params.put("Keywords", query);
        params.put("ResponseGroup", responseGroup);

        try {
            final File file = getCacheFile("ItemSearch", DigestUtils.shaHex(query));
            if (!file.exists()) {
                Utils.copy(new URL(signUrl(params)), file, false);
                Utils.replaceLiteralInFile(file, " xmlns=\"http://webservices.amazon.com/AWSECommerceService/2009-11-01\"", "");
            }

            final InputStream is = new FileInputStream(file);
            final ItemSearchResponse response = (ItemSearchResponse) unmarshaller.unmarshal(is);
            is.close();

            return response.getItems().get(0).getItem();
        } catch (final java.io.IOException ex) {
            Logger.error("ItemSearch IOException: " + ex.getMessage());
        } catch (final javax.xml.bind.JAXBException ex) {
            Logger.error("ItemSearch JAXBException: " + ex.getMessage());
        }

        return null;
    }

    /**
     * Add default params to the given params, and sign the URL as Amazon wants it to be signed.
     */
    private String signUrl(final Map<String, String> params) {
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2009-11-01");
        return helper.sign(params);
    }

    private File getCacheFile(final String operation, final String filename) {
        final File file = Play.getFile("tmp/" + API_ENDPOINT + "-" + operation + "/" + filename + ".xml");
        if (file.getTotalSpace() == 0l) {
            file.delete();
        }
        return file;
    }

    /**
     * Helper method to find the "normal" (or best guess at normal) price of an item.
     */
    public static Price getPrice(final Item item) {
        Price price = null;

        // Get Amazon's list price.
        try {
            if ((price = item.getItemAttributes().getListPrice()) != null) {
                return price;
            }
        } catch (final NullPointerException ex) {
        }

        // Get the lowest new price from a third-party.
        try {
            if ((price = item.getOfferSummary().getLowestNewPrice()) != null) {
                return price;
            }
        } catch (final NullPointerException ex) {
        }

        return price;
    }

    /**
     * This class contains all the logic for signing requests to the Amazon Product Advertising API.
     */
    private static class SignedRequestsHelper {
        /**
         * All strings are handled as UTF-8
         */
        private static final String UTF8_CHARSET = "UTF-8";

        /**
         * The HMAC algorithm required by Amazon
         */
        private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

        /**
         * This is the URI for the service, don't change unless you really know what you're doing.
         */
        private static final String REQUEST_URI = "/onca/xml";

        /**
         * The sample uses HTTP GET to fetch the response. If you changed the sample to use HTTP POST
         * instead, change the
         * value below to POST.
         */
        private static final String REQUEST_METHOD = "GET";

        private String endpoint = null;

        private String awsAccessKey = null;

        private String awsSecretKey = null;

        private SecretKeySpec secretKeySpec = null;

        private Mac mac = null;

        private SignedRequestsHelper() {
            // Use <code>getInstance()</code>.
        }

        /**
         * You must provide the three values below to initialize the helper.
         *
         * @param endpoint Destination for the requests, e.g. "ecs.amazonaws.jp".
         * @param awsAccessKey
         * @param awsSecretKey
         * @return
         * @throws IllegalArgumentException if any of the params are invalid.
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeyException
         */
        static SignedRequestsHelper getInstance(final String endpoint, final String awsAccessKey,
                final String awsSecretKey)
                throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeyException {
            if ((null == endpoint) || (endpoint.length() == 0)) {
                throw new IllegalArgumentException("endpoint is null or empty");
            }
            if ((null == awsAccessKey) || (awsAccessKey.length() == 0)) {
                throw new IllegalArgumentException("awsAccessKey is null or empty");
            }
            if ((null == awsSecretKey) || (awsSecretKey.length() == 0)) {
                throw new IllegalArgumentException("awsSecretKey is null or empty");
            }

            final SignedRequestsHelper instance = new SignedRequestsHelper();
            instance.endpoint = endpoint.toLowerCase();
            instance.awsAccessKey = awsAccessKey;
            instance.awsSecretKey = awsSecretKey;

            byte[] secretyKeyBytes;
            try {
                secretyKeyBytes = instance.awsSecretKey.getBytes(UTF8_CHARSET);
            } catch (final UnsupportedEncodingException ex) {
                secretyKeyBytes = new byte[] {};
                Logger.error(ex, ex.getMessage());
            }
            instance.secretKeySpec = new SecretKeySpec(secretyKeyBytes, HMAC_SHA256_ALGORITHM);
            instance.mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            instance.mac.init(instance.secretKeySpec);

            return instance;
        }

        /**
         * This method signs requests in hashmap form. It returns a URL that should be used to fetch the
         * response. The URL returned should not be modified in any way, doing so will invalidate the signature
         * and Amazon will reject the request.
         */
        String sign(final Map<String, String> params) {
            params.put("AWSAccessKeyId", awsAccessKey);
            params.put("Timestamp", timestamp());

            // The parameters need to be processed in lexicographical order, so we'll use a TreeMap
            // implementation for that.
            final SortedMap<String, String> sortedParamMap = new TreeMap<String, String>(params);

            final String canonicalQS = Utils.mapToQueryString(sortedParamMap);
            final String toSign = REQUEST_METHOD + "\n" + endpoint + "\n" + REQUEST_URI + "\n" + canonicalQS;
            String sig = Utils.percentEncodeRfc3986(hmac(toSign));

            // When <code>canonicalQS</code> is empty, a line break is at the end of the signature. Remove it.
            if (sig.endsWith("%0D%0A")) {
                sig = sig.substring(0, sig.length() - 6);
            }

            return "http://" + endpoint + REQUEST_URI + "?" + canonicalQS + "&Signature=" + sig;
        }

        /**
         * This method signs requests in query-string form. It returns a URL that should be used to
         * fetch the response. The URL returned should not be modified in any way, doing so will invalidate
         * the signature and Amazon will reject the request.
         *
         * @param queryString
         * @return
         */
        String sign(final String queryString) {
            return sign(Utils.queryStringToMap(queryString));
        }

        /**
         * Compute the HMAC.
         *
         * @param stringToSign String to compute the HMAC over.
         * @return base64-encoded hmac value.
         */
        private String hmac(final String stringToSign) {
            String signature = null;
            byte[] data;
            byte[] rawHmac;
            try {
                data = stringToSign.getBytes(UTF8_CHARSET);
                rawHmac = mac.doFinal(data);
                signature = new String(new Base64().encode(rawHmac));
            } catch (final UnsupportedEncodingException ex) {
                throw new RuntimeException(UTF8_CHARSET + " is unsupported!", ex);
            }
            return signature;
        }

        /**
         * Generate a ISO-8601 format timestamp as required by Amazon.
         *
         * @return ISO-8601 format timestamp.
         */
        private String timestamp() {
            final Calendar cal = Calendar.getInstance();
            final DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
            return dfm.format(cal.getTime());
        }
    }
}
