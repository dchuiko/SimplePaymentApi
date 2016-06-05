package me.dchuiko.spa.rest.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import me.dchuiko.spa.model.User;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.WebContext;

@JsonPropertyOrder({"href", "type", "name" })
public class UserJson extends HateosObject {
    @JsonProperty
    private final String name;
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FieldRef accounts;
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FieldRef transactions;

    public UserJson(WebContext webContext, User user) {
        super(webContext, JsonType.User, user.id());
        this.name = user.name();
        this.accounts = new FieldRef(JsonType.Account, getHref(), "accounts");
        this.transactions = new FieldRef(JsonType.Transaction, getHref(), "transactions");
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