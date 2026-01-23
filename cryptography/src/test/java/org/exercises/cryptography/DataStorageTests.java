package org.exercises.cryptography;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = CryptographyApplication.class)
@Transactional
public class DataStorageTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldEncryptOnSave() {
        UserEntity user = new UserEntity("teste", "teste", 0L);
        userRepository.save(user);

        String storedUserDocument = jdbcTemplate.queryForObject("SELECT USER_DOCUMENT FROM T_USER", String.class);
        String storedCreditCardToken = jdbcTemplate.queryForObject("SELECT CREDIT_CARD_TOKEN FROM T_USER", String.class);

        assertThat(storedUserDocument).isNotEqualTo("teste");
        assertThat(storedCreditCardToken).isNotEqualTo("teste");
    }

    @Test
    public void shouldDecryptOnFindById() {
        UserEntity user = new UserEntity("teste", "teste", 0L);
        UserEntity saved = userRepository.save(user);
        UserEntity retrieved = userRepository.findById(saved.getId()).orElseThrow();

        assertThat(retrieved.getUserDocument()).isEqualTo("teste");
        assertThat(retrieved.getCreditCardToken()).isEqualTo("teste");
    }

    @Test
    public void shouldSaveAndFindSavedUser() {
        UserEntity user = new UserEntity("teste", "teste", 0L);
        UserEntity saved = userRepository.save(user);
        assertThat(userRepository.findById(saved.getId()).orElse(null)).isNotNull();
    }

    @Test
    public void shouldDeleteAndNotFindDeletedUser() {
        UserEntity user = new UserEntity("teste", "teste", 0L);
        UserEntity saved = userRepository.save(user);
        assertThat(userRepository.findById(saved.getId()).orElse(null)).isNotNull();
        userRepository.deleteById(saved.getId());
        assertThat(userRepository.findById(saved.getId()).orElse(null)).isNull();
    }

}
