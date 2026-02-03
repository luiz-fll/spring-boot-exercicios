package org.exercises.session;

public record UserDTO(String email) {

    public static UserDTO from(UserEntity userEntity) {
        return new UserDTO(userEntity.getEmail());
    }

}
