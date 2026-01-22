package org.exercises.cryptography;

public record UserDTO (Long id, String userDocument, String creditCardToken, Long value) {

    public static UserDTO from(UserEntity user) {
        return new UserDTO(user.getId(), user.getUserDocument(), user.getCreditCardToken(), user.getValue());
    }

}
