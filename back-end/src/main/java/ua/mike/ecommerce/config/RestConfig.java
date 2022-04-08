package ua.mike.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import ua.mike.ecommerce.models.Category;
import ua.mike.ecommerce.models.Product;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;

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
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((meta, methods) -> methods.disable(disabledMethods))
                .withCollectionExposure((meta, methods) -> methods.disable(disabledMethods));
        config.getExposureConfiguration()
                .forDomainType(Category.class)
                .withItemExposure((meta, methods) -> methods.disable(disabledMethods))
                .withCollectionExposure((meta, methods) -> methods.disable(disabledMethods));
        // expose all primary keys
        config.exposeIdsFor(
                entityManager.getMetamodel()
                        .getEntities().stream()
                        .map(Type::getJavaType).toArray(Class[]::new)
        );
    }
}
