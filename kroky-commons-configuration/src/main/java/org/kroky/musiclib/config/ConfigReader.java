package org.kroky.musiclib.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConfigReader {

    private static final Logger LOG = LoggerFactory.getLogger(Config.class);
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    static void init(File... dirsToScan) {
        synchronized (initialized) {
            if (!initialized.get()) {
                try {
                    LOG.debug("Initializing configuration...");

                    // do not use getInstance() method here or you'll get in the infinite loop
                    Config config = Config.getNoInitInstance();
                    Properties props = new Properties();

                    File file = Config.CONFIG_FILE;
                    if (!file.exists()) {
                        props.store(new FileOutputStream(file), null);
                    }
                    props.load(new FileInputStream(file));
                    config.putAll(props);

                    initialized.set(true);

                    LOG.debug("Configuration initialized.");
                } catch (Throwable e) {
                    LOG.error("Unable to initialize Configuration!", e);
                    throw new ConfigurationException("Unable to initialize Configuration!", e);
                }
            }
        }
    }

}
