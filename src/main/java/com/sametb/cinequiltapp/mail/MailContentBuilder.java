package com.sametb.cinequiltapp.mail;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Samet Bayat.
 * Date: 15.12.2023 11:14 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@Component
public class MailContentBuilder {

    @Value("${mail-service.image}")
    private String mailImage;

    @Value("${mail-service.direct-link}")
    private String directLink;

    @Value("${mail-service.greet}")
    private String greet;

    @Value("${mail-service.slogan}")
    private String slogan;

    @Value("${mail-service.farewell}")
    private String farewell;

    @Value("${mail-service.signature}")
    private String signature;

    @Deprecated
    public String removeWhiteSpaces(String myString){
        return myString.replaceAll("[^a-zA-Z0-9 ]", "");
    }

    public String bodyBuilder(String username) {
        return String.format("%s, <br/>\n\n%s", username, slogan);
    }

    public String subjectBuilder(String username) {
        return String.format("%s, %s!", username, greet);
    }

    public String farewellSubjectBuilder(String username) {
        return String.format("Arrivederci, %s!", username);
    }

    public String farewellBodyBuilder(String username) {
        return String.format("%s, %s!", username, farewell);
    }

}
