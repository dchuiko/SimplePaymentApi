package me.dchuiko.spa.rest.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import me.dchuiko.spa.model.Transaction;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.WebContext;

import java.time.LocalDateTime;

@JsonPropertyOrder({"href", "type", "moment", "sender", "senderAccount", "receiver", "receiverAccount", "amount"})
public class TransactionJson extends HateosObject {
    @JsonProperty
    private LocalDateTime moment;
    @JsonProperty
    private Ref sender;
    @JsonProperty
    private Ref senderAccount;
    @JsonProperty
    private Ref receiver;
    @JsonProperty
    private Ref receiverAccount;
    @JsonProperty
    private int amount;

    public TransactionJson(WebContext context, Transaction transaction) {
        super(context, JsonType.Transaction, transaction.id());
        this.moment = transaction.moment();
        this.sender = new Ref(context, JsonType.User, transaction.senderId());
        this.senderAccount = new Ref(context, JsonType.Account, transaction.senderAccountId());
        this.receiver = new Ref(context, JsonType.User, transaction.receiverId());
        this.receiverAccount = new Ref(context, JsonType.Account, transaction.senderAccountId());
        this.amount = transaction.amount();
    }

    @JsonCreator
    public TransactionJson(@JsonProperty("href") String href,
                           @JsonProperty("moment") LocalDateTime moment,
                           @JsonProperty("senderAccount") Ref senderAccount,
                           @JsonProperty("receiverAccount") Ref receiverAccount,
                           @JsonProperty("amount") int amount) {
        super(JsonType.Transaction, new Href(href));
        this.moment = moment;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public Ref getSenderAccount() {
        return senderAccount;
    }

    public Ref getReceiverAccount() {
        return receiverAccount;
    }

    public int getAmount() {
        return amount;
    }
}
