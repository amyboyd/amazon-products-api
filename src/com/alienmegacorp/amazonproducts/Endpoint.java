package com.alienmegacorp.amazonproducts;

/**
 * Defines the domains for each country the API supports.
 */
public enum Endpoint {
    // Enum name must be an upper-case ISO 3166 country code.
    CA("ecs.amazonaws.ca", "www.amazon.ca"),
    FR("ecs.amazonaws.fr", "www.amazon.fr"),
    DE("ecs.amazonaws.de", "www.amazon.de"),
    JP("ecs.amazonaws.jp", "www.amazon.jp"),
    // The UK's ISO code is GB (Great Britain).
    GB("ecs.amazonaws.co.uk", "www.amazon.co.uk"),
    US("ecs.amazonaws.com", "www.amazon.com");

    private final String api, website;

    /**
     * @param api API domain.
     * @param website Consumer website.
     */
    Endpoint(final String api, final String website) {
        this.api = api;
        this.website = website;
    }

    /**
     * @return API domain.
     */
    public String getAPIdomain() {
        return api;
    }

    /**
     * @return Consumer website.
     */
    public String getWebsiteDomain() {
        return website;
    }
}
