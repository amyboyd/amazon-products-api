package com.alienmegacorp.amazonproducts;

import java.io.*;
import java.net.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.GZIPOutputStream;

/**
 * Utility class.
 */
final class Utils {
    private Utils() {
        // Prevent this class from being instantiated.
    }

    /**
     * Copy the content of one file to another.
     *
     * <p>Optionally the source can be appended to the destination file.
     *
     * @throws IOException if the URL doesn't respond "200 OK", or another error.
     */
    public static void copy(final URL source, final File destination, final boolean append)
            throws IOException {
        play.Logger.debug("Copying URL: " + source.toString());

        InputStream in = null;
        FileOutputStream out = null;

        try {
            if (!append) {
                destination.mkdirs();
                destination.delete();
                destination.createNewFile();
            }

            in = source.openStream();
            out = new FileOutputStream(destination, append);

            byte[] buf = new byte[10000];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (final IOException ex) {
            play.Logger.error("Exception copying URL to file: " + ex.getMessage());
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
                play.Logger.error(ex.getMessage());
            }
        }
    }

    /**
     * Copy the content of one file to another.
     *
     * Optionally the source can be appended to the destination file.
     *
     * @throws IOException
     */
    public static void copy(final File source, final File destination, final boolean append)
            throws IOException {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(source);
            out = new FileOutputStream(destination, append);

            byte[] buf = new byte[10000];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (final IOException ex) {
            play.Logger.error(ex.getMessage());
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
                play.Logger.error(ex.getMessage());
            }
        }
    }

    /**
     * Write to a file.
     *
     * Optionally the source can be appended to the destination file.
     *
     * @throws IOException
     */
    public static void write(final String source, final File destination, final boolean append)
            throws IOException {
        OutputStream out = null;

        try {
            out = new FileOutputStream(destination, append);
            out.write(source.getBytes("UTF-8"));
        } catch (final IOException ex) {
            play.Logger.error(ex.getMessage());
            destination.delete();

            throw ex;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (final IOException ex) {
                play.Logger.error(ex.getMessage());
            }
        }
    }

    /**
     * Read the content of a URL to a string.
     *
     * @throws IOException
     */
    public static String read(final URL source)
            throws IOException {
        play.Logger.debug("Reading URL to string: " + source.toString());

        final BufferedReader in = new BufferedReader(new InputStreamReader(source.openStream()));
        final StringBuilder response = new StringBuilder(5000);
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }

        in.close();

        return response.toString();
    }

    /**
     * @link http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
     *
     * @return Content of the file as a string.
     * @throws IOException if there is a file-system error.
     */
    public static String read(final File source) throws IOException {
        final FileInputStream stream = new FileInputStream(source);
        try {
            final FileChannel fc = stream.getChannel();
            final MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

            // Instead of using default, pass in a decoder.
            return Charset.defaultCharset().decode(bb).toString();
        } finally {
            stream.close();
        }
    }

    /**
     * Change every occurence of <tt>replace</tt> to <tt>with</tt> in a file's content, then overwrite the file.
     *
     * @throws IOException if the file does not exist.
     */
    public static void replaceLiteralInFile(final File file, final String replace, final String with)
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
            play.Logger.error("Exception replacing literal: %s", ex.getMessage());
        }
    }

    /**
     * @return The fully-redirected URL, or the original if this fails.
     */
    public static URL followRedirects(final URL original) {
        InputStream is = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) original.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            conn.connect();
            is = conn.getInputStream();

            return conn.getURL();
        } catch (final Exception ex) {
            play.Logger.error(ex.getMessage());

            // Just return the original and forget about following redirects.
            return original;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (final IOException ex) {
                    play.Logger.error(ex, "IO exception");
                }
            }
        }
    }

    /**
     * GZIP the "input" file and save to "output".
     *
     * @param input
     * @param output Will be overwritten if it already exists.
     */
    public static void gzip(final File input, final File output) {
        try {
            output.delete();
            output.createNewFile();
        } catch (final IOException ex) {
            play.Logger.error(ex, "File not created: " + ex.getMessage());
        }

        try {
            final InputStream in = new FileInputStream(input);
            final OutputStream out = new GZIPOutputStream(new FileOutputStream(output));

            final byte[] buf = new byte[5000];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        } catch (final IOException ex) {
            play.Logger.error(ex, "GZIP failed: " + ex.getMessage());
        }
    }

    /**
     * Canonicalize a Map as a query string.
     *
     * @param params Parameter name-value pairs.
     * @return Canonical form of query string.
     */
    public static String mapToQueryString(final Map<String, String> params) {
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
    public static Map<String, String> queryStringToMap(final String queryString) {
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
                    play.Logger.error(ex, ex.getMessage());
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
    public static String percentEncodeRfc3986(final String string) {
        try {
            return URLEncoder.encode(string, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (final UnsupportedEncodingException e) {
            return string;
        }
    }
}
