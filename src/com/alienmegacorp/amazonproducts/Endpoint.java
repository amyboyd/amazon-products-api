package com.alienmegacorp.amazonproducts;

/**
 * Enum name must be an upper-case ISO 3166 country code.
 */
public enum Endpoint implements IEndpoint {
    CA("ecs.amazonaws.ca", "www.amazon.ca"),
    FR("ecs.amazonaws.fr", "www.amazon.fr"),
    DE("ecs.amazonaws.de", "www.amazon.de"),
    JP("ecs.amazonaws.jp", "www.amazon.jp"),
    GB("ecs.amazonaws.co.uk", "www.amazon.co.uk"),
    US("ecs.amazonaws.com", "www.amazon.com");

    private String apiEndpoint, website;

    Endpoint(String apiEndpoint, String website) {
        this.apiEndpoint = apiEndpoint;
        this.website = website;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public String getWebsite() {
        return website;
    }
}
