package org.kroky.musiclib.config;

/**
 * The exception thrown by the Configuration module
 *
 * @author PeterKrokavec
 * @since 1.0
 */
public class ConfigurationException extends RuntimeException {

    private static final long serialVersionUID = -4064421689986999911L;

    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

}
