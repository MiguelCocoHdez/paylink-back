package com.paylink.auth.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.paylink.auth.application.dto.UserRegisterLocalDTO;
import com.paylink.auth.application.port.out.UserRegisteredPublisher;
import com.paylink.auth.application.port.out.UserRepository;
import com.paylink.auth.domain.exception.InvalidEmailOrUsernameException;
import com.paylink.auth.domain.model.AuthProvider;
import com.paylink.auth.domain.model.Role;
import com.paylink.auth.domain.model.User;
import com.paylink.security.jwt.JwtService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test RegisterLocal Service")
public class RegisterLocalServiceTest {
	
	@Mock
	private UserRepository ur;
	
	@Mock
	private JwtService js;

	@Mock
	private PasswordEncoder pe;
	
	@Mock
	private UserRegisteredPublisher urp;
	
	@InjectMocks
	private RegisterLocalService registerLocalService;
	
	private UserRegisterLocalDTO userDTO;
    private User savedUser;

	@BeforeEach
	void setUp() {
		userDTO = UserRegisterLocalDTO.builder()
				.username("test")
				.email("test@gmail.com")
				.password("passwordRaw")
				.build();
		
		savedUser = User.fromEntity(
				1L,
				"test",
				"test@gmail.com",
				"passwordEncrypted",
				Role.USER,
				AuthProvider.LOCAL
				);
	}
	
	@Nested //Anotacion que se usa como una carpeta para agrupar test
	@DisplayName("Test Success Register")
	class SuccessRegister {
		
		@BeforeEach
		void setUpSuccessRegister() {
			when(pe.encode(anyString())).thenReturn("passwordEncrypted");
			when(ur.save(any(User.class))).thenReturn(savedUser);
			when(js.generateToken(any())).thenReturn("mock_token");
		}
		
		@Test
		@DisplayName("Verificar que hace encode a la contraseña")
		void pwEncode() {
			//when
			registerLocalService.registerLocal(userDTO);
			//then
			verify(pe).encode("passwordRaw");
		}
		
        @Test
        @DisplayName("Debe guardar el usuario en la base de datos")
        void debeGuardarUsuario() {
            // WHEN
            registerLocalService.registerLocal(userDTO);      
            // THEN
            verify(ur).save(any(User.class));
        }
        
        @Test
        @DisplayName("Debe publicar el evento UserRegistered")
        void debePublicarEvento() {
            // WHEN
            registerLocalService.registerLocal(userDTO);           
            // THEN
            verify(urp).publishUserRegistered(any());
        }
        
        @Test
        @DisplayName("Debe generar el token JWT")
        void debeGenerarToken() {
            // WHEN
            registerLocalService.registerLocal(userDTO);      
            // THEN
            verify(js).generateToken(any());
        }
        
        @Test
        @DisplayName("Debe retornar un token JWT")
        void debeRetornarTokenJWT() {
            // WHEN
            String token = registerLocalService.registerLocal(userDTO);
            
            // THEN
            assertNotNull(token);
            assertEquals("mock_token", token);
        }
	}
	
	@Nested
	@DisplayName("Test Not Success Register")
	class NotSuccessRegister {
		
		@Test
		@DisplayName("Debe mostrar email ya registrado")
		void emailRegistrado() {
			//WHEN
			when(ur.existsByEmail("test@gmail.com")).thenReturn(true);
			Boolean flag = ur.existsByEmail("test@gmail.com");
			//THEn
			assertEquals(true, flag);
			InvalidEmailOrUsernameException exception = assertThrows(InvalidEmailOrUsernameException.class, 
					() -> registerLocalService.registerLocal(userDTO));
			assertEquals("Email ya registrado", exception.getMessage());
		}
		
		@Test
		@DisplayName("Debe mostrar username ya registrado")
		void usernameRegistrado() {
			//WHEN
			when(ur.existsByUsername(anyString())).thenReturn(true);
			Boolean flag = ur.existsByUsername("test");
			//THEN
			assertEquals(true, flag);
			InvalidEmailOrUsernameException exception = assertThrows(InvalidEmailOrUsernameException.class, 
					() -> registerLocalService.registerLocal(userDTO));
			assertEquals("Username ya registrado", exception.getMessage());
		}

	}
}
