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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Finds products through the Amazon Products API.
 *
 * Operations supported so far are: ItemLookup, ItemSearch.
 */
public class AmazonProductsAPI {
    /*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    private final String awsAccessKey;

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS Your Account page.
     */
    private final String awsSecretKey;

    private final String awsAssociateTag;

    private final Endpoint endpoint;

    private SignedRequestsHelper helper;

    private static Unmarshaller unmarshaller;

    private static final String API_VERSION = "2011-08-01";

    static {
        try {
            unmarshaller = JAXBContext.newInstance("com.alienmegacorp.amazonproducts.internals").createUnmarshaller();
        }
        catch (JAXBException ex) {
            Logger.getLogger(AmazonProductsAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private AmazonProductsAPI(final String awsAccessKey, final String awsSecretKey, final String awsAssociateTag, final Endpoint endpoint) {
        if (awsAccessKey.length() != 20) {
            throw new IllegalArgumentException("AWS access key length must be 20, got: " + awsAccessKey);
        }
        if (awsSecretKey.length() != 40) {
            throw new IllegalArgumentException("AWS secret key length must be 40, got: " + awsSecretKey);
        }

        Logger.getLogger(AmazonProductsAPI.class.getName()).log(
            Level.INFO,
            "Creating AmazonProductsAPI instance for endpoint: {0}, access key: {1}, secret key: {2}, associate tag {3}",
            new Object[] { endpoint, awsAccessKey, awsSecretKey, awsAssociateTag }
        );

        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
        this.awsAssociateTag = awsAssociateTag;
        this.endpoint = endpoint;

        try {
            helper = SignedRequestsHelper.getInstance(this.endpoint.getAPIdomain(), this.awsSecretKey);
        }
        catch (final Exception ex) {
            Logger.getLogger(AmazonProductsAPI.class.getName()).log(Level.SEVERE, "Amazon request sign error", ex);
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
        final Map<String, String> params = new HashMap<String, String>();
        params.put("Operation", "ItemLookup");
        params.put("ItemId", asin);
        params.put("ResponseGroup", responseGroups);

        final ItemLookupResponse response = (ItemLookupResponse) fetchFromUrlOrCacheAndUnmarshal(params, asin);
        return response.getItems().get(0).getItem().get(0);
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
        final Map<String, String> params = new HashMap<String, String>();
        params.put("SearchIndex", searchIndex);
        params.put("Operation", "ItemSearch");
        params.put("Keywords", query);
        params.put("ResponseGroup", responseGroup);

        final ItemSearchResponse response = (ItemSearchResponse) fetchFromUrlOrCacheAndUnmarshal(params, DigestUtils.shaHex(query));
        return response.getItems().get(0).getItem();
    }

    private Object fetchFromUrlOrCacheAndUnmarshal(final Map<String, String> params, final String cacheKey) {
        if (!params.containsKey("Operation")) {
            throw new RuntimeException("params must have an Operation");
        }
        final String operation = params.get("Operation");

        try {
            final File file = getCacheFile(operation, cacheKey);
            if (!file.exists()) {
                // Add global params that all requests need.
                addGlobalParams(params);
                // Sign the params in a URL as Amazon specifies.
                final URL url = new URL(helper.sign(params));
                // Copy the response to our cache file.
                Utils.copy(url, file);
            }

            // Unmarshal.
            Utils.replaceLiteralInFile(file, " xmlns=\"http://webservices.amazon.com/AWSECommerceService/" + API_VERSION + "\"", "");
            final InputStream is = new FileInputStream(file);
            final Object response = unmarshaller.unmarshal(is);
            is.close();

            return response;
        }
        catch (final Exception ex) {
            Logger.getLogger(AmazonProductsAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Add global params that all requests need.
     */
    private void addGlobalParams(final Map<String, String> params) {
        params.put("AWSAccessKeyId", awsAccessKey);
        params.put("AssociateTag", awsAssociateTag);
        params.put("Service", "AWSECommerceService");
        params.put("Version", API_VERSION);
        params.put("Timestamp", timestamp());
    }

    private File getCacheFile(final String operation, final String filename) {
        final File file = new File("/tmp/" + endpoint.name() + "-" + operation + "/" + filename + ".xml");
        if (file.getTotalSpace() == 0l) {
            file.delete();
        }
        return file;
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
        }
        catch (final NullPointerException ex) {
        }

        // Get the lowest new price from a third-party.
        try {
            if ((price = item.getOfferSummary().getLowestNewPrice()) != null) {
                return price;
            }
        }
        catch (final NullPointerException ex) {
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

        private String apiDomain = null;

        private String awsSecretKey = null;

        private SecretKeySpec secretKeySpec = null;

        private Mac mac = null;

        private SignedRequestsHelper() {
            // Use <code>getInstance()</code>.
        }

        /**
         * You must provide the three values below to initialize the helper.
         *
         * @param apiDomain Destination for the requests, e.g. "ecs.amazonaws.jp".
         * @param awsSecretKey
         * @return
         * @throws IllegalArgumentException if any of the params are invalid.
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeyException
         */
        static SignedRequestsHelper getInstance(final String apiDomain, final String awsSecretKey)
                throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeyException {
            if ((null == apiDomain) || (apiDomain.length() == 0)) {
                throw new IllegalArgumentException("endpoint is null or empty");
            }
            if ((null == awsSecretKey) || (awsSecretKey.length() == 0)) {
                throw new IllegalArgumentException("awsSecretKey is null or empty");
            }

            final SignedRequestsHelper instance = new SignedRequestsHelper();
            instance.apiDomain = apiDomain.toLowerCase();
            instance.awsSecretKey = awsSecretKey;

            byte[] secretyKeyBytes;
            try {
                secretyKeyBytes = instance.awsSecretKey.getBytes(UTF8_CHARSET);
            }
            catch (final UnsupportedEncodingException ex) {
                secretyKeyBytes = new byte[] {};
                Logger.getLogger(SignedRequestsHelper.class.getName()).log(Level.SEVERE, null, ex);
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
            // The parameters need to be processed in lexicographical order, so we'll use a TreeMap
            // implementation for that.
            final SortedMap<String, String> sortedParamMap = new TreeMap<String, String>(params);

            final String canonicalQS = Utils.mapToQueryString(sortedParamMap);
            final String toSign = REQUEST_METHOD + "\n" + apiDomain + "\n" + REQUEST_URI + "\n" + canonicalQS;
            String sig = Utils.percentEncodeRfc3986(hmac(toSign));

            // When <code>canonicalQS</code> is empty, a line break is at the end of the signature. Remove it.
            if (sig.endsWith("%0D%0A")) {
                sig = sig.substring(0, sig.length() - 6);
            }

            return "http://" + apiDomain + REQUEST_URI + "?" + canonicalQS + "&Signature=" + sig;
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
            }
            catch (final UnsupportedEncodingException ex) {
                throw new RuntimeException(UTF8_CHARSET + " is unsupported!", ex);
            }
            return signature;
        }
    }

    // AmazonProductsAPI factory ////////////////////////////////
    /**
     * One instance for each Amazon site.
     */
    final private static EnumMap<Endpoint, AmazonProductsAPI> INSTANCES = new EnumMap<Endpoint, AmazonProductsAPI>(Endpoint.class);

    /**
     * Get a {@link AmazonProductsAPI}.
     */
    public static AmazonProductsAPI getInstance(final Endpoint endpoint,
            final String awsAccessKey, final String awsSecretKey, final String awsAssociateTag) {
        if (!INSTANCES.containsKey(endpoint)) {
            INSTANCES.put(endpoint, new AmazonProductsAPI(awsAccessKey, awsSecretKey, awsAssociateTag, endpoint));
        }

        return INSTANCES.get(endpoint);
    }
}
