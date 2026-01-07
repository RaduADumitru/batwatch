package com.radud.batwatch.service;

import com.radud.batwatch.model.City;
import com.radud.batwatch.repository.CityRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    @Transactional
    public City createCity(@Nonnull City city) {
        return cityRepository.save(city);
    }
}
