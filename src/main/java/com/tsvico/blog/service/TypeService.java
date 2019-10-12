package com.tsvico.blog.service;

import com.tsvico.blog.po.Type;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/14 1:08
 * 功能 type接口
 */
public interface TypeService {

    Type saveType(Type type); //新增
    Type getType(Long id); //查询
    Page<Type> listType(Pageable pageable);//分页查询
    Type getTypeByname(String name);//通过名称来查询
    List<Type> listType();
    List<Type> listTypeTop(Integer size);
    Type updateType (Long id,Type type) throws NotFoundException; //修改
    void deleteType(Long id); //删除
}
