package me.dchuiko.spa.rest.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import me.dchuiko.spa.model.AccountWithBalance;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.WebContext;

@JsonPropertyOrder({"href", "type", "number", "initialBalance", "balance", "alias", "user"})
public class AccountJson extends HateosObject {
    @JsonProperty
    private String number;
    @JsonProperty
    private String alias;
    @JsonIgnore
    private int initialBalance;
    @JsonProperty
    private int balance;
    @JsonProperty
    private Ref user;

    public AccountJson(WebContext context, AccountWithBalance account) {
        super(context, JsonType.Account, account.id());
        this.number = account.number();
        this.alias = account.alias();
        this.balance = account.balance();
        this.initialBalance = account.initialBalance();
        this.user = new Ref(context, JsonType.User, account.userId());
    }

    @JsonCreator
    public AccountJson(@JsonProperty("href") String href,
                       @JsonProperty("number") String number,
                       @JsonProperty("alias") String alias,
                       @JsonProperty("initialBalance") int initialBalance,
                       @JsonProperty("user") Ref user) {
        super(JsonType.Account, new Href(href));
        this.number = number;
        this.initialBalance = initialBalance;
        this.alias = alias;
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public String getAlias() {
        return alias;
    }

    public int getInitialBalance() {
        return initialBalance;
    }

    public Ref getUser() {
        return user;
    }
}
