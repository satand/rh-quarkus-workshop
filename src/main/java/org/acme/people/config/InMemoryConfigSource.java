package org.acme.people.config;

import java.util.Map;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

public class InMemoryConfigSource implements ConfigSource {

    private static final Map<String, String> PROPERTIES = Map.of(
            "app.info.version", "1.0.0",
            "app.info.author", "Quarkus Workshop Team"
    );

    @Override
    public int getOrdinal() {
        return 230;
    }

    @Override
    public Set<String> getPropertyNames() {
        return PROPERTIES.keySet();
    }

    @Override
    public Map<String, String> getProperties() {
        return PROPERTIES;
    }

    @Override
    public String getValue(String propertyName) {
        return PROPERTIES.get(propertyName);
    }

    @Override
    public String getName() {
        return "InMemoryConfigSource";
    }
}
