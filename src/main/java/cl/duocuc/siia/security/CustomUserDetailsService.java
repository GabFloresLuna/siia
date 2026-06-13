package cl.duocuc.siia.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Simulación de base de datos de usuarios
    private final Map<String, UserDetails> users = new ConcurrentHashMap<>();

    public CustomUserDetailsService() {
        // Inspector: solo puede consultar y registrar pasajeros, trámites, etc.
        users.put("inspector", User.builder()
                .username("inspector")
                .password(new BCryptPasswordEncoder().encode("inspector123"))
                .roles("INSPECTOR")
                .build());

        // Administrador: tiene todos los permisos (además puede eliminar, etc.)
        users.put("admin", User.builder()
                .username("admin")
                .password(new BCryptPasswordEncoder().encode("admin123"))
                .roles("ADMIN", "INSPECTOR")
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return user;
    }
}