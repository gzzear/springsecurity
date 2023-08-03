package com.gz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gz.entity.po.SysClientDetails;
import com.gz.mapper.SysClientDetailsMapper;
import com.gz.service.ISysClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户端授权表 服务实现类
 * </p>
 *
 * @author gzzear
 * @since 2023-07-13
 */
@Service
public class SysClientDetailsServiceImpl extends
    ServiceImpl<SysClientDetailsMapper, SysClientDetails> implements
    ISysClientDetailsService {

}
