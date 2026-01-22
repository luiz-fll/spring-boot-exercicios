package org.exercises.cryptography;

import jakarta.persistence.*;

@Entity
@Table(name = "T_USER")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USER_DOCUMENT")
    private String userDocument;

    @Column(name = "CREDIT_CARD_TOKEN")
    private String creditCardToken;

    @Column(name = "AMOUNT")
    private long value;

    public UserEntity() {}

    public UserEntity(String userDocument, String creditCardToken, Long value) {
        this.userDocument = userDocument;
        this.creditCardToken = creditCardToken;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getUserDocument() {
        return userDocument;
    }

    public String getCreditCardToken() {
        return creditCardToken;
    }

    public long getValue() {
        return value;
    }

    public void setUserDocument(String userDocument) {
        this.userDocument = userDocument;
    }

    public void setCreditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
