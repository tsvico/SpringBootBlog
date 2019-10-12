package com.tsvico.blog.service;

import com.tsvico.blog.po.Tag;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TagService {

    Tag saveTag(Tag type);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTop(Integer size);

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag type) throws NotFoundException;

    void deleteTag(Long id);
}
