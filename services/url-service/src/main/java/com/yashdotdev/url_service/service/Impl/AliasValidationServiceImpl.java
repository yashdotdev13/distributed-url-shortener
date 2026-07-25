package com.yashdotdev.url_service.service.Impl;


import com.yashdotdev.url_service.repository.UrlRepository;
import com.yashdotdev.url_service.service.AliasValidationService;
import com.yashdotdev.url_service.validation.ReservedAliasValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AliasValidationServiceImpl implements AliasValidationService {

    private final UrlRepository urlRepository;
    private final ReservedAliasValidator reservedAliasValidator;


    @Override
    public String validateAndNormalize(String alias) {

        if (alias == null || alias.isBlank()) {
            return null;
        }

        alias = alias.trim().toLowerCase();

        if (!alias.matches("^[a-zA-Z0-9_-]+$")) {
            throw new IllegalArgumentException(
                    "Alias can contain only letters, numbers, '-' and '_'."
            );
        }

        if (reservedAliasValidator.isReserved(alias)) {
            throw new IllegalArgumentException(
                    "Alias '" + alias + "' is reserved."
            );
        }

        if (urlRepository.existsByShortCode(alias)) {
            throw new IllegalArgumentException(
                    "Alias '" + alias + "' already exists."
            );
        }

        return alias;
    }
}
