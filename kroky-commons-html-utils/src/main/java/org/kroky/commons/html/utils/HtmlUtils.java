package org.kroky.commons.html.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kroky
 */
public class HtmlUtils {

    private static final Logger LOG = LogManager.getLogger();

    private static final int URL_READ_MAX_ATTEMPTS = 3;
    private static final Map<String, String> HTML_TEXTS = new HashMap<>();
    private static final Map<String, String> HTML_SOURCES = new HashMap<>();

    /**
     * Same as getHtmlFromUrl(url, false);
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String getHtmlFromUrl(final String url) {
        return getHtmlFromUrl(url, false);
    }

    /**
     * Gets the whole HTML source from the url.
     *
     * @param url
     * @param cached
     *            if true, attempts to retrieve data from cache, if not found goes
     *            to web
     * @return
     * @throws Exception
     */
    public static String getHtmlFromUrl(final String url, final boolean cached) {
        InputStreamReader isr = null;
        int readStreamFailedCount = 0;
        while (readStreamFailedCount < URL_READ_MAX_ATTEMPTS) {
            try {
                if (cached && HTML_SOURCES.containsKey(url)) {
                    return HTML_SOURCES.get(url);
                }
                // Read all the text returned by the server
                // try several times
                int openStreamFailedCount = 0;
                LOG.info("Trying to open input stream from URL: " + url);
                while (openStreamFailedCount < URL_READ_MAX_ATTEMPTS) {
                    try {
                        isr = new InputStreamReader(new URL(url).openStream(), "UTF-8");
                        openStreamFailedCount = URL_READ_MAX_ATTEMPTS;
                        LOG.info("Success, trying to read from it...");
                    } catch (final IOException e) {
                        openStreamFailedCount++;
                        if (openStreamFailedCount == URL_READ_MAX_ATTEMPTS) {
                            throw e;
                        }
                        LOG.error("Failure, trying again...");
                    }
                }
                final StringBuilder sb = new StringBuilder();
                final char[] buffer = new char[1024];
                int read;
                while ((read = isr.read(buffer)) != -1) {
                    sb.append(buffer, 0, read);
                }
                HTML_SOURCES.put(url, sb.toString());
                LOG.info("Success, returning content");
                return sb.toString();
            } catch (final Exception e) {
                readStreamFailedCount++;
                if (readStreamFailedCount == URL_READ_MAX_ATTEMPTS) {
                    final String message = "Failed to read from URL: " + url;
                    throw new RuntimeException(message + "\nNumber of attempts: " + readStreamFailedCount, e);
                }
                LOG.error("Failure, trying again...");
            } finally {
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (final IOException e) {
                        // nothing to do
                    }
                }
            }
        }
        return null;
    }

    /**
     * Same as getTextFromUrl(url, false);
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String getTextFromUrl(final String url) {
        return getTextFromUrl(url, false);
    }

    /**
     * Gets the whole HTML source from the url, stripped off of tags.
     *
     * @param url
     * @param cached
     *            if true, attempts to retrieve data from cache, if not found goes
     *            to web
     * @return
     * @throws Exception
     */
    public static String getTextFromUrl(final String url, final boolean cached) {
        if (cached && HTML_TEXTS.containsKey(url)) {
            return HTML_TEXTS.get(url);
        }
        // Read all the text returned by the server
        String text = getHtmlFromUrl(url, cached);
        // remove tags from it and replace with TABS
        text = text.replaceAll("<[^>]+>", "\t");
        HTML_TEXTS.put(url, text);
        return text;
    }
}
