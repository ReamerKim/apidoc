package com.moss.apidoc.repository;

import org.springframework.stereotype.Repository;

import com.moss.apidoc.model.Api;

@Repository
public interface ApiRepository {
    
    int addApi(Api api);
}
