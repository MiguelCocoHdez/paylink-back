package com.paylink.auth.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.paylink.auth.application.dto.UserLoginLocalDTO;
import com.paylink.auth.application.port.out.UserRepository;
import com.paylink.auth.domain.exception.InvalidCredentialsException;
import com.paylink.auth.domain.model.AuthProvider;
import com.paylink.auth.domain.model.Role;
import com.paylink.auth.domain.model.User;
import com.paylink.security.jwt.JwtService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test LoginLocal Service")
public class LoginLocalServiceTest {

	@Mock
	private UserRepository ur;
	
	@Mock
	private JwtService js;
	
	@Mock
	private PasswordEncoder pe;
	
	@InjectMocks
	private LoginLocalService loginLocalService;
	
	private UserLoginLocalDTO loginWithEmail;
	private UserLoginLocalDTO loginWithUsername;
    private User savedUser;
	
	@BeforeEach
	void setUp() {
		loginWithEmail = UserLoginLocalDTO.builder()
				.emailOrUsername("test@gmail.com")
				.password("psswd")
				.build();
		
		loginWithUsername = UserLoginLocalDTO.builder()
				.emailOrUsername("test")
				.password("psswd")
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
	
	@Nested
	@DisplayName("Test Success Email")
	class successEmailLogin {
		
		@BeforeEach
		void setUpEmailLoginSuccess() {
			when(ur.findByEmail(loginWithEmail.getEmailOrUsername())).thenReturn(savedUser);
			when(pe.matches(loginWithEmail.getPassword(), savedUser.getPassword())).thenReturn(true);
			when(js.generateToken(any())).thenReturn("fake.jwt.token");
		}
		

		@Test
		@DisplayName("Comprobar que busca por email")
		void comprobarEmail() {
			loginLocalService.loginLocal(loginWithEmail);
			
			verify(ur).findByEmail(loginWithEmail.getEmailOrUsername());
			verify(ur, never()).findByUsername(anyString());
		}
		
		@Test
	    @DisplayName("Debe validar la contraseña")
	    void debeValidarPassword() {
	        loginLocalService.loginLocal(loginWithEmail); 

	        verify(pe).matches(loginWithEmail.getPassword(), savedUser.getPassword());
	    }
		
		@Test
	    @DisplayName("Debe generar y retornar token")
	    void debeGenerarToken() {
	        String token = loginLocalService.loginLocal(loginWithEmail);
	        
	        verify(js).generateToken(any());
	        assertEquals("fake.jwt.token", token);
	    }
	}
	
	@Nested
	@DisplayName("Test Succes username")
	class successUsernameLogin {
		
		@BeforeEach
		void setUpSuccessUsernameLogin() {
			when(ur.findByUsername(loginWithUsername.getEmailOrUsername())).thenReturn(savedUser);
			when(pe.matches(loginWithUsername.getPassword(), savedUser.getPassword())).thenReturn(true);
			when(js.generateToken(any())).thenReturn("fake.jwt.token");
		}
		
		@Test
		@DisplayName("Comprobar que busca por username")
		void comprobarEmail() {
			loginLocalService.loginLocal(loginWithUsername);
			
			verify(ur).findByUsername(loginWithUsername.getEmailOrUsername());
			verify(ur, never()).findByEmail(anyString());
		}
		
		@Test
	    @DisplayName("Debe validar la contraseña")
	    void debeValidarPassword() {
	        loginLocalService.loginLocal(loginWithUsername); 

	        verify(pe).matches(loginWithUsername.getPassword(), savedUser.getPassword());
	    }
		
		@Test
	    @DisplayName("Debe generar y retornar token")
	    void debeGenerarToken() {
	        String token = loginLocalService.loginLocal(loginWithUsername);
	        
	        verify(js).generateToken(any());
	        assertEquals("fake.jwt.token", token);
	    }
	}
	
	@Nested
	@DisplayName("Test Failed Login")
	class FailedLogin {

	    @Test
	    @DisplayName("Debe lanzar excepción cuando usuario no existe (email)")
	    void debeLanzarExcepcionConEmail() {
	        InvalidCredentialsException exception = assertThrows(
	            InvalidCredentialsException.class,
	            () -> loginLocalService.loginLocal(loginWithEmail)
	        );
	        
	        assertEquals("Usuario o contraseña incorrectos", exception.getMessage());
	    }

	    @Test
	    @DisplayName("Debe lanzar excepción cuando usuario no existe (username)")
	    void debeLanzarExcepcionConUsername() {
	        InvalidCredentialsException exception = assertThrows(
	            InvalidCredentialsException.class,
	            () -> loginLocalService.loginLocal(loginWithUsername)
	        );
	        
	        assertEquals("Usuario o contraseña incorrectos", exception.getMessage());
	    }
	    
	    @Test
	    @DisplayName("Debe buscar usuario por email")
	    void debeBuscarPorEmail() {
	        assertThrows(InvalidCredentialsException.class,
	            () -> loginLocalService.loginLocal(loginWithEmail));
	        
	        verify(ur).findByEmail("test@gmail.com");
	    }
	    
	    @Test
	    @DisplayName("Debe buscar usuario por username")
	    void debeBuscarPorUsername() {
	        assertThrows(InvalidCredentialsException.class,
	            () -> loginLocalService.loginLocal(loginWithUsername));
	        
	        verify(ur).findByUsername("test");
	    }
	    
	    @Test
	    @DisplayName("NO debe validar contraseña (user es null)")
	    void noDebeValidarPassword() {
	        assertThrows(InvalidCredentialsException.class,
	            () -> loginLocalService.loginLocal(loginWithEmail));
	        
	        verify(pe, never()).matches(anyString(), anyString());
	    }
	    
	    @Test
	    @DisplayName("NO debe generar token")
	    void noDebeGenerarToken() {
	        assertThrows(InvalidCredentialsException.class,
	            () -> loginLocalService.loginLocal(loginWithEmail));
	        
	        verify(js, never()).generateToken(any());
	    }
	}
}
