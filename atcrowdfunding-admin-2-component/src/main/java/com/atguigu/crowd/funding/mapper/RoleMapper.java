package com.atguigu.crowd.funding.mapper;

import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    //模糊查询
    List<Role> selectByKeyword (String keyword);

    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectAssignByAdminId(Integer adminId);

    List<Role> selectUnAssignByAdminId(Integer adminId);

    void deleteExistedRoleAssign(Integer adminId);

    void insertRoleAssign(@Param("adminId") Integer adminId,@Param("roleIdList") List<Integer> roles);
}