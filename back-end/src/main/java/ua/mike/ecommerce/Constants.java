package ua.mike.ecommerce;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    @UtilityClass
    public static class Version {
        public static final String V1 = "/api/v1/";
    }

    @UtilityClass
    public static class Header {
        public static final String X_CUSTOMER_TOKEN = "x-customer-token";
    }
}
