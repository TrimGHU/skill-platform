package com.ubtechinc.corpus.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
//import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubtechinc.corpus.api.ICorpusService;
import com.ubtechinc.corpus.entity.Corpus;
import com.ubtechinc.corpus.mapper.CorpusMapper;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hugui
 * @since 2018-04-02
 */

@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class CorpusServiceImpl extends ServiceImpl<CorpusMapper, Corpus> implements ICorpusService {

}
