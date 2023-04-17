package org.sahaj.parking.data;

import net.snowflake.client.jdbc.internal.google.gson.Gson;

/**
 * Class to represent Responses by controller for parking related requests.
 */
public class Response {

    /**
     * The Reponse string.
     */
    String reponseString;

    /**
     * Instantiates a new Response.
     *
     * @param response the response
     */
    public Response(String response) {
        this.reponseString = response;
    }

    /**
     * Instantiates a new Response.
     */
    public Response() {
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
