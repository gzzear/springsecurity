package com.gz.controller;

import com.gz.common.core.entity.R;
import com.gz.entity.po.SysClientDetails;
import com.gz.service.ISysClientDetailsService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 客户端权限表 前端控制器
 * </p>
 *
 * @author gzzear
 * @since 2023-07-13
 */
@RestController
@RequestMapping("/client")
public class SysClientDetailsController {

  @Resource
  private ISysClientDetailsService clientDetailsService;

  @ApiOperation("客户端列表")
  @PreAuthorize("@ss.hasPermi('system:client:list')")
  @GetMapping("/list")
  public R<List<SysClientDetails>> listAll() {
    return R.ok(clientDetailsService.list());
  }

  @ApiOperation("获取客户端详情")
  @GetMapping("/{clientId}")
  public R<SysClientDetails> getInfo(@PathVariable String clientId) {
    return R.ok(clientDetailsService.getById(clientId));
  }


  @ApiOperation("上传客户端详情")
  @PostMapping
  public R<String> add(@RequestBody SysClientDetails sysClientDetails) {
    String clientId = sysClientDetails.getClientId();
    SysClientDetails clientDetails = clientDetailsService.getById(clientId);
    if (Objects.nonNull(clientDetails)) {
      return R.fail("新增终端'" + clientId + "'失败，编号已存在");
    }
    clientDetailsService.save(sysClientDetails);
    return R.ok("上传成功");
  }


  @ApiOperation("修改终端配置")
  @PutMapping
  public R<String> edit(@RequestBody SysClientDetails sysClientDetails) {
    clientDetailsService.updateById(sysClientDetails);
    return R.ok("修改成功");
  }

  @ApiOperation("删除终端配置")
  @DeleteMapping("/{clientIds}")
  public R<String> remove(@PathVariable List<String> clientIds) {
    clientDetailsService.removeBatchByIds(clientIds);
    return R.ok("删除成功");
  }
}
