package me.dchuiko.spa.rest.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import me.dchuiko.spa.model.User;
import me.dchuiko.spa.rest.JsonType;

@JsonPropertyOrder({"href", "type", "name" })
public class UserJson extends HateosObject {
    @JsonProperty
    private final String name;
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Ref accounts;
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Ref transactions;

    public UserJson(User user, String absoluteUri) {
        super(JsonType.User, user, absoluteUri);
        this.name = user.name();
        this.accounts = new Ref(JsonType.Account, new Href(getHref(), "accounts"));
        this.transactions = new Ref(JsonType.Transaction, new Href(getHref(), "transactions"));
    }

    @JsonCreator
    public UserJson(@JsonProperty("href") String href, @JsonProperty("name") String name) {
        super(JsonType.User, new Href(href));
        this.name = name;
    }


    public String getName() {
        return name;
    }

}