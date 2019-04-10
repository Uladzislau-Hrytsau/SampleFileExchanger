package com.exchange.service.implementation;

import com.exchange.configuration.security.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * The type Common service.
 */
@Component
public class CommonService {

    /**
     * Gets user id by authentication.
     *
     * @param authentication the authentication
     * @return the user id by authentication
     */
    public Long getUserIdByAuthentication(final Authentication authentication) {
        return ((UserDetails) authentication.getPrincipal()).getUserId();
    }

    /**
     * Gets authorities by authentication.
     *
     * @param authentication the authentication
     * @return the authorities by authentication
     */
    public Collection<GrantedAuthority> getAuthoritiesByAuthentication(final Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getAuthorities();
    }

    /**
     * Gets offset by size and page.
     *
     * @param size the size
     * @param page the page
     * @return the offset by size and page
     */
    public Integer getOffsetBySizeAndPage(
            final Integer size,
            final Integer page) {
        return size * (page - 1);
    }

    /**
     * Check batch result boolean.
     *
     * @param batchResult the batch result
     * @return the boolean
     */
    public Boolean checkBatchResult(final int[] batchResult) {
        for (int result : batchResult) {
            if (result == 0) {
                return false;
            }
        }
        return true;
    }
}
