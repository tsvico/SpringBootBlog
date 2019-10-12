package com.tsvico.blog.service;

import com.tsvico.blog.dao.TypeRepository;
import com.tsvico.blog.po.Type;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/14 1:11
 * 功能 实现type接口
 */
@Service    //注解 Service
public class TypeServiceImpl implements TypeService {

    //注入
    @Autowired
    private TypeRepository typeRepository;

    @Transactional  //放在事务中 dao操作数据库
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional  //放在事务中 dao操作数据库
    @Override
    public Type getType(Long id) {
        //return typeRepository.findOne(id); //已经弃用
        return typeRepository.findById(id).get();
    }

    @Transactional  //放在事务中 dao操作数据库
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public Type getTypeByname(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        //Pageable pageable = new PageRequest(0,size); 过时
        Pageable pageable = PageRequest.of(0,size,sort);

        return typeRepository.findTop(pageable);
    }

    @Transactional  //放在事务中 dao操作数据库
    @Override
    public Type updateType(Long id, Type type) throws NotFoundException {
        Type t = typeRepository.findById(id).get();
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional  //放在事务中 dao操作数据库
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
