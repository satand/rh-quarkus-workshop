package org.acme.people.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import io.smallrye.config.ConfigValue;

public class FileConfigSourceFactory implements ConfigSourceFactory {

    @Override
    public Iterable<ConfigSource> getConfigSources(ConfigSourceContext context) {
        
        ConfigValue value = context.getValue("custom.config.file");
        if (value == null || value.getValue() == null || value.getValue().isBlank()) {
            return Collections.emptyList();
        }

        Path filePath = Path.of(value.getValue());
        if (!Files.exists(filePath)) {
            return Collections.emptyList();
        }

        Map<String, String> properties = new HashMap<>();
        try (InputStream is = Files.newInputStream(filePath)) {
            Properties props = new Properties();
            props.load(is);
            props.forEach((k, v) -> properties.put(k.toString(), v.toString()));
        } catch (IOException e) {
            return Collections.emptyList();
        }

        ConfigSource source = new ConfigSource() {
            @Override
            public int getOrdinal() {
                return 280;
            }

            @Override
            public Set<String> getPropertyNames() {
                return properties.keySet();
            }

            @Override
            public Map<String, String> getProperties() {
                return properties;
            }

            @Override
            public String getValue(String propertyName) {
                return properties.get(propertyName);
            }

            @Override
            public String getName() {
                return "FileConfigSource[" + filePath + "]";
            }
        };

        return List.of(source);
    }
}
