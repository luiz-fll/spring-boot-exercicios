package org.exercises.cryptography;

import jakarta.persistence.*;

@Entity
@Table(name = "T_USER")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_DOCUMENT", nullable = false)
    @Convert(converter = SensitiveDataConverter.class)
    private String userDocument;

    @Column(name = "CREDIT_CARD_TOKEN", nullable = false)
    @Convert(converter = SensitiveDataConverter.class)
    private String creditCardToken;

    @Column(name = "AMOUNT", nullable = false)
    private Long value;

    public UserEntity() {}

    public UserEntity(String userDocument, String creditCardToken, Long value) {
        this.userDocument = userDocument;
        this.creditCardToken = creditCardToken;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getUserDocument() {
        return userDocument;
    }

    public String getCreditCardToken() {
        return creditCardToken;
    }

    public Long getValue() {
        return value;
    }

    public void setUserDocument(String userDocument) {
        this.userDocument = userDocument;
    }

    public void setCreditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    public void setValue(Long value) {
        this.value = value;
    }

}
