package org.exercises.cryptography;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAllEndpoint() throws Exception {
        List<UserDTO> users = List.of(
                new UserDTO(1L, "user1", "card1", 100L),
                new UserDTO(2L, "user2", "card2", 200L),
                new UserDTO(3L, "user3", "card3", 300L)
        );

        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void testFindOneEndpoint() throws Exception {
        UserDTO user = new UserDTO(1L, "user1", "card1", 100L);

        when(userService.findUser(1L)).thenReturn(user);
        when(userService.findUser(2L)).thenThrow(NoSuchUserExistsException.class);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));

        mockMvc.perform(get("/users/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSaveEndpoint() throws Exception {
        UserDTO requestUser = new UserDTO(null, "user1", "card1", 100L);
        UserDTO badRequestUser = new UserDTO(null, null, null, null);
        UserDTO responseUser = new UserDTO(1L, "user1", "card1", 100L);

        when(userService.addUser(requestUser)).thenReturn(responseUser);
        when(userService.addUser(badRequestUser)).thenThrow(MissingDataException.class);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUser)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseUser)));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequestUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEndpoint() throws Exception {
        UserDTO requestUser = new UserDTO(null, "edited", "edited", null);
        UserDTO responseUser = new UserDTO(1L, "edited", "edited", 100L);

        when(userService.updateUser(1L, requestUser)).thenReturn(responseUser);
        when(userService.updateUser(2L, requestUser)).thenThrow(NoSuchUserExistsException.class);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUser)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseUser)));

        mockMvc.perform(put("/users/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteEndpoint() throws Exception {
        UserDTO user = new UserDTO(1L, "user1", "card1", 100L);

        when(userService.deleteUser(1L)).thenReturn(user);
        when(userService.deleteUser(2L)).thenThrow(NoSuchUserExistsException.class);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));

        mockMvc.perform(delete("/users/2"))
                .andExpect(status().isNotFound());
    }

}
