package com.moss.apidoc.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moss.apidoc.model.Api;
import com.moss.apidoc.repository.ApiRepository;

@Service
public class ApiService {
    
    @Inject
    private ApiRepository repository;
    
    @Transactional("apiTxMgr")
    public int addApi(Api api) {
        int affectedRow = repository.addApi(api);
        if (api.getId() % 2 == 0)
            throw new RuntimeException("tx test");
        return api.getId();
    }
}
