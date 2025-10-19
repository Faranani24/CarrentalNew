package co.za.carrental.service.impl;

import co.za.carrental.domain.Customer;
import co.za.carrental.repository.CustomerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public JwtUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Convert role to Spring Security authority format (ROLE_ADMIN, ROLE_CUSTOMER)
        String role = customer.getRole() != null ? customer.getRole() : "CUSTOMER";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        return new User(
                customer.getEmail(),
                customer.getPassword(),
                Collections.singletonList(authority)
        );
    }
}