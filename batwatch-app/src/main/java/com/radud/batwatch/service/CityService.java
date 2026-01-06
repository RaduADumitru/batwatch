package com.radud.batwatch.service;

import com.radud.batwatch.model.City;
import com.radud.batwatch.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public City createCity(City city) {
        return cityRepository.save(city);
    }
}
