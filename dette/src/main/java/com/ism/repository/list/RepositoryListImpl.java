package com.ism.repository.list;

import java.util.List;

import com.ism.core.repository.Repository;

import java.util.ArrayList;

public class RepositoryListImpl<Entity> implements Repository<Entity> {
    protected List<Entity> datas = new ArrayList<>();

}
