package ua.mike.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import ua.mike.ecommerce.models.*;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;
import java.util.Set;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    private static final HttpMethod[] disabledMethods = {
            HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH
    };


    private final EntityManager entityManager;

    public RestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins("*");
        this.disableMethods(config, Set.of(Product.class, Category.class, Country.class, State.class, Order.class));
        // expose all primary keys
        config.exposeIdsFor(
                entityManager.getMetamodel()
                        .getEntities().stream()
                        .map(Type::getJavaType).toArray(Class[]::new)
        );
    }

    private void disableMethods(RepositoryRestConfiguration config, Set<Class<?>> types) {
        types.forEach(type -> {
            config.getExposureConfiguration()
                    .forDomainType(type)
                    .withItemExposure((meta, methods) -> methods.disable(disabledMethods))
                    .withCollectionExposure((meta, methods) -> methods.disable(disabledMethods));
        });
    }
}
