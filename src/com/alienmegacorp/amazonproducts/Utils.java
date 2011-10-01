package com.alienmegacorp.amazonproducts;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

final class Utils {
    private Utils() {
    }

    /**
     * Copy the content of one file to another.
     *
     * @throws IOException if the URL doesn't respond "200 OK", or another error.
     */
    public static void copy(final URL source, final File destination)
            throws IOException {
        InputStream in = null;
        FileOutputStream out = null;

        try {
            destination.getParentFile().mkdirs();
            destination.createNewFile();

            in = source.openStream();
            out = new FileOutputStream(destination, false);

            byte[] buf = new byte[10000];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (final IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            destination.delete();

            throw ex;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (final IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Change every occurence of <tt>replace</tt> to <tt>with</tt> in a file's content, then overwrite the file.
     *
     * @throws IOException if the file does not exist.
     */
    static void replaceLiteralInFile(final File file, final String replace, final String with)
            throws IOException {
        if (!file.exists()) {
            throw new IOException("File does not exist: " + file.getAbsolutePath());
        }

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final StringBuffer oldText = new StringBuffer((int) file.length());
            String line = "";

            while ((line = reader.readLine()) != null) {
                oldText.append(line).append("\n");
            }
            reader.close();

            final FileWriter writer = new FileWriter(file);
            writer.write(oldText.toString().replace(replace, with));
            writer.close();
        } catch (final IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Canonicalize a Map as a query string.
     *
     * @param params Parameter name-value pairs.
     * @return Canonical form of query string.
     */
    static String mapToQueryString(final Map<String, String> params) {
        if (params.isEmpty()) {
            throw new IllegalArgumentException("Map must not be empty");
        }

        final StringBuilder buffer = new StringBuilder(350);
        final Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();

        while (iter.hasNext()) {
            final Map.Entry<String, String> pair = iter.next();
            buffer.append(percentEncodeRfc3986(pair.getKey()));
            buffer.append('=');
            buffer.append(percentEncodeRfc3986(pair.getValue()));
            if (iter.hasNext()) {
                buffer.append('&');
            }
        }

        return buffer.toString();
    }

    /**
     * Takes a query string, separates the constituent name-value pairs and stores them in a
     * hashmap.
     */
    static Map<String, String> queryStringToMap(final String queryString) {
        final String[] pairs = queryString.split("&");
        final Map<String, String> map = new HashMap<String, String>(pairs.length);

        for (final String pair: pairs) {
            if (pair.length() < 1) {
                continue;
            }

            String[] tokens = pair.split("=", 2);
            for (int j = 0; j < tokens.length; j++) {
                try {
                    tokens[j] = URLDecoder.decode(tokens[j], "UTF-8");
                } catch (final UnsupportedEncodingException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            switch (tokens.length) {
                case 1: {
                    if (pair.charAt(0) == '=') {
                        map.put("", tokens[0]);
                    } else {
                        map.put(tokens[0], "");
                    }
                    break;
                }
                case 2: {
                    map.put(tokens[0], tokens[1]);
                    break;
                }
            }
        }
        return map;
    }

    /**
     * Percent-encode values according the RFC 3986. The built-in Java URLEncoder does not encode
     * according to the RFC,
     * so we make the extra replacements.
     *
     * @param string Decoded string.
     * @return Encoded string per RFC 3986.
     */
    static String percentEncodeRfc3986(final String string) {
        try {
            return URLEncoder.encode(string, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (final UnsupportedEncodingException e) {
            return string;
        }
    }
}
